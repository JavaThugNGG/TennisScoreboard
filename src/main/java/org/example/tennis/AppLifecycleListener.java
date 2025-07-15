package org.example.tennis;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppLifecycleListener implements ServletContextListener {
    private H2ServerManager h2ServerManager;
    private SessionFactoryManager sessionFactoryManager;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        h2ServerManager = new H2ServerManager();
        h2ServerManager.startServer();
        sessionFactoryManager = SessionFactoryManager.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        h2ServerManager.stopServer();
        sessionFactoryManager.close();
    }
}


