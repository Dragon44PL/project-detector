package com.github.projectdetector.core.project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
class ProjectController {

    private final ProjectFacade projectFacade;
    private final ProjectScheduler projectScheduler;
    private final ProjectSchedulerExecutor projectSchedulerExecutor;

    ProjectController(ProjectFacade projectFacade, ProjectScheduler projectScheduler, ProjectSchedulerExecutor projectSchedulerExecutor) {
        this.projectFacade = projectFacade;
        this.projectScheduler = projectScheduler;
        this.projectSchedulerExecutor = projectSchedulerExecutor;
    }

    @GetMapping("/projects")
    List<Project> findProjects(@RequestParam(required = false) String username) {
        return (username != null && !username.equals(""))
            ? projectFacade.findProjectsByUser(username)
            : projectFacade.findAllProjects();
    }

    @PostMapping("/projects/username/{username}")
    ResponseEntity<String> scheduleFor(@PathVariable(value = "username", required = true) String username) {
        final RepositoryConfig repositoryConfig = new RepositoryConfig(username);
        this.projectScheduler.setRepositoryConfig(repositoryConfig);
        this.projectSchedulerExecutor.restart();
        return ResponseEntity.ok("User has been changed to \"" + username + "\"");
    }

    @PostMapping("/projects/stop")
    ResponseEntity<String> stopScheduler() {
        if(projectSchedulerExecutor.isRunning()) {
            projectSchedulerExecutor.stop();
        }

        return ResponseEntity.ok("Scheduler stopped");
    }

    @PostMapping("/projects/start")
    ResponseEntity<String> startScheduler() {
        if(!projectSchedulerExecutor.isRunning()) {
            projectSchedulerExecutor.start();
        }

        return ResponseEntity.ok("Scheduler work in progress");
    }
}
