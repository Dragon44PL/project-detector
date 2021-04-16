package com.github.projectdetector.core.project;

class RepositoryConfig {

    private final String username;

    RepositoryConfig(String username) {
        this.username = username;
    }

    String getUsername() {
        return username;
    }
}
