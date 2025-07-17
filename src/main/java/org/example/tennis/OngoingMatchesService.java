package org.example.tennis;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {
    private static final OngoingMatchesService INSTANCE = new OngoingMatchesService();
    private final Map<UUID, MatchScoreModel> currentMatches;

    private OngoingMatchesService() {
        currentMatches = new ConcurrentHashMap<>();
    }

    public static OngoingMatchesService getInstance() {
        return INSTANCE;
    }

    public Map<UUID, MatchScoreModel> getCurrentMatches() {
        return currentMatches;
    }

    public UUID addMatch(MatchScoreModel match) {
        UUID id = UUID.randomUUID();
        currentMatches.put(id, match);
        return id;
    }

    public void removeMatch(UUID id) {
        currentMatches.remove(id);
    }
}
