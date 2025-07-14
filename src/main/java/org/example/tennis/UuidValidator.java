package org.example.tennis;

public class UuidValidator {//допишется логика на длину символов и тд

    public void validate(String uuid) {
        if (uuid == null || uuid.isBlank()) {
            throw new IllegalArgumentException("Некорректное значение UUID: " + uuid);
        }
    }
}
