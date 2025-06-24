package org.example.tennis;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class PlayerService {
    private final SessionFactory sessionFactory;
    private final PlayerDao playerDao = new PlayerDao();

    public PlayerService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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
}

