package com.github.projectdetector.core.project;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
class ApplicationContextReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    private final ProjectSchedulerExecutor projectSchedulerExecutor;

    ApplicationContextReadyListener(ProjectSchedulerExecutor projectSchedulerExecutor) {
        this.projectSchedulerExecutor = projectSchedulerExecutor;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        this.projectSchedulerExecutor.start();
    }
}
