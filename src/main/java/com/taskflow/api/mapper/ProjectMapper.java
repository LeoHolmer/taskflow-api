package com.taskflow.api.mapper;

import com.taskflow.api.dto.request.CreateProjectRequest;
import com.taskflow.api.dto.response.ProjectResponse;
import com.taskflow.api.model.Project;

public class ProjectMapper {

    private ProjectMapper() {
    }

    public static Project toEntity(CreateProjectRequest request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        return project;
    }

    public static ProjectResponse toResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription()
        );
    }
}
