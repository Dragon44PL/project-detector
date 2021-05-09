package com.github.projectdetector.core.project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class RestTemplateGithubRepository implements GithubRepository {

    private final RestTemplate restTemplate;
    private final String url;

    RestTemplateGithubRepository(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public List<GithubProject> findAllRepositories(String username) {
        final String formattedUrl = String.format(url, username);
        final ResponseEntity<GithubProject[]> projectEntity = restTemplate.getForEntity(formattedUrl, GithubProject[].class);
        final Optional<GithubProject[]> projects = Optional.ofNullable(projectEntity.getBody());
        return projects.map(value -> new ArrayList<>(List.of(value))).orElseGet(ArrayList::new);
    }
}
