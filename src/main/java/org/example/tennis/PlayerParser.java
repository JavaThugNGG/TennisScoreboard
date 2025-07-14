package org.example.tennis;

public class PlayerParser {
    public int parseId(String playerId) {
        try {
            return Integer.parseInt(playerId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ошибка при парсинге. Некорректный аргумент playerId :" + playerId);
        }
    }
}
