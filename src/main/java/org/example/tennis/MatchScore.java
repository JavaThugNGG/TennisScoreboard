package org.example.tennis;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class MatchScore {
    String playerOneId;
    String playerTwoId;

    public MatchScore(String playerOneId, String playerTwoId, int scorePlayerOne, int scorePlayerTwo) {
        this.playerOneId = playerOneId;
        this.playerTwoId = playerTwoId;
        this.scorePlayerOne = scorePlayerOne;
        this.scorePlayerTwo = scorePlayerTwo;
    }

    int scorePlayerOne;
    int scorePlayerTwo;
}

