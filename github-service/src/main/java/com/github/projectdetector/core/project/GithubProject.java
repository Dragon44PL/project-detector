package com.github.projectdetector.core.project;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class GithubProject {

    private String id;
    private String node_id;
    private String name;
    private String url;
    private String created_at;
    private String updated_at;
    private String pushed_at;
}
