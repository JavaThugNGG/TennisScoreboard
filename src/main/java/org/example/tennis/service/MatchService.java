package org.example.tennis.service;

import org.example.tennis.dao.MatchDao;
import org.example.tennis.entity.MatchEntity;
import org.example.tennis.entity.PlayerEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MatchService {
    private static final Logger logger = LoggerFactory.getLogger(MatchService.class);
    private final SessionFactory sessionFactory;
    private final MatchDao matchDao;

    public MatchService(SessionFactory sessionFactory, MatchDao matchDao) {
        this.sessionFactory = sessionFactory;
        this.matchDao = matchDao;
    }

    public List<MatchEntity> getPage(int matchesPerPage, int startIndex) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            List<MatchEntity> matches = matchDao.getPage(session, matchesPerPage, startIndex);
            session.getTransaction().commit();
            return matches;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            logger.error("error getting page of matches with start index: {}, matches per page: {}", startIndex, matchesPerPage, e);
            throw e;
        } finally {
            session.close();
        }

    }

    public long count() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            long total = matchDao.count(session);
            session.getTransaction().commit();
            return total;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            logger.error("error getting count of matches", e);
            throw e;
        } finally {
            session.close();
        }
    }

    public List<MatchEntity> getPageWithPlayerFilter(PlayerEntity player, int matchesPerPage, int startIndex) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            List<MatchEntity> matches = matchDao.getPageWithPlayerFilter(session, player, matchesPerPage, startIndex);
            session.getTransaction().commit();
            return matches;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            logger.error("error getting page of matches with player filter: {}, player id: {}, matches per page: {}, start index: {}", player.getName(), player.getId(), matchesPerPage, startIndex, e);
            throw e;
        } finally {
            session.close();
        }
    }

    public long countWithPlayerFilter(PlayerEntity player) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            long totalMatches = matchDao.countWithPlayerFilter(session, player);
            session.getTransaction().commit();
            return totalMatches;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            logger.error("error getting count matches with player filter: {}, player id: {}", player.getName(), player.getId(), e);
            throw e;
        } finally {
            session.close();
        }
    }
}
