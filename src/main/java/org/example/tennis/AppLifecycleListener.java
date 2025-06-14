package org.example.tennis;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@WebListener
public class AppLifecycleListener implements ServletContextListener {
    private final H2ServerManager h2ServerManager = new H2ServerManager();
    private final SessionFactoryManager sessionFactoryManager = new SessionFactoryManager();

    private SessionFactory sessionFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        h2ServerManager.startServer();

        sessionFactoryManager.createSessionFactory();
        sessionFactory = sessionFactoryManager.getSessionFactory();
        sce.getServletContext().setAttribute("sessionFactory", sessionFactory);

        Map<UUID, MatchScoreModel> currentMatches = new ConcurrentHashMap<>();
        sce.getServletContext().setAttribute("currentMatches", currentMatches);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        h2ServerManager.stopServer();
        sessionFactoryManager.closeSessionFactory();
    }
}


