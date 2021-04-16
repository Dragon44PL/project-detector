package com.github.projectdetector.core.project;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@EnableScheduling
@Configuration
class ProjectConfig {

    @Value("${project.detector.username:}")
    private String username;

    @Bean
    ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Bean
    ProjectScheduler projectScheduler(GithubRepository githubRepository, ProjectMapper projectMapper, ProjectFacade projectFacade) {
        final RepositoryConfig repositoryConfig = new RepositoryConfig(username);
        return new ProjectScheduler(githubRepository, projectMapper, projectFacade, repositoryConfig);
    }
}

