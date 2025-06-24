package org.example.tennis;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class MatchService {                                                 // вынесение логики открытия закрытия Session и обертка для эксепшенов
    private final SessionFactory sessionFactory;
    private final MatchDao matchDao = new MatchDao();

    public MatchService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<MatchEntity> getPage(int startIndex) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            List<MatchEntity> matches = matchDao.getPage(session, startIndex);
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

    public List<MatchEntity> getPageWithPlayerFilter(PlayerEntity player, int startIndex) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            List<MatchEntity> matches = matchDao.getPageWithPlayerFilter(session, player, startIndex);
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
