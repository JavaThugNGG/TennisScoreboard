package org.example.tennis;

import lombok.Getter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public class OngoingMatchesService {
    private static final OngoingMatchesService INSTANCE = new OngoingMatchesService();
    @Getter
    private final Map<UUID, MatchScoreModel> currentMatches;
    private final SchedulerService schedulerService = new SchedulerService();

    private OngoingMatchesService() {
        currentMatches = new ConcurrentHashMap<>();
    }

    public static OngoingMatchesService getInstance() {
        return INSTANCE;
    }

    public UUID addMatch(MatchScoreModel match) {
        UUID id = UUID.randomUUID();
        currentMatches.put(id, match);
        return id;
    }

    public void removeMatchWithDelay(UUID id, long delaySeconds) {
        schedulerService.schedule(() -> currentMatches.remove(id), delaySeconds);
    }

    public void shutdownScheduler() {
        schedulerService.shutdown();
    }
}
