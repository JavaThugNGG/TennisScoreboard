package org.example.tennis;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    private SessionFactory sessionFactory;
    private Map<UUID, MatchScoreModel> currentMatches;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/new-match.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sessionFactory = (SessionFactory) getServletContext().getAttribute("SessionFactory");
        currentMatches = (Map<UUID, MatchScoreModel>) getServletContext().getAttribute("currentMatches");

        String firstPlayerName = request.getParameter("playerOne");
        String secondPlayerName = request.getParameter("playerTwo");

        if (firstPlayerName == null || secondPlayerName == null || firstPlayerName.isBlank() || secondPlayerName.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Player names are required.");
            return;
        }

        int generatedId1;
        int generatedId2;

        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            // Получаем или создаём первого игрока
            Integer player1Id;
            try {
                String hql1 = "SELECT id FROM PlayerEntity WHERE name = :name";
                Query<Integer> query1 = session.createQuery(hql1, Integer.class);
                query1.setParameter("name", firstPlayerName);
                player1Id = query1.getSingleResult();
            } catch (jakarta.persistence.NoResultException e) {
                PlayerEntity player1 = new PlayerEntity(firstPlayerName);
                session.persist(player1);
                player1Id = player1.getId();
            }
            generatedId1 = player1Id;

            // Получаем или создаём второго игрока
            Integer player2Id;
            try {
                String hql2 = "SELECT id FROM PlayerEntity WHERE name = :name";
                Query<Integer> query2 = session.createQuery(hql2, Integer.class);
                query2.setParameter("name", secondPlayerName);
                player2Id = query2.getSingleResult();
            } catch (jakarta.persistence.NoResultException e) {
                PlayerEntity player2 = new PlayerEntity(secondPlayerName);
                session.persist(player2);
                player2Id = player2.getId();
            }
            generatedId2 = player2Id;

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error.");
            return;
        }

        UUID uuid = UUID.randomUUID();
        MatchScoreModel matchScoreModel = new MatchScoreModel(generatedId1, generatedId2, 0, 0, 0, 0, 0, 0);
        currentMatches.put(uuid, matchScoreModel);

        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + "/match-score?uuid=" + uuid);
    }
}
