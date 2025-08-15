package org.example.tennis.validator;

public class PageValidator {

    public void validatePage(int page) {
        if (page < 1) {
            throw new IllegalArgumentException("Некорректный аргумент страницы матча");
        }
    }
}
