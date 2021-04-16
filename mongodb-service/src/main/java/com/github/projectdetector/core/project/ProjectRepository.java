package com.github.projectdetector.core.project;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

interface ProjectRepository extends MongoRepository<Project, Long> {
    List<Project> findAllByUser(String user);
}
