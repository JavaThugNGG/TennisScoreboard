package org.example.tennis;

public class PlayerNameFilterValidator {

    public void validateFilter(String filter) {
        if (filter.trim().isEmpty()) {
            throw new IllegalArgumentException("Фильтр не может быть пустым");
        }

        if (!filter.matches("[a-zA-Zа-яА-ЯёЁ\\- ]+")) {
            throw new IllegalArgumentException("Фильтр содержит недопустимые символы");
        }
    }
}
