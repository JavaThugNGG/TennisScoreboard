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

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {
    MatchScore matchScore;

    private int firstPlayerAdvantage = 0;
    private int secondPlayerAdvantage = 0;

    private int firstPlayerTaibreak;
    private int secondPlayerTaibreak;


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

        matchScore = currentMatches.get(uuid);

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
            scoringFirstPlayer();

            if (matchScore.getFirstPlayerSets() == 2) {                                                         //выигрыш
                if ((matchScore.getSecondPlayerSets() == 0) || (matchScore.getSecondPlayerSets() == 1)) {


                    currentMatches.remove(uuid);   //удалили матч текущий


                    Session session = sessionFactory.getCurrentSession();
                    session.beginTransaction();

                    PlayerEntity firstPlayer = session.get(PlayerEntity.class, firstPlayerId);
                    PlayerEntity secondPlayer = session.get(PlayerEntity.class, secondPlayerId);

                    MatchEntity matchEntity = new MatchEntity();
                    matchEntity.setPlayer1(firstPlayer);
                    matchEntity.setPlayer2(secondPlayer);
                    matchEntity.setWinner(firstPlayer);

                    session.persist(matchEntity);


                    session.getTransaction().commit();


                    request.setAttribute("match", matchScore);
                    request.setAttribute("firstPlayerResult", "winner!");
                    request.setAttribute("secondPlayerResult", "lost!");

                    request.getRequestDispatcher("/WEB-INF/match-result.jsp").forward(request, response);   //передаем запрос на другой ресурс через диспетчер
                    return;
                }
            }
        }

        if (intScoredId == secondPlayerId) {
            scoringSecondPlayer();

            if (matchScore.getSecondPlayerSets() == 2) {                                                         //выигрыш
                if ((matchScore.getFirstPlayerSets() == 0) || (matchScore.getFirstPlayerSets() == 1)) {

                currentMatches.remove(uuid);   //удалили матч текущий


                Session session = sessionFactory.getCurrentSession();
                session.beginTransaction();

                PlayerEntity firstPlayer = session.get(PlayerEntity.class, firstPlayerId);
                PlayerEntity secondPlayer = session.get(PlayerEntity.class, secondPlayerId);

                MatchEntity matchEntity = new MatchEntity();
                matchEntity.setPlayer1(firstPlayer);
                matchEntity.setPlayer2(secondPlayer);
                matchEntity.setWinner(firstPlayer);

                session.persist(matchEntity);


                session.getTransaction().commit();


                request.setAttribute("match", matchScore);
                request.setAttribute("firstPlayerResult", "lost!");
                request.setAttribute("secondPlayerResult", "winner!");

                request.getRequestDispatcher("/WEB-INF/match-result.jsp").forward(request, response);
                return;
            }
            }

        }

        response.sendRedirect(request.getContextPath() + "/match-score?uuid=" + uuid);
    }


    private void scoringFirstPlayer() {

            int currentPoints = matchScore.getFirstPlayerPoints();

            if (currentPoints == 0) {
                int newPoints = 15;
                matchScore.setFirstPlayerPoints(newPoints);
            }

            if (currentPoints == 15) {
                int newPoints = 30;
                matchScore.setFirstPlayerPoints(newPoints);
            }

            if (currentPoints == 30) {
                int newPoints = 40;
                matchScore.setFirstPlayerPoints(newPoints);
            }

            if (currentPoints == 40) {                      //геймы обновляются только тут
                int secondPlayerPoints = matchScore.getSecondPlayerPoints();

                if (secondPlayerPoints < 40) {
                    updateFirstPlayerGames();
                }

                if (secondPlayerPoints == 40) {
                    if (firstPlayerAdvantage - secondPlayerAdvantage == 2) {
                        updateFirstPlayerGames();
                    } else {
                        firstPlayerAdvantage++;
                    }
                }
            }
    }

    private void scoringSecondPlayer() {

        int currentPoints = matchScore.getSecondPlayerPoints();

        if (currentPoints == 0) {
            int newPoints = 15;
            matchScore.setSecondPlayerPoints(newPoints);
        }

        if (currentPoints == 15) {
            int newPoints = 30;
            matchScore.setSecondPlayerPoints(newPoints);
        }

        if (currentPoints == 30) {
            int newPoints = 40;
            matchScore.setSecondPlayerPoints(newPoints);
        }

        if (currentPoints == 40) {                      //геймы обновляются только тут
            int firstPlayerPoints = matchScore.getFirstPlayerPoints();

            if (firstPlayerPoints < 40) {
                updateSecondPlayerGames();
            }

            if (firstPlayerPoints == 40) {
                if (secondPlayerAdvantage - firstPlayerAdvantage == 2) {
                    updateSecondPlayerGames();
                } else {
                    secondPlayerAdvantage++;
                }
            }
        }
    }

    private void updateFirstPlayerGames() {
        if ((matchScore.getFirstPlayerGames() == 6) && (matchScore.getSecondPlayerGames() == 6)) {
            updateFirstSets();
            return;
        }

        if ((matchScore.getFirstPlayerGames() == 5) && (matchScore.getFirstPlayerGames() - matchScore.getSecondPlayerGames() >= 2)) {
            updateFirstSets();
            return;
        }


        int firstPlayerGames = matchScore.getFirstPlayerGames();    //обновляем гейм в первого
        int newFirstPlayerGames = firstPlayerGames + 1;
        matchScore.setFirstPlayerGames(newFirstPlayerGames);
        matchScore.setFirstPlayerPoints(0);
        matchScore.setSecondPlayerPoints(0);
    }

    private void updateSecondPlayerGames() {
        if ((matchScore.getSecondPlayerGames() == 6) && (matchScore.getFirstPlayerGames() == 6)) {
            updateSecondSets();
            return;
        }

        if ((matchScore.getSecondPlayerGames() == 5) && (matchScore.getSecondPlayerGames() - matchScore.getFirstPlayerGames() >= 2)) {
            updateSecondSets();
            return;
        }


        int secondPlayerGames = matchScore.getSecondPlayerGames();    //обновляем гейм в первого
        int newSecondPlayerGames = secondPlayerGames + 1;
        matchScore.setSecondPlayerGames(newSecondPlayerGames);
        matchScore.setFirstPlayerPoints(0);
        matchScore.setSecondPlayerPoints(0);
    }


    private void updateFirstSets() {
        int firstPlayerCurrentSets = matchScore.getFirstPlayerSets();
        int newFirstPlayerSets = firstPlayerCurrentSets + 1;
        matchScore.setFirstPlayerSets(newFirstPlayerSets);

        matchScore.setFirstPlayerGames(0);
        matchScore.setSecondPlayerGames(0);
        matchScore.setFirstPlayerPoints(0);
        matchScore.setSecondPlayerPoints(0);
    }

    private void updateSecondSets() {
        int secondPlayerCurrentSets = matchScore.getSecondPlayerSets();
        int newSecondPlayerSets = secondPlayerCurrentSets + 1;
        matchScore.setSecondPlayerSets(newSecondPlayerSets);

        matchScore.setFirstPlayerGames(0);
        matchScore.setSecondPlayerGames(0);
        matchScore.setFirstPlayerPoints(0);
        matchScore.setSecondPlayerPoints(0);
    }

}


