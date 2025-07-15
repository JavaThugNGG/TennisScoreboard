package org.example.tennis;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class MatchService {
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
            session.close();
            return matches;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public long count() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            long total = matchDao.count(session);
            session.getTransaction().commit();
            session.close();
            return total;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public List<MatchEntity> getPageWithPlayerFilter(PlayerEntity player, int matchesPerPage, int startIndex) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            List<MatchEntity> matches = matchDao.getPageWithPlayerFilter(session, player, matchesPerPage, startIndex);
            session.getTransaction().commit();
            session.close();
            return matches;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public long countWithPlayerFilter(PlayerEntity player) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            long totalMatches = matchDao.countWithPlayerFilter(session, player);
            session.getTransaction().commit();
            session.close();
            return totalMatches;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }
}
