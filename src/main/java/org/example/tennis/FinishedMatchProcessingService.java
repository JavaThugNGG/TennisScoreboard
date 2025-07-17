package org.example.tennis;

import org.hibernate.SessionFactory;

import java.util.UUID;

public class FinishedMatchProcessingService {
    private final MatchFinishingService matchFinishingService;

    public FinishedMatchProcessingService(MatchFinishingService matchFinishingService) {
        this.matchFinishingService = matchFinishingService;
    }

    public FinishedMatchViewDto handleFinishedMatch(MatchScoreModel currentMatch, PlayerSide winnerSide, SessionFactory sessionFactory, int firstPlayerId, int secondPlayerId, OngoingMatchesService ongoingMatchesService, UUID matchIUuid) {
        PlayersResultDto playersResult = matchFinishingService.persistMatch(sessionFactory, firstPlayerId, secondPlayerId, winnerSide);
        ongoingMatchesService.removeMatch(matchIUuid);
        return new FinishedMatchViewDto(currentMatch, playersResult);
    }
}


