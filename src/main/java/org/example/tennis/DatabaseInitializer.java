package org.example.tennis;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebListener
public class DatabaseInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName("org.h2.Driver");
            String url = "jdbc:h2:mem:database;DB_CLOSE_DELAY=-1";
            String user = "sa";
            String password = "";

            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();

            String query1 = """
                            CREATE TABLE IF NOT EXISTS players (
                                id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                name VARCHAR(50) UNIQUE
                            )
                            """;
        
            stmt.execute(query1);

            String query2 = """
                            CREATE TABLE IF NOT EXISTS matches (
                                id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                player1 INT,
                                player2 INT,
                                winner INT,
                                FOREIGN KEY (player1) REFERENCES players(id),
                                FOREIGN KEY (player2) REFERENCES players(id),
                                FOREIGN KEY (winner) REFERENCES players(id)
                            )
                            """;
            stmt.execute(query2);

            String query3 = """
                            INSERT INTO players (name) values ('Zdarova')
                            """;

            String query4 = """
                            SELECT *
                            FROM players
                            """;

            stmt.execute(query3);
            ResultSet rs = stmt.executeQuery(query4);
            while (rs.next()) {
                System.out.println("id: " + rs.getInt("id") + ", name: " + rs.getString("name"));
            }
            rs.close();

            stmt.close();
            conn.close();

            System.out.println("Таблицы инициализированы успешно!");  //мб в лог это
        
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
