package com.github.projectdetector.core.project;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

class ProjectSchedulerExecutor {

    private final ScheduledExecutorService scheduledExecutorService;
    private final ProjectScheduler projectScheduler;
    private SchedulerPeriodConfig schedulerPeriodConfig;
    private ScheduledFuture<?> scheduledFuture;

    ProjectSchedulerExecutor(ScheduledExecutorService scheduledExecutorService, ProjectScheduler projectScheduler) {
        this.scheduledExecutorService = scheduledExecutorService;
        this.projectScheduler = projectScheduler;
    }

    void setSchedulerPeriodConfig(SchedulerPeriodConfig schedulerPeriodConfig) {
        this.schedulerPeriodConfig = schedulerPeriodConfig;
    }

    private void prepareProjectScheduler() {
        this.scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(projectScheduler, schedulerPeriodConfig.getInitialDelay(), schedulerPeriodConfig.getPeriod(), schedulerPeriodConfig.getTimeUnit());
    }

    void stop() {
        scheduledFuture.cancel(true);
    }

    void start() {
        prepareProjectScheduler();
    }

}
