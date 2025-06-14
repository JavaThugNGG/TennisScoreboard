package org.example.tennis;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryManager {
    private SessionFactory sessionFactory;

    public void createSessionFactory() {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            System.out.println("SessionFactory создан.");                                                          //в лог
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeSessionFactory() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            System.out.println("SessionFactory закрыт.");                                                      //в лог
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

