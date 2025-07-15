package org.example.tennis;

public class PageParser {

    public int parsePage(String requestedPageParameter) {
        try {
            return Integer.parseInt(requestedPageParameter);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректный параметр страницы!", e);
        }
    }
}
