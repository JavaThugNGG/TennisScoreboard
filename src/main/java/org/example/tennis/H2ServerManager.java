package org.example.tennis;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class H2ServerManager {
    private static final Logger logger = LoggerFactory.getLogger(H2ServerManager.class);
    private static final String WEB_OPTION = "-web";
    private static final String WEB_ALLOW_OTHERS_OPTION = "-webAllowOthers";
    private static final String WEB_PORT_OPTION = "-webPort";
    private static final String WEB_PORT = "8082";

    private Server h2Server;

    public void startServer() {
        if (h2Server != null && h2Server.isRunning(false)) {
            logger.warn("server is already running");
            return;
        }

        try {
            h2Server = Server.createWebServer(WEB_OPTION, WEB_ALLOW_OTHERS_OPTION, WEB_PORT_OPTION, WEB_PORT).start();
            logger.info("h2 server started");
        } catch (Exception e) {
            logger.error("failed to start H2 server", e);
        }
    }

    public void stopServer() {
        if (h2Server != null) {
            h2Server.stop();
            System.out.println("info: h2 server has stopped");
        } else {
            System.out.println("warn: h2 server has not started or has already been stopped");
        }
    }
}
