package org.example.tennis;

import java.util.UUID;

public class UuidParser {

    public UUID parse(String uuidParameter) {
        try {
            return UUID.fromString(uuidParameter);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ошибка при парсинге. Некорректный uuid :" + uuidParameter);
        }
    }
}

