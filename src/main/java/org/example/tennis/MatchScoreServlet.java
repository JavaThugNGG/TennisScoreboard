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
    private final SessionFactory sessionFactory = SessionFactoryManager.getInstance().getSessionFactory();

    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();
    private final MatchStateService matchStateService = new MatchStateService();
    private final MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService(matchStateService);
    private final MatchFinishingService matchFinishingService = new MatchFinishingService();
    private final FinishedMatchProcessingService finishedMatchProcessingService = new FinishedMatchProcessingService(matchFinishingService);

    private final MatchValidator matchValidator = new MatchValidator();
    private final PlayerValidator playerValidator = new PlayerValidator();

    private final MatchParser matchParser = new MatchParser();
    private final PlayerParser playerParser = new PlayerParser();

    private final MatchProcessor matchProcessor = new MatchProcessor();
    private final PlayerProcessor playerProcessor = new PlayerProcessor();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuidParameter = request.getParameter("uuid");

        matchValidator.validateUuid(uuidParameter);
        UUID uuid = matchParser.parseUuid(uuidParameter);
        MatchScoreModel currentMatch = matchProcessor.findMatch(ongoingMatchesService.getCurrentMatches(), uuid);

        request.setAttribute("match", currentMatch);
        request.getRequestDispatcher("/WEB-INF/match-score.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuidParameter = request.getParameter("uuid");
        String scoredIdParameter = request.getParameter("scoredPlayerId");

        matchValidator.validateUuid(uuidParameter);
        UUID matchUuid = matchParser.parseUuid(uuidParameter);
        playerValidator.validateId(scoredIdParameter);
        int scoredId = playerParser.parseId(scoredIdParameter);

        Map<UUID, MatchScoreModel> currentMatches = ongoingMatchesService.getCurrentMatches();
        MatchScoreModel currentMatch = matchProcessor.findMatch(currentMatches, matchUuid);

        PlayerSide scorerSide = playerProcessor.determineScorerSide(currentMatch, scoredId);

        try {
            matchScoreCalculationService.scoring(currentMatch, scorerSide);
            request.setAttribute("match", currentMatch);
            response.sendRedirect(request.getContextPath() + "/match-score?uuid=" + matchUuid);
        } catch (MatchAlreadyFinishedException e) {
            FinishedMatchViewDto finishedMatch = finishedMatchProcessingService.handleFinishedMatch(currentMatch, scorerSide, sessionFactory, ongoingMatchesService, matchUuid);
            request.setAttribute("match", finishedMatch);
            request.getRequestDispatcher("/WEB-INF/match-result.jsp").forward(request, response);
        }
    }
}


