package com.github.projectdetector.core.project;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
class ProjectFacade {

    private final ProjectRepository projectRepository;

    public ProjectFacade(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> findProjectsByUser(String user) {
        return projectRepository.findAllByOwnerLoginAndAvailabilityIsTrue(user);
    }

    public void saveProjects(List<Project> projects) {
        projectRepository.saveAll(projects);
    }

}
