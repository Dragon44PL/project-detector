package com.github.projectdetector.core.project;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class ProjectSchedulerExecutor {

    private final ScheduledExecutorService scheduledExecutorService;
    private final ProjectScheduler projectScheduler;
    private SchedulerPeriodConfig schedulerPeriodConfig;
    private ScheduledFuture<?> scheduledFuture;
    private boolean isRunning;

    ProjectSchedulerExecutor(ScheduledExecutorService scheduledExecutorService, ProjectScheduler projectScheduler) {
        this.scheduledExecutorService = scheduledExecutorService;
        this.projectScheduler = projectScheduler;
        this.isRunning = false;
    }

    void setSchedulerPeriodConfig(SchedulerPeriodConfig schedulerPeriodConfig) {
        this.schedulerPeriodConfig = schedulerPeriodConfig;
    }

    private void prepareProjectScheduler() {
        final long initialDelay = schedulerPeriodConfig.getInitialDelay();
        final long period = schedulerPeriodConfig.getPeriod();
        final TimeUnit timeUnit = schedulerPeriodConfig.getTimeUnit();
        this.scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(projectScheduler, initialDelay, period, timeUnit);
    }

    void stop() {
        scheduledFuture.cancel(true);
        this.isRunning = false;
    }

    void start() {
        prepareProjectScheduler();
        this.isRunning = true;
    }

    boolean isRunning() {
        return this.isRunning;
    }

    void restart() {
        stop();
        start();
    }

}
