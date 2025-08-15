package org.example.tennis.validator;

import org.example.tennis.exception.IllegalPlayerNameException;
import org.example.tennis.exception.IllegalPlayerNameFilterException;

public class PlayerValidator {
    private final static int MAX_PLAYER_NAME_LENGTH = 16;

    public void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalPlayerNameException("Имя игрока не может быть пустым.");
        }
        if (name.length() > MAX_PLAYER_NAME_LENGTH) {
            throw new IllegalPlayerNameException("Имя игрока слишком длинное. Максимум " + MAX_PLAYER_NAME_LENGTH + " символов.");
        }
        if (!name.matches("[a-zA-Zа-яА-ЯёЁ ]+")) {
            throw new IllegalPlayerNameException("Имя игрока содержит недопустимые символы.");
        }
    }

    public void validateId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Некорректное значение id игрока: " + id);
        }
    }

    public void validateNameFilter(String filter) {
        if (filter.trim().isEmpty()) {
            throw new IllegalPlayerNameFilterException("Некорректный фильтр - пустая строка");
        }

        if (!filter.matches("[a-zA-Zа-яА-ЯёЁ\\- ]+")) {
            throw new IllegalPlayerNameFilterException("Фильтр содержит недопустимые символы");
        }
    }
}
