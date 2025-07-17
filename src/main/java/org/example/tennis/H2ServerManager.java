package org.example.tennis;

import org.h2.tools.Server;

public class H2ServerManager {
    private static final String WEB_OPTION = "-web";
    private static final String WEB_ALLOW_OTHERS_OPTION = "-webAllowOthers";
    private static final String WEB_PORT_OPTION = "-webPort";
    private static final String WEB_PORT = "8082";

    private Server h2Server;

    public void startServer() {
        if (h2Server != null && h2Server.isRunning(false)) {
            System.out.println("H2-сервер уже запущен.");
            return;
        }

        try {
            h2Server = Server.createWebServer(WEB_OPTION, WEB_ALLOW_OTHERS_OPTION, WEB_PORT_OPTION, WEB_PORT).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        if (h2Server != null) {
            h2Server.stop();
            System.out.println("H2-консоль остановлена.");
        } else {
            System.out.println("H2-сервер не был запущен или уже остановлен.");
        }
    }
}
