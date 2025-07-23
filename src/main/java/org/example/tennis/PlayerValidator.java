package org.example.tennis;

public class PlayerValidator {
    private final static int MAX_PLAYER_NAME_LENGTH = 16;

    public void validateName(String name) {
        if (name == null || name.trim().isEmpty() || name.length() > MAX_PLAYER_NAME_LENGTH) {
            throw new IllegalArgumentException("Некорректное имя игрока: " + name);
        }
    }

    public void validateId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Некорректное значение id игрока: " + id);
        }
    }
}
