package org.example.tennis;


import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class OngoingMatchesService {
    private final Map<UUID, MatchScoreModel> currentMatches;

    public OngoingMatchesService(Map<UUID, MatchScoreModel> currentMatches) {
        this.currentMatches = currentMatches;
    }

    public Map<UUID, MatchScoreModel> getCurrentMatches() {
        return currentMatches;
    }

    public UUID addMatch(MatchScoreModel match) {
        UUID id = UUID.randomUUID();
        currentMatches.put(id, match);
        return id;
    }

    public MatchScoreModel findMatch(UUID id) {
        return currentMatches.get(id);
    }

    public void deleteMatch(UUID id) {
        currentMatches.remove(id);
    }
}

