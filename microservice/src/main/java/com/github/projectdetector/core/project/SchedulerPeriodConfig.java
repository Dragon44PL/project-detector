package com.github.projectdetector.core.project;

import lombok.Builder;
import lombok.Getter;
import java.util.concurrent.TimeUnit;

@Getter
@Builder
class SchedulerPeriodConfig {
    private final long initialDelay;
    private final long period;
    private final TimeUnit timeUnit;
}
