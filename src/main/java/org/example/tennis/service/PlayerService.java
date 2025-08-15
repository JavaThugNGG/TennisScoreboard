package org.example.tennis.service;

import org.example.tennis.exception.PlayerAlreadyExistsException;
import org.example.tennis.dao.PlayerDao;
import org.example.tennis.entity.PlayerEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerService {
    private static final Logger logger = LoggerFactory.getLogger(PlayerService.class);
    private final SessionFactory sessionFactory;
    private final PlayerDao playerDao;

    public PlayerService(SessionFactory sessionFactory, PlayerDao playerDao) {
        this.sessionFactory = sessionFactory;
        this.playerDao = playerDao;
    }

    public PlayerEntity getByName(String playerName) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        try {
            PlayerEntity player = playerDao.getByName(session, playerName);
            session.getTransaction().commit();
            return player;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            logger.error("error getting player by name: {}", playerName, e);
            throw e;
        } finally {
            session.close();
        }
    }

    public PlayerEntity insert(String playerName) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        try {
            PlayerEntity player = new PlayerEntity(playerName);
            session.persist(player);
            session.getTransaction().commit();
            return player;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            if (isUniqueConstraintViolation(e)) {
                throw new PlayerAlreadyExistsException();
            } else {
                logger.warn("error inserting player by name: {}", playerName, e);
            }
            throw e;
        } finally {
            session.close();
        }
    }

    private boolean isUniqueConstraintViolation(Throwable e) {
        Throwable cause = e;
        while (cause != null) {
            if (cause instanceof org.hibernate.exception.ConstraintViolationException) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }
}

