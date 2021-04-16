package com.github.projectdetector.core.project;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
class RestTemplateGithubRepository implements GithubRepository {

    private static final String REPOS = "https://api.github.com/users/%s/repos";

    private final RestTemplate restTemplate;

    RestTemplateGithubRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<GithubProject> findAllRepositories(String username) {
        final String formattedUrl = String.format(REPOS, username);
        final ResponseEntity<GithubProject[]> projectEntity = restTemplate.getForEntity(formattedUrl, GithubProject[].class);
        final Optional<GithubProject[]> projects = Optional.ofNullable(projectEntity.getBody());
        return projects.map(value -> new ArrayList<>(List.of(value))).orElseGet(ArrayList::new);
    }
}
