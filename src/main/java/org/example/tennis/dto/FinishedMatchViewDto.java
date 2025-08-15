package org.example.tennis.dto;

import lombok.Getter;
import org.example.tennis.model.MatchScoreModel;

@Getter
public class FinishedMatchViewDto {
    private final MatchScoreModel currentMatch;
    private final String firstPlayerResult;
    private final String secondPlayerResult;

    public FinishedMatchViewDto(MatchScoreModel currentMatch, PlayersResultDto playersResult) {
        this.currentMatch = currentMatch;
        this.firstPlayerResult = playersResult.getFirstPlayerResult();
        this.secondPlayerResult = playersResult.getSecondPlayerResult();
    }
}
