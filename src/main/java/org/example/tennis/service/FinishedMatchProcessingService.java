package org.example.tennis.service;

import org.example.tennis.model.MatchScoreModel;
import org.example.tennis.PlayerSide;
import org.example.tennis.dto.FinishedMatchViewDto;
import org.example.tennis.dto.PlayersResultDto;
import org.hibernate.SessionFactory;

import java.util.UUID;

public class FinishedMatchProcessingService {
    private final MatchFinishingService matchFinishingService;

    public FinishedMatchProcessingService(MatchFinishingService matchFinishingService) {
        this.matchFinishingService = matchFinishingService;
    }

    public FinishedMatchViewDto handleFinishedMatch(MatchScoreModel currentMatch, PlayerSide winnerSide, SessionFactory sessionFactory, OngoingMatchesService ongoingMatchesService, UUID matchIUuid) {
        PlayersResultDto playersResult = matchFinishingService.persistMatch(sessionFactory, currentMatch, winnerSide);
        ongoingMatchesService.removeMatchWithDelay(matchIUuid, 1);
        return new FinishedMatchViewDto(currentMatch, playersResult);
    }
}


