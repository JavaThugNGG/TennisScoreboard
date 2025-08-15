package org.example.tennis.exception;

public class IllegalPlayerNameFilterException extends IllegalArgumentException {
    public IllegalPlayerNameFilterException(String message) {
        super(message);
    }
}
