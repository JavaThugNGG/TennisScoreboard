package org.example.tennis;

public class IllegalPlayerNameException extends IllegalArgumentException {
    public IllegalPlayerNameException(String message) {
        super(message);
    }
}
