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
    private String nodeId;
    private String name;
    private String url;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant pushedAt;
    private String user;

    public boolean sameProject(Project project) {
        return this.id.equals(project.id);
    }
}
