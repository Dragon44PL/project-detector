package com.github.projectdetector.core.project;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.time.Instant;

@Document(collection = "project")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Project {

    @MongoId
    private Long id;
    private Owner owner;
    private boolean availability = true;
    private String name;
    private String url;
    private Instant createdAt;
    private Instant updatedAt;
    private String description;
    private String language;

    boolean sameProject(Project project) {
        return this.id.equals(project.id);
    }
}
