package org.example.tennis;

import org.hibernate.SessionFactory;

import java.util.UUID;

public class MatchFinishingService {
    private final MatchEndingService matchEndingService;

    public MatchFinishingService(MatchEndingService matchEndingService) {
        this.matchEndingService = matchEndingService;
    }

    public FinishedMatchDto handleFinishedMatch(MatchScoreModel currentMatch, PlayerSide winnerSide, SessionFactory sessionFactory, int firstPlayerId, int secondPlayerId, OngoingMatchesService ongoingMatchesService, UUID matchIUuid) {
        PlayersResultDto playersResult = matchEndingService.persist(sessionFactory, firstPlayerId, secondPlayerId, winnerSide);
        ongoingMatchesService.deleteMatch(matchIUuid);
        return new FinishedMatchDto(currentMatch, playersResult);
    }
}


