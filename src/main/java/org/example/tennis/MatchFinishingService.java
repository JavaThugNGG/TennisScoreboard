package org.example.tennis;

import org.hibernate.SessionFactory;

import java.util.UUID;

public class MatchFinishingService {
    MatchScoreCalculationService matchScoreCalculationService;
    MatchPersistenceService matchPersistenceService = new MatchPersistenceService();

    public MatchFinishingService(MatchScoreCalculationService matchScoreCalculationService) {
        this.matchScoreCalculationService = matchScoreCalculationService;
    }

    public FinishedMatchDto handleFinishedMatch(MatchScoreModel currentMatch, PlayerSide winnerSide, SessionFactory sessionFactory, int firstPlayerId, int secondPlayerId, OngoingMatchesService ongoingMatchesService, UUID matchIUuid) {
        PlayersResultDto playersResult = matchPersistenceService.persist(sessionFactory, firstPlayerId, secondPlayerId, winnerSide);
        ongoingMatchesService.deleteMatch(matchIUuid);
        return new FinishedMatchDto(currentMatch, playersResult);
    }
}


