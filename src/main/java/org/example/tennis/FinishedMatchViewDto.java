package org.example.tennis;

import lombok.Getter;

@Getter
public class FinishedMatchViewDto {
    private final MatchScoreModel currentMatch;
    private final String firstPlayerResult;
    private final String secondPlayerResult;

    FinishedMatchViewDto(MatchScoreModel currentMatch, PlayersResultDto playersResult) {
        this.currentMatch = currentMatch;
        this.firstPlayerResult = String.valueOf(playersResult.getFirstPlayerResult());
        this.secondPlayerResult = String.valueOf(playersResult.getSecondPlayerResult());
    }
}
