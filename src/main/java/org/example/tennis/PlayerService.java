package org.example.tennis;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class PlayerService {
    private final SessionFactory sessionFactory;
    private final PlayerDao playerDao;

    public PlayerService(SessionFactory sessionFactory, PlayerDao playerDao) {
        this.sessionFactory = sessionFactory;
        this.playerDao = playerDao;
    }

    public PlayerEntity getByName(String playerName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            PlayerEntity player = playerDao.getByName(session, playerName);
            session.getTransaction().commit();
            session.close();
            return player;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
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
        } catch (Exception e) {
            session.getTransaction().rollback();
            if (isUniqueConstraintViolation(e)) {
                throw new PlayerAlreadyExistsException();
            }
            throw e;
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

