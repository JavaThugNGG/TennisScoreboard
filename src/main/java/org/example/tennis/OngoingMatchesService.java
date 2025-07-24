package org.example.tennis;

import lombok.Getter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public class OngoingMatchesService {
    private static final OngoingMatchesService INSTANCE = new OngoingMatchesService();
    @Getter
    private final Map<UUID, MatchScoreModel> currentMatches;
    private ScheduledExecutorService scheduler;

    private OngoingMatchesService() {
        currentMatches = new ConcurrentHashMap<>();
        scheduler = Executors.newSingleThreadScheduledExecutor();
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
        scheduler.schedule(() -> currentMatches.remove(id), delaySeconds, TimeUnit.SECONDS);
    }

    public void startScheduler() {
        if (scheduler == null || scheduler.isShutdown()) {
            scheduler = Executors.newSingleThreadScheduledExecutor();
        }
    }

    public void shutdownScheduler() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

}
