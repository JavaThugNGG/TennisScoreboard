package org.example.tennis;

import lombok.Getter;

@Getter
public class FinishedMatchDto {
    private final MatchScoreModel currentMatch;
    private final String firstPlayerResult;
    private final String secondPlayerResult;

    FinishedMatchDto(MatchScoreModel currentMatch, PlayersResultDto playersResult) {
        this.currentMatch = currentMatch;
        this.firstPlayerResult = String.valueOf(playersResult.getFirstPlayerResult());
        this.secondPlayerResult = String.valueOf(playersResult.getSecondPlayerResult());
    }
}
