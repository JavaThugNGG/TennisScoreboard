package org.example.tennis;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.h2.tools.Server;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@WebListener
public class DatabaseInitializer implements ServletContextListener {
    private SessionFactory sessionFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            // Создаем SessionFactory — здесь Hibernate создаст таблицы
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            sce.getServletContext().setAttribute("SessionFactory", sessionFactory);

            // Открываем сессию и транзакцию для инициализации данных
            try (Session session = sessionFactory.getCurrentSession()) {
                session.beginTransaction();

                // Добавляем стартовые данные (например, игроков и матч)
                PlayerEntity firstPlayer = new PlayerEntity("First");
                PlayerEntity secondPlayer = new PlayerEntity("Second");
                MatchEntity firstMatch = new MatchEntity(firstPlayer, secondPlayer, firstPlayer);
                firstPlayer.setWinner(firstMatch);

                session.persist(firstPlayer);
                session.persist(secondPlayer);
                session.persist(firstMatch);

                MatchEntity match = session.get(MatchEntity.class, firstMatch.getId());
                System.out.println(match);


                session.getTransaction().commit();


            }

            Map<UUID, MatchScore> currentMatches = new HashMap<>();
            sce.getServletContext().setAttribute("currentMatches", currentMatches);


            System.out.println("Инициализация таблиц и данных выполнена успешно.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            System.out.println("SessionFactory закрыт.");
        }
    }
}

