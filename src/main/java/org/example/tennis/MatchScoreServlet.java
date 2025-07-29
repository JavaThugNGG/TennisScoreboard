package org.example.tennis;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(MatchScoreServlet.class);

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

    private final ErrorDtoBuilder errorDtoBuilder = new ErrorDtoBuilder();

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

        UUID matchUuid;
        int scoredId;

        try {
            matchValidator.validateUuid(uuidParameter);
            matchUuid = matchParser.parseUuid(uuidParameter);
            playerValidator.validateId(scoredIdParameter);
            scoredId = playerParser.parseId(scoredIdParameter);
        } catch (IllegalArgumentException e) {
            ErrorDto error = errorDtoBuilder.build(e);
            response.setStatus(error.getStatusCode());
            request.setAttribute("errorMessage", error.getMessage());
            logger.warn("incorrect match uuid or scoredId: {} {}", uuidParameter, scoredIdParameter);
            request.getRequestDispatcher("/WEB-INF/new-match.jsp").forward(request, response);
            return;
        }

        Map<UUID, MatchScoreModel> currentMatches = ongoingMatchesService.getCurrentMatches();
        MatchScoreModel currentMatch = matchProcessor.findMatch(currentMatches, matchUuid);
        PlayerSide scorerSide = playerProcessor.determineScorerSide(currentMatch, scoredId);

        try {
            matchScoreCalculationService.scoring(currentMatch, scorerSide);
            logger.info("match is scoring: first player id {}, second player id {}, scoring by id: {}", currentMatch.getFirstPlayerId(), currentMatch.getSecondPlayerId(), scoredIdParameter);
            response.sendRedirect(request.getContextPath() + "/match-score?uuid=" + matchUuid);
        } catch (MatchAlreadyFinishedException e) {
            handleFinishedMatch(request, response, currentMatch, scorerSide, matchUuid);
        }
    }

    private void handleFinishedMatch(HttpServletRequest request, HttpServletResponse response, MatchScoreModel currentMatch, PlayerSide scorerSide, UUID matchUuid) throws ServletException, IOException {
        synchronized (currentMatch.getLock()) {
            if (!currentMatch.isFinished()) {
                FinishedMatchViewDto finishedMatch = finishedMatchProcessingService.handleFinishedMatch(currentMatch, scorerSide, sessionFactory, ongoingMatchesService, matchUuid);
                currentMatch.setFinished(true);
                request.setAttribute("match", finishedMatch);
                logger.info("match is finished: first player id {}, second player id {}", finishedMatch.getCurrentMatch().getFirstPlayerId(), finishedMatch.getCurrentMatch().getSecondPlayerId());
                request.getRequestDispatcher("/WEB-INF/match-result.jsp").forward(request, response);
                return;
            }
        }
        response.sendRedirect(request.getContextPath() + "/match-score?uuid=" + matchUuid);
    }
}


