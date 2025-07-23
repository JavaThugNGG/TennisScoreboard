package org.example.tennis;

public class PlayerValidator {
    private final static int MAX_PLAYER_NAME_LENGTH = 16;

    public void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя игрока не может быть пустым.");
        }
        if (name.length() > MAX_PLAYER_NAME_LENGTH) {
            throw new IllegalArgumentException("Имя игрока слишком длинное. Максимум " + MAX_PLAYER_NAME_LENGTH + " символов.");
        }
        if (!name.matches("[a-zA-Zа-яА-ЯёЁ\\- ]+")) {
            throw new IllegalArgumentException("Имя игрока содержит недопустимые символы.");
        }
    }

    public void validateId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Некорректное значение id игрока: " + id);
        }
    }
}
