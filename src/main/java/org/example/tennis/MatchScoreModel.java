package org.example.tennis;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchScoreModel {
    private int firstPlayerId;
    private int secondPlayerId;

    private int firstPlayerSets;
    private int secondPlayerSets;

    private int firstPlayerGames;
    private int secondPlayerGames;

    private int firstPlayerPoints;
    private int secondPlayerPoints;

    private int firstPlayerAdvantage;
    private int secondPlayerAdvantage;

    private boolean tiebreak;

    public MatchScoreModel(int firstPlayerId, int secondPlayerId) {
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
    }
}

