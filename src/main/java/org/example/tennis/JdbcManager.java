package org.example.tennis;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class JdbcManager {

    public static void deregisterJdbcDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                System.out.println("info: jdbc driver deregistered: " + driver.getClass().getName());
            } catch (SQLException e) {
                System.out.println("error: error deregistering JDBC driver: " + driver.getClass().getName());
                e.printStackTrace(System.out);
            }
        }
    }
}
