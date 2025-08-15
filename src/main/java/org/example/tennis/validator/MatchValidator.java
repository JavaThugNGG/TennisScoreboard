package org.example.tennis.validator;

public class MatchValidator {

    public void validateUuid(String uuid) {
        if (uuid == null || uuid.isBlank()) {
            throw new IllegalArgumentException("Некорректное значение UUID: " + uuid);
        }
    }
}
