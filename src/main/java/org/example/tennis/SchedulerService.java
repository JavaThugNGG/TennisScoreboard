package org.example.tennis;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulerService {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public void schedule(Runnable task, long delaySeconds) {
        scheduler.schedule(task, delaySeconds, TimeUnit.SECONDS);
    }

    public void shutdown() {
        if (!scheduler.isShutdown()) {
            scheduler.shutdown();
            System.out.println("info: scheduler shutdown initiated");
            try {
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
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

