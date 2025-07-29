package org.example.tennis;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public class OngoingMatchesService {
    private final static Logger logger = LoggerFactory.getLogger(OngoingMatchesService.class);
    private static final OngoingMatchesService INSTANCE = new OngoingMatchesService();
    @Getter
    private final Map<UUID, MatchScoreModel> currentMatches;
    private ScheduledExecutorService scheduler;

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
        scheduler.schedule(() -> currentMatches.remove(id), delaySeconds, TimeUnit.SECONDS);
    }

    public void startScheduler() {
        if (scheduler == null || scheduler.isShutdown()) {
            scheduler = Executors.newSingleThreadScheduledExecutor();
            logger.info("scheduler started");
        } else {
            logger.info("scheduler already running");
        }
    }

    public void shutdownScheduler() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            System.out.println("info: scheduler shutdown initiated");
            try {
                if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                    System.out.println("warn: scheduler did not terminate within 10 seconds, forcing shutdown");
                    scheduler.shutdownNow();
                } else {
                    System.out.println("info: scheduler terminated");
                }
            } catch (InterruptedException e) {
                System.out.println("error: interrupted while waiting for scheduler termination");
                e.printStackTrace(System.out);
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        } else {
            System.out.println("warn: scheduler is already shut down or not initialized");
        }
    }

}
