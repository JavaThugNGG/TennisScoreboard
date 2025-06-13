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


    OngoingMatchesService ongoingMatchesService;

    @SuppressWarnings("unchecked")
    private Map<UUID, MatchScoreModel> currentMatches;

    MatchScoreModel matchScoreModel;




    private SessionFactory sessionFactory;


    @Override
    public void init() throws ServletException {
        super.init();
        ongoingMatchesService = (OngoingMatchesService) getServletContext().getAttribute("ongoingMatchesService");
        currentMatches = ongoingMatchesService.getCurrentMatches();
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sessionFactory = (SessionFactory) getServletContext().getAttribute("SessionFactory");

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

        MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();


        if (intScoredId == firstPlayerId) {
            try {
                matchScoreCalculationService.scoringFirstPlayer(matchScoreModel);
            } catch (MatchAlreadyFinishedException e) {
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

                ongoingMatchesService.deleteMatch(uuid);


                request.setAttribute("match", matchScoreModel);
                request.setAttribute("firstPlayerResult", "winner!");
                request.setAttribute("secondPlayerResult", "lost!");

                request.getRequestDispatcher("/WEB-INF/match-result.jsp").forward(request, response);   //передаем запрос на другой ресурс через диспетчер
            }
        }

        if (intScoredId == secondPlayerId) {
            try {
                matchScoreCalculationService.scoringSecondPlayer(matchScoreModel);
            } catch (MatchAlreadyFinishedException e) {
                Session session = sessionFactory.getCurrentSession();
                session.beginTransaction();

                PlayerEntity firstPlayer = session.get(PlayerEntity.class, firstPlayerId);
                PlayerEntity secondPlayer = session.get(PlayerEntity.class, secondPlayerId);

                MatchEntity matchEntity = new MatchEntity();
                matchEntity.setPlayer1(firstPlayer);
                matchEntity.setPlayer2(secondPlayer);
                matchEntity.setWinner(secondPlayer);

                session.persist(matchEntity);


                session.getTransaction().commit();

                ongoingMatchesService.deleteMatch(uuid);


                request.setAttribute("match", matchScoreModel);
                request.setAttribute("firstPlayerResult", "lost!");
                request.setAttribute("secondPlayerResult", "winner!");

                request.getRequestDispatcher("/WEB-INF/match-result.jsp").forward(request, response);   //передаем запрос на другой ресурс через диспетчер
            }
        }
        response.sendRedirect(request.getContextPath() + "/match-score?uuid=" + uuid);
    }

}


