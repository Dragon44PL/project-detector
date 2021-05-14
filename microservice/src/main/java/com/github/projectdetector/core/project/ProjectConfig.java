package com.github.projectdetector.core.project;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@EnableScheduling
@Configuration
class ProjectConfig {

    @Value("${project.detector.username:}")
    private String username;

    @Value("${project.detector.api.url:}")
    private String url;

    private static final long DEFAULT_INITIAL_DELAY = 0;
    private static final long DEFAULT_PERIOD = 15;
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MINUTES;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    GithubRepository githubRepository(RestTemplate restTemplate) {
        final String url = createUrl();
        return new RestTemplateGithubRepository(restTemplate, url);
    }

    private String createUrl() {
        return String.format(url, username);
    }

    @Bean
    ProjectScheduler projectScheduler(GithubRepository githubRepository, ProjectMapper projectMapper, ProjectFacade projectFacade) {
        final RepositoryConfig repositoryConfig = new RepositoryConfig(username);
        return new ProjectScheduler(githubRepository, projectMapper, projectFacade, repositoryConfig);
    }

    @Bean
    ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Bean
    ProjectSchedulerExecutor projectSchedulerExecutor(ScheduledExecutorService scheduledExecutorService, ProjectScheduler projectScheduler) {
        final SchedulerPeriodConfig schedulerPeriodConfig = SchedulerPeriodConfig.builder().initialDelay(DEFAULT_INITIAL_DELAY)
                                                                .period(DEFAULT_PERIOD).timeUnit(DEFAULT_TIME_UNIT).build();

        final ProjectSchedulerExecutor projectSchedulerExecutor =  new ProjectSchedulerExecutor(scheduledExecutorService, projectScheduler);
        projectSchedulerExecutor.setSchedulerPeriodConfig(schedulerPeriodConfig);
        return projectSchedulerExecutor;
    }
}

