package org.example.tennis.exception;

public class IllegalPlayerNameException extends IllegalArgumentException {
    public IllegalPlayerNameException(String message) {
        super(message);
    }
}
