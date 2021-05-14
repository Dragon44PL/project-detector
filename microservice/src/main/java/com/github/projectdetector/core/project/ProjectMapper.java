package com.github.projectdetector.core.project;

import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
class ProjectMapper {

    public Optional<Project> convertFrom(GithubProject githubProject) {
        final Long id = Long.parseLong(githubProject.getId());

        final Instant createdAt = Instant.parse(githubProject.getCreated_at());
        final Instant updatedAt = Instant.parse(githubProject.getUpdated_at());

        return Optional.ofNullable(Project.builder().availability(true).id(id)
            .language(githubProject.getLanguage()).description(githubProject.getDescription()).owner(owner(githubProject.getOwner()))
            .name(githubProject.getName()).createdAt(createdAt).updatedAt(updatedAt).url(githubProject.getUrl()).build());
    }

    private Owner owner(GithubOwner githubOwner) {
        return Owner.builder().login(githubOwner.getLogin()).build();
    }

    public List<Project> convertAll(List<GithubProject> githubProjects) {
        return githubProjects.stream().map(this::convertFrom).filter(Optional::isPresent).map(Optional::get)
            .collect(Collectors.toList());
    }
}
