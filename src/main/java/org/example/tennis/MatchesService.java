package org.example.tennis;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class MatchesService {                                                 // вынесение логики открытия закрытия Session и обертка для эксепшенов
    private final SessionFactory sessionFactory;
    private final MatchesDao matchesDao = new MatchesDao();

    public MatchesService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<MatchEntity> getPageOfMatches(int startIndex) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            List<MatchEntity> matches = matchesDao.getPageOfMatches(session, startIndex);
            session.getTransaction().commit();
            session.close();
            return matches;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public long getTotalMatches() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            long total = matchesDao.getTotalMatches(session);
            session.getTransaction().commit();
            session.close();
            return total;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public List<MatchEntity> getPageOfMatchesWithPlayerNameFilter(PlayerEntity player, int startIndex) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            List<MatchEntity> matches = matchesDao.getPageOfMatchesWithPlayerNameFilter(session, player, startIndex);
            session.getTransaction().commit();
            session.close();
            return matches;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public PlayerEntity getPlayerByName(String playerName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            PlayerEntity player = matchesDao.getPlayerByName(session, playerName);
            session.getTransaction().commit();
            session.close();
            return player;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public long getTotalMatchesWithPlayerNameFilter(PlayerEntity player) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            long totalMatches = matchesDao.getTotalMatchesWithPlayerNameFilter(session, player);
            session.getTransaction().commit();
            session.close();
            return totalMatches;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }
}
