package org.example.tennis;

import lombok.Getter;

@Getter
public class FinishedMatchDto {
    private final MatchScoreModel currentMatch;
    private final String firstPlayerResult;
    private final String secondPlayerResult;

    FinishedMatchDto(MatchScoreModel currentMatch, PlayersResultDto playersResultDto) {
        this.currentMatch = currentMatch;
        this.firstPlayerResult = String.valueOf(playersResultDto.getFirstPlayerResult());
        this.secondPlayerResult = String.valueOf(playersResultDto.getSecondPlayerResult());
    }
}
