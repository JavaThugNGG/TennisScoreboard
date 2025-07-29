package org.example.tennis;

public class PlayerProcessor {

    public PlayerSide determineScorerSide(MatchScoreModel match, int scorerId) {
        int firstPlayerId = match.getFirstPlayerId();
        int secondPlayerId = match.getSecondPlayerId();

        if (scorerId == firstPlayerId) {
            return PlayerSide.FIRST;
        } else if (scorerId == secondPlayerId) {
            return PlayerSide.SECOND;
        } else {
            throw new PlayerNotFoundException("Игрок не найден. scorerId: " + scorerId);
        }
    }
}
