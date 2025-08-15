package org.example.tennis.parser;

import java.util.UUID;

public class MatchParser {

    public UUID parseUuid(String uuidParameter) {
        try {
            return UUID.fromString(uuidParameter);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ошибка при парсинге. Некорректный uuid :" + uuidParameter);
        }
    }
}

