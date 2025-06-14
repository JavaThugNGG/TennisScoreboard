package org.example.tennis;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.h2.tools.Server;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@WebListener
public class DatabaseInitializer implements ServletContextListener {
    private SessionFactory sessionFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        createH2Console();

        createSessionFactory();
        System.out.println("SessionFactory открыт.");                                 //в лог
        sce.getServletContext().setAttribute("sessionFactory", sessionFactory);

        Map<UUID, MatchScoreModel> currentMatches = new ConcurrentHashMap<>();
        sce.getServletContext().setAttribute("currentMatches", currentMatches);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            System.out.println("SessionFactory закрыт.");                             //в лог
        }
    }


    private void createH2Console() {
        try {
            Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
        } catch (Exception e) {
            e.printStackTrace();    //сделаешь в лог
        }
    }

    private void createSessionFactory() {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

