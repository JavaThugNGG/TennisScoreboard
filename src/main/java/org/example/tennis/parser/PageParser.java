package org.example.tennis.parser;

public class PageParser {

    public int parsePage(String requestedPageParameter) {
        try {
            return Integer.parseInt(requestedPageParameter);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный параметр страницы!", e);
        }
    }
}
