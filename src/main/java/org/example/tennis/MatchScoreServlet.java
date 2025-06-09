package org.example.tennis;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    private SessionFactory sessionFactory;
    private Map<UUID, MatchScore> currentMatches;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sessionFactory = (SessionFactory) getServletContext().getAttribute("SessionFactory");
        currentMatches = (Map<UUID, MatchScore>) getServletContext().getAttribute("currentMatches");

        String uuidParameter = request.getParameter("uuid");                  //нейминг нормальный напишешь

        if (uuidParameter == null || uuidParameter.isBlank()) {               //валидация на корректность параметров
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Параметр uuid пуст.");
            return;
        }

        UUID uuid;
        try {
            uuid = UUID.fromString(uuidParameter);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный формат UUID.");
            return;
        }

        MatchScore matchScore = currentMatches.get(uuid);

        if (matchScore == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND, "match score не найден");
            return;
        }


        request.setAttribute("match", matchScore);   //ключ значение положили значение для передачи
        request.getRequestDispatcher("/WEB-INF/match-score.jsp").forward(request, response);   //передаем запрос на другой ресурс через диспетчер
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sessionFactory = (SessionFactory) getServletContext().getAttribute("SessionFactory");
        currentMatches = (Map<UUID, MatchScore>) getServletContext().getAttribute("currentMatches");

        String uuidParameter = request.getParameter("uuid");
        String scoredId = request.getParameter("scoredPlayerId");     //игрок, которому надо увеличить очки, нужно в MatchScore его найти по UUID матча и увеличить ему очко
        int intScoredId = Integer.parseInt(scoredId);

        // Валидация UUID
        if (uuidParameter == null || uuidParameter.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Параметр uuid пуст.");
            return;
        }

        UUID uuid;
        try {
            uuid = UUID.fromString(uuidParameter);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный формат UUID.");
            return;
        }


        // Валидация игрока
        if (scoredId == null || scoredId.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Параметр scoredPlayer пуст.");
            return;
        }
        // Получаем матч по UUID
        MatchScore matchScore = currentMatches.get(uuid);
        if (matchScore == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Матч не найден.");
            return;
        }

        int firstPlayerId = matchScore.getFirstPlayerId();
        int secondPlayerId = matchScore.getSecondPlayerId();

        if (firstPlayerId == intScoredId) {
            int currentPoints = matchScore.getFirstPlayerPoints();
            int newPoints = currentPoints + 1;
            matchScore.setFirstPlayerPoints(newPoints);
        } else if (secondPlayerId == intScoredId) {
            int currentPoints = matchScore.getSecondPlayerPoints();
            int newPoints = currentPoints + 1;
            matchScore.setSecondPlayerPoints(newPoints);
        }



        /*
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            String hql = """
            select
            from MatchEntity m
            where player1.id = :playerId or player2.id = :playerId
            """;


            MatchEntity match = session.createQuery(hql, MatchEntity.class)
                    .setParameter("playerId", scoredId)
                    .getSingleResult();

            PlayerEntity player = null;
            if (match.getPlayer1().getId() == intScoredId) {
                player = match.getPlayer1();
            } else if (match.getPlayer2().getId() == intScoredId) {
                player = match.getPlayer2();
            }

            int playerScore = player.get

            session.getTransaction().commit();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при обработке очка.");
            e.printStackTrace();
            return;
        } */


        // После обработки можно редиректить на GET
        response.sendRedirect(request.getContextPath() + "/match-score?uuid=" + uuid);
    }
}
