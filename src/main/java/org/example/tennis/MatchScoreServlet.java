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
    MatchScoreModel matchScoreModel;

    private int firstPlayerAdvantage = 0;
    private int secondPlayerAdvantage = 0;

    private int firstPlayerTaibreak;
    private int secondPlayerTaibreak;


    private SessionFactory sessionFactory;
    private Map<UUID, MatchScoreModel> currentMatches;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sessionFactory = (SessionFactory) getServletContext().getAttribute("SessionFactory");
        currentMatches = (Map<UUID, MatchScoreModel>) getServletContext().getAttribute("currentMatches");

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

        matchScoreModel = currentMatches.get(uuid);

        if (matchScoreModel == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND, "match score не найден");
            return;
        }


        request.setAttribute("match", matchScoreModel);   //ключ значение положили значение для передачи
        request.getRequestDispatcher("/WEB-INF/match-score.jsp").forward(request, response);   //передаем запрос на другой ресурс через диспетчер
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sessionFactory = (SessionFactory) getServletContext().getAttribute("SessionFactory");
        currentMatches = (Map<UUID, MatchScoreModel>) getServletContext().getAttribute("currentMatches");

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
        MatchScoreModel matchScoreModel = currentMatches.get(uuid);
        if (matchScoreModel == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Матч не найден.");
            return;
        }

        int firstPlayerId = matchScoreModel.getFirstPlayerId();
        int secondPlayerId = matchScoreModel.getSecondPlayerId();


        if (intScoredId == firstPlayerId) {
            scoringFirstPlayer();

            if (matchScoreModel.getFirstPlayerSets() == 2) {                                                         //выигрыш
                if ((matchScoreModel.getSecondPlayerSets() == 0) || (matchScoreModel.getSecondPlayerSets() == 1)) {


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


                    request.setAttribute("match", matchScoreModel);
                    request.setAttribute("firstPlayerResult", "winner!");
                    request.setAttribute("secondPlayerResult", "lost!");

                    request.getRequestDispatcher("/WEB-INF/match-result.jsp").forward(request, response);   //передаем запрос на другой ресурс через диспетчер
                    return;
                }
            }
        }

        if (intScoredId == secondPlayerId) {
            scoringSecondPlayer();

            if (matchScoreModel.getSecondPlayerSets() == 2) {                                                         //выигрыш
                if ((matchScoreModel.getFirstPlayerSets() == 0) || (matchScoreModel.getFirstPlayerSets() == 1)) {

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


                request.setAttribute("match", matchScoreModel);
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

            int currentPoints = matchScoreModel.getFirstPlayerPoints();

            if (currentPoints == 0) {
                int newPoints = 15;
                matchScoreModel.setFirstPlayerPoints(newPoints);
            }

            if (currentPoints == 15) {
                int newPoints = 30;
                matchScoreModel.setFirstPlayerPoints(newPoints);
            }

            if (currentPoints == 30) {
                int newPoints = 40;
                matchScoreModel.setFirstPlayerPoints(newPoints);
            }

            if (currentPoints == 40) {                      //геймы обновляются только тут
                int secondPlayerPoints = matchScoreModel.getSecondPlayerPoints();

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

        int currentPoints = matchScoreModel.getSecondPlayerPoints();

        if (currentPoints == 0) {
            int newPoints = 15;
            matchScoreModel.setSecondPlayerPoints(newPoints);
        }

        if (currentPoints == 15) {
            int newPoints = 30;
            matchScoreModel.setSecondPlayerPoints(newPoints);
        }

        if (currentPoints == 30) {
            int newPoints = 40;
            matchScoreModel.setSecondPlayerPoints(newPoints);
        }

        if (currentPoints == 40) {                      //геймы обновляются только тут
            int firstPlayerPoints = matchScoreModel.getFirstPlayerPoints();

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
        if ((matchScoreModel.getFirstPlayerGames() == 6) && (matchScoreModel.getSecondPlayerGames() == 6)) {
            updateFirstSets();
            return;
        }

        if ((matchScoreModel.getFirstPlayerGames() == 5) && (matchScoreModel.getFirstPlayerGames() - matchScoreModel.getSecondPlayerGames() >= 2)) {
            updateFirstSets();
            return;
        }


        int firstPlayerGames = matchScoreModel.getFirstPlayerGames();    //обновляем гейм в первого
        int newFirstPlayerGames = firstPlayerGames + 1;
        matchScoreModel.setFirstPlayerGames(newFirstPlayerGames);
        matchScoreModel.setFirstPlayerPoints(0);
        matchScoreModel.setSecondPlayerPoints(0);
    }

    private void updateSecondPlayerGames() {
        if ((matchScoreModel.getSecondPlayerGames() == 6) && (matchScoreModel.getFirstPlayerGames() == 6)) {
            updateSecondSets();
            return;
        }

        if ((matchScoreModel.getSecondPlayerGames() == 5) && (matchScoreModel.getSecondPlayerGames() - matchScoreModel.getFirstPlayerGames() >= 2)) {
            updateSecondSets();
            return;
        }


        int secondPlayerGames = matchScoreModel.getSecondPlayerGames();    //обновляем гейм в первого
        int newSecondPlayerGames = secondPlayerGames + 1;
        matchScoreModel.setSecondPlayerGames(newSecondPlayerGames);
        matchScoreModel.setFirstPlayerPoints(0);
        matchScoreModel.setSecondPlayerPoints(0);
    }


    private void updateFirstSets() {
        int firstPlayerCurrentSets = matchScoreModel.getFirstPlayerSets();
        int newFirstPlayerSets = firstPlayerCurrentSets + 1;
        matchScoreModel.setFirstPlayerSets(newFirstPlayerSets);

        matchScoreModel.setFirstPlayerGames(0);
        matchScoreModel.setSecondPlayerGames(0);
        matchScoreModel.setFirstPlayerPoints(0);
        matchScoreModel.setSecondPlayerPoints(0);
    }

    private void updateSecondSets() {
        int secondPlayerCurrentSets = matchScoreModel.getSecondPlayerSets();
        int newSecondPlayerSets = secondPlayerCurrentSets + 1;
        matchScoreModel.setSecondPlayerSets(newSecondPlayerSets);

        matchScoreModel.setFirstPlayerGames(0);
        matchScoreModel.setSecondPlayerGames(0);
        matchScoreModel.setFirstPlayerPoints(0);
        matchScoreModel.setSecondPlayerPoints(0);
    }

}


