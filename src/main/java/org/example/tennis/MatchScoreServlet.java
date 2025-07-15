package org.example.tennis;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {
    SessionFactory sessionFactory = SessionFactoryManager.getInstance().getSessionFactory();

    OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();
    MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
    MatchEndingService matchEndingService = new MatchEndingService();
    MatchFinishingService matchFinishingService = new MatchFinishingService(matchEndingService);

    UuidValidator uuidValidator = new UuidValidator();
    PlayerValidator playerValidator = new PlayerValidator();

    UuidParser uuidParser = new UuidParser();
    PlayerParser playerParser = new PlayerParser();

    MatchProcessor matchProcessor = new MatchProcessor();
    PlayerProcessor playerProcessor = new PlayerProcessor();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuidParameter = request.getParameter("uuid");

        uuidValidator.validate(uuidParameter);
        UUID uuid = uuidParser.parse(uuidParameter);
        MatchScoreModel currentMatch = matchProcessor.findMatch(ongoingMatchesService.getCurrentMatches(), uuid);

        request.setAttribute("match", currentMatch);
        request.getRequestDispatcher("/WEB-INF/match-score.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuidParameter = request.getParameter("uuid");
        String scoredIdParameter = request.getParameter("scoredPlayerId");

        uuidValidator.validate(uuidParameter);
        UUID matchUuid = uuidParser.parse(uuidParameter);
        playerValidator.validateId(scoredIdParameter);
        int scoredId = playerParser.parseId(scoredIdParameter);

        Map<UUID, MatchScoreModel> currentMatches = ongoingMatchesService.getCurrentMatches();
        MatchScoreModel currentMatch = matchProcessor.findMatch(currentMatches, matchUuid);

        int firstPlayerId = currentMatch.getFirstPlayerId();
        int secondPlayerId = currentMatch.getSecondPlayerId();
        PlayerSide scorerSide = playerProcessor.determineScorerSide(scoredId, firstPlayerId, secondPlayerId);

        try {
            matchScoreCalculationService.scoring(currentMatch, scorerSide);
            request.setAttribute("match", currentMatch);
            response.sendRedirect(request.getContextPath() + "/match-score?uuid=" + matchUuid);
        } catch (MatchAlreadyFinishedException e) {
            FinishedMatchDto finishedMatch = matchFinishingService.handleFinishedMatch(currentMatch, scorerSide, sessionFactory, firstPlayerId, secondPlayerId, ongoingMatchesService, matchUuid);
            request.setAttribute("match", finishedMatch);
            request.getRequestDispatcher("/WEB-INF/match-result.jsp").forward(request, response);
        }
    }
}


