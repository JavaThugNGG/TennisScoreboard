package org.example.tennis;

import java.util.Map;
import java.util.UUID;

public class MatchProcessor {
    public MatchScoreModel findMatch(Map<UUID, MatchScoreModel> currentMatches, UUID uuid) {
        MatchScoreModel currentMatch = currentMatches.get(uuid);

        if (currentMatch == null) {
            throw new ElementNotFoundException("Матч не найден! uuid матча: " + uuid);
        }

        return currentMatch;
    }
}
