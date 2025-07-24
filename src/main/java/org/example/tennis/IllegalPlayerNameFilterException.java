package org.example.tennis;

public class IllegalPlayerNameFilterException extends IllegalArgumentException {
    public IllegalPlayerNameFilterException(String message) {
        super(message);
    }
}
