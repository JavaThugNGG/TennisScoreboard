package org.example.tennis;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    private SessionFactory sessionFactory;
    private Map<UUID, MatchScore> currentMatches;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        sessionFactory = (SessionFactory) getServletContext().getAttribute("SessionFactory");
        currentMatches = (Map<UUID, MatchScore>) getServletContext().getAttribute("currentMatches");

        String firstPlayerName = request.getParameter("playerOne");
        String secondPlayerName = request.getParameter("playerTwo");

        if (firstPlayerName == null || secondPlayerName == null || firstPlayerName.isBlank() || secondPlayerName.isBlank()) {   //валидация на корректность параметров
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Player names are required.");
            return;
        }

        try (Session session = sessionFactory.getCurrentSession()) {           //вот тут была валидация (с firstPlayer'ом сделал, не знаю какого передадут)
            session.beginTransaction();

            String hql1 = """
                FROM PlayerEntity
                WHERE name = :name
            """;
            Query<PlayerEntity> query1 = session.createQuery(hql1, PlayerEntity.class);
            query1.setParameter("name", firstPlayerName);

            PlayerEntity player1 = query1.uniqueResult();


            if (player1 != null) {
                System.out.println("Игрок1 уже существует в таблице игроков!");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            player1 = new PlayerEntity(firstPlayerName);
            session.persist(player1);                        //сохранили игрока в бд



            String hql2 = """
                FROM PlayerEntity
                WHERE name = :name
            """;
            Query<PlayerEntity> query2 = session.createQuery(hql2, PlayerEntity.class);
            query2.setParameter("name", secondPlayerName);

            PlayerEntity player2 = query2.uniqueResult();


            if (player2 != null) {
                System.out.println("Игрок2 уже суещствует в таблице игроков!");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            player2 = new PlayerEntity(secondPlayerName);
            session.persist(player2);                        //сохранили игрока в бд

            session.getTransaction().commit();
        }


        UUID uuid = UUID.randomUUID();
        MatchScore matchScore = new MatchScore(firstPlayerName, secondPlayerName, 0, 0, 0, 0, 0, 0);     //создали MatchScore (нейминг говно)
        currentMatches.put(uuid, matchScore);                                                                           //добавили матч с айдишником



        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + "/match-score?uuid=" + uuid);
    }
}
