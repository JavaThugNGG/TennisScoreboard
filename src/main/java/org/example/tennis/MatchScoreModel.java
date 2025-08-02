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

    private boolean firstPlayerAdvantage;
    private boolean secondPlayerAdvantage;

    private boolean tiebreak;

    private boolean finished;

    private final Object lock = new Object();

    public MatchScoreModel(int firstPlayerId, int secondPlayerId) {
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
    }

    public String getFirstPlayerDisplayPoints() {
        return getDisplayPoints(firstPlayerPoints, firstPlayerAdvantage);
    }

    public String getSecondPlayerDisplayPoints() {
        return getDisplayPoints(secondPlayerPoints, secondPlayerAdvantage);
    }

    private String getDisplayPoints(int points, boolean hasAdvantage) {
        if (hasAdvantage) {
            return "adv";
        }

        return String.valueOf(points);
    }
}

