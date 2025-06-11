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
    private int firstPlayerAdvantage = 0;
    private int secondPlayerAdvantage = 0;


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

        if (intScoredId == firstPlayerId) {
            int currentPoints = matchScore.getFirstPlayerPoints();
            if (currentPoints == 0) {
                int newPoints = 15;
                matchScore.setFirstPlayerPoints(newPoints);
            } else if (currentPoints == 15) {
                int newPoints = 30;
                matchScore.setFirstPlayerPoints(newPoints);
            } else if (currentPoints == 30) {
                int newPoints = 40;
                matchScore.setFirstPlayerPoints(newPoints);
            } else if (currentPoints == 40) {
                int secondPlayerPoints = matchScore.getSecondPlayerPoints();
                if (secondPlayerPoints < 40) {
                    int firstPlayerGames = matchScore.getFirstPlayerGames();
                    int newFirstPlayerGames = firstPlayerGames + 1;
                    matchScore.setFirstPlayerGames(newFirstPlayerGames);
                    matchScore.setFirstPlayerPoints(0);
                    matchScore.setSecondPlayerPoints(0);
                } else if (secondPlayerPoints == 40) {
                    if (firstPlayerAdvantage - secondPlayerAdvantage == 2) {
                        int firstPlayerCurrentGames = matchScore.getFirstPlayerGames();
                        int newFirstPlayerGames = firstPlayerCurrentGames + 1;
                        matchScore.setFirstPlayerGames(newFirstPlayerGames);
                        firstPlayerAdvantage = 0;
                        secondPlayerAdvantage = 0;
                        matchScore.setFirstPlayerPoints(0);
                        matchScore.setSecondPlayerPoints(0);
                    } else {
                        firstPlayerAdvantage++;
                    }
                }
            }
        } else if (intScoredId == secondPlayerId) {
            int currentPoints = matchScore.getSecondPlayerPoints();
            if (currentPoints == 0) {
                int newPoints = 15;
                matchScore.setSecondPlayerPoints(newPoints);
            } else if (currentPoints == 15) {
                int newPoints = 30;
                matchScore.setSecondPlayerPoints(newPoints);
            } else if (currentPoints == 30) {
                int newPoints = 40;
                matchScore.setSecondPlayerPoints(newPoints);
            } else if (currentPoints == 40) {
                int firstPlayerPoints = matchScore.getFirstPlayerPoints();
                if (firstPlayerPoints < 40) {
                    int secondPlayerGames = matchScore.getSecondPlayerGames();
                    int newSecondPlayerGames = secondPlayerGames + 1;
                    matchScore.setSecondPlayerGames(newSecondPlayerGames);
                    matchScore.setFirstPlayerPoints(0);
                    matchScore.setSecondPlayerPoints(0);
                } else if (firstPlayerPoints == 40) {
                    if (secondPlayerAdvantage - firstPlayerAdvantage == 2) {
                        int secondPlayerCurrentGames = matchScore.getSecondPlayerGames();
                        int newSecondPlayerGames = secondPlayerCurrentGames + 1;
                        matchScore.setSecondPlayerGames(newSecondPlayerGames);
                        firstPlayerAdvantage = 0;
                        secondPlayerAdvantage = 0;
                        matchScore.setFirstPlayerPoints(0);
                        matchScore.setSecondPlayerPoints(0);
                    } else {
                        secondPlayerAdvantage++;
                    }
                }
            }
        }





        // После обработки можно редиректить на GET
        response.sendRedirect(request.getContextPath() + "/match-score?uuid=" + uuid);
    }
}
