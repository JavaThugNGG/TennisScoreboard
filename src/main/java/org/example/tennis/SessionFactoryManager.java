package org.example.tennis;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class SessionFactoryManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionFactoryManager.class);
    private static final SessionFactoryManager INSTANCE = new SessionFactoryManager();
    private final SessionFactory sessionFactory;

    private SessionFactoryManager() {
        sessionFactory = buildSessionFactory();
    }

    private SessionFactory buildSessionFactory() {
        try {
            SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            logger.info("sessionFactory created");
            return factory;
        } catch (Exception e) {
            logger.error("failed to create sessionFactory", e);
            throw new RuntimeException("error while creating SessionFactory", e);
        }
    }

    public static SessionFactoryManager getInstance() {
        return INSTANCE;
    }

    public void close() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            System.out.println("sessionFactory is closed.");
        }
    }
}

