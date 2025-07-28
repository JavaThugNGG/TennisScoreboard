package org.example.tennis;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class AppLifecycleListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(AppLifecycleListener.class);
    private H2ServerManager h2ServerManager;
    private SessionFactoryManager sessionFactoryManager;
    private OngoingMatchesService ongoingMatchesService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Starting app initialization");

        h2ServerManager = new H2ServerManager();
        h2ServerManager.startServer();
        sessionFactoryManager = SessionFactoryManager.getInstance();
        ongoingMatchesService = OngoingMatchesService.getInstance();
        ongoingMatchesService.startScheduler();

        logger.info("App has been initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Starting app shutdown");

        h2ServerManager.stopServer();
        sessionFactoryManager.close();
        ongoingMatchesService.shutdownScheduler();
        JdbcManager.deregisterJdbcDrivers();

        System.out.println("App has been shutdown");
    }
}


