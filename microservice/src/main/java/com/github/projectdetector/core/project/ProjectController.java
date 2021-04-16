package com.github.projectdetector.core.project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
class ProjectController {

    private final ProjectFacade projectFacade;
    private final ProjectScheduler projectScheduler;
    private final ProjectSchedulerStopper projectSchedulerStopper;

    ProjectController(ProjectFacade projectFacade, ProjectScheduler projectScheduler, ProjectSchedulerStopper projectSchedulerStopper) {
        this.projectFacade = projectFacade;
        this.projectScheduler = projectScheduler;
        this.projectSchedulerStopper = projectSchedulerStopper;
    }

    @GetMapping("/project")
    List<Project> findProjects(@RequestParam(required = false) String username) {
        return (username != null && !username.equals(""))
            ? projectFacade.findProjectsByUser(username)
            : projectFacade.findAllProjects();
    }

    @PostMapping("/project/username/{username}")
    ResponseEntity<String> scheduleFor(@PathVariable(value = "username", required = true) String username) {
        final RepositoryConfig repositoryConfig = new RepositoryConfig(username);
        this.projectScheduler.setRepositoryConfig(repositoryConfig);
        final String message = "User has been changed to \"" + username + "\"";
        return ResponseEntity.ok(message);
    }

    @PostMapping("/project/stop")
    ResponseEntity<String> stopScheduler() {
        projectSchedulerStopper.stop();
        final String message = "Scheduler stopped";
        return ResponseEntity.ok(message);
    }

    @PostMapping("/project/start")
    ResponseEntity<String> startScheduler() {
        projectSchedulerStopper.start();
        final String message = "Scheduler work in progress";
        return ResponseEntity.ok(message);
    }
}
