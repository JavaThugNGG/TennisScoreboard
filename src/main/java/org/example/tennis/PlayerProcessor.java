package org.example.tennis;

public class PlayerProcessor {
    public PlayerSide resolveScorerSide(int scorerId, int firstPlayerId, int secondPlayerId) {
        if (scorerId == firstPlayerId) {
            return PlayerSide.FIRST;
        } else if (scorerId == secondPlayerId) {
            return PlayerSide.SECOND;
        } else {
            throw new ElementNotFoundException("Игрок не найден. scorerId: " + scorerId);
        }
    }
}
