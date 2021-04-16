package com.github.projectdetector.core.project;

import org.springframework.stereotype.Component;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Component
class ProjectSchedulerStopper {

    private final ScheduledExecutorService scheduledExecutorService;
    private final ProjectScheduler projectScheduler;
    private ScheduledFuture<?> scheduledFuture;

    ProjectSchedulerStopper(ScheduledExecutorService scheduledExecutorService, ProjectScheduler projectScheduler) {
        this.scheduledExecutorService = scheduledExecutorService;
        this.projectScheduler = projectScheduler;
    }

    private void prepareProjectScheduler() {
        this.scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(projectScheduler, 0, 15, TimeUnit.MINUTES);
    }

    void stop() {
        scheduledFuture.cancel(true);
    }

    void start() {
        prepareProjectScheduler();
    }

}
