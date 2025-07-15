package org.example.tennis;

public class PlayerValidator {

    public void validateId(String playerId) {
        if (playerId == null || playerId.isBlank()) {
            throw new IllegalArgumentException("Некорректное значение playerId: " + playerId);
        }
    }
}
