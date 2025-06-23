package org.example.tennis;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryManager {

    private static final SessionFactoryManager INSTANCE = new SessionFactoryManager();

    private final SessionFactory sessionFactory;

    private SessionFactoryManager() {
        sessionFactory = buildSessionFactory();
    }

    private SessionFactory buildSessionFactory() {
        try {
            SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            System.out.println("SessionFactory создан.");
            return factory;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании SessionFactory", e);
        }
    }

    public static SessionFactoryManager getInstance() {
        return INSTANCE;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void close() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            System.out.println("SessionFactory закрыт.");
        }
    }
}

