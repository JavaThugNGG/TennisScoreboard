package org.example.tennis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

public class MatchProcessor {
    private static final Logger logger = LoggerFactory.getLogger(MatchProcessor.class);

    public MatchScoreModel findMatch(Map<UUID, MatchScoreModel> currentMatches, UUID uuid) {
        MatchScoreModel currentMatch = currentMatches.get(uuid);
        if (currentMatch == null) {
            logger.error("match not found in curent matches, uuid: {}", uuid.toString());
            throw new PlayerNotFoundException("Матч не найден! uuid матча: " + uuid);
        }

        return currentMatch;
    }
}
