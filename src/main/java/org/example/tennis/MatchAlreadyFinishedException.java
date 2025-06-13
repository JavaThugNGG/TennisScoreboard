package org.example.tennis;

public class MatchAlreadyFinishedException extends RuntimeException {
    public MatchAlreadyFinishedException() {
        super("Match is already finished.");
    }

    public MatchAlreadyFinishedException(String message) {
        super(message);
    }
}

