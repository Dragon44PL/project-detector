package com.github.projectdetector.core.project;

import java.util.List;

interface GithubRepository {
    List<GithubProject> findAllRepositories(String username);
}
