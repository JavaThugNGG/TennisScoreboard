package org.example.tennis;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@WebListener
public class AppLifecycleListener implements ServletContextListener {          //при запуске приложения открывает все че надо, при стопе приложения закрывает все че надо
    private final H2ServerManager h2ServerManager = new H2ServerManager();
    private SessionFactoryManager sessionFactoryManager;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        h2ServerManager.startServer();
        sessionFactoryManager = SessionFactoryManager.getInstance();

        Map<UUID, MatchScoreModel> currentMatches = new ConcurrentHashMap<>();
        sce.getServletContext().setAttribute("currentMatches", currentMatches);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        h2ServerManager.stopServer();
        sessionFactoryManager.close();
    }
}


