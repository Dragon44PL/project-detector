package com.github.projectdetector.core.project;

import java.util.*;
import java.util.stream.Collectors;

class ProjectScheduler implements Runnable {

    private final GithubRepository githubRepository;
    private final ProjectMapper projectMapper;
    private final ProjectFacade projectFacade;
    private RepositoryConfig repositoryConfig;

    ProjectScheduler(GithubRepository githubRepository, ProjectMapper projectMapper, ProjectFacade projectFacade, RepositoryConfig repositoryConfig) {
        this.githubRepository = githubRepository;
        this.projectMapper = projectMapper;
        this.projectFacade = projectFacade;
        this.repositoryConfig = repositoryConfig;
    }

    @Override
    public void run() {
        this.checkForGithubRepos();
    }

    void checkForGithubRepos() {
        final List<GithubProject> githubProjects = githubRepository.findAllRepositories(repositoryConfig.getUsername());
        final List<Project> convertedProjects = projectMapper.convertAll(githubProjects);
        final List<Project> originalProjects = projectFacade.findProjectsByUser(repositoryConfig.getUsername());
        saveProjects(originalProjects, convertedProjects);
    }

    private List<Project> updateProjects(Map<Project, Project> projects) {
        return projects.keySet().stream().map((original) -> {
            final Project converted = projects.get(original);
            return updateProject(original, converted);
        }).collect(Collectors.toList());
    }

    private Project updateProject(Project original, Project retrieved) {
        original.setCreatedAt(retrieved.getCreatedAt());
        original.setName(retrieved.getName());
        original.setUrl(retrieved.getUrl());
        original.setLanguage(retrieved.getLanguage());
        original.setDescription(retrieved.getDescription());
        original.setUpdatedAt(retrieved.getUpdatedAt());
        return original;
    }

    private void saveProjects(List<Project> original, List<Project> converted) {
        final Map<Project, Project> toUpdate = new HashMap<>();
        final List<Project> toSave = new ArrayList<>();

        converted.forEach((currentConverted) -> {
            final Optional<Project> foundExists = original.stream().filter(currentConverted::sameProject).findAny();
            foundExists.ifPresentOrElse(current -> toUpdate.put(current, currentConverted), () -> toSave.add(currentConverted));
        });

        markDeleted(converted, original);
        toSave.addAll(updateProjects(toUpdate));
        projectFacade.saveProjects(toSave);
    }

    private void markDeleted(List<Project> converted, List<Project> original) {
        original.forEach((origin) -> {
            if(converted.stream().noneMatch((origin::sameProject))) {
                origin.setAvailability(false);
            }
        });
    }

    void setRepositoryConfig(RepositoryConfig repositoryConfig) {
        this.repositoryConfig = repositoryConfig;
        this.checkForGithubRepos();
    }

    RepositoryConfig getRepositoryConfig() {
        return repositoryConfig;
    }
}
