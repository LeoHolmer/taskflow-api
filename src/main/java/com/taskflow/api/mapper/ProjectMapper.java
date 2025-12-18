package com.taskflow.api.mapper;

import com.taskflow.api.dto.request.CreateProjectRequest;
import com.taskflow.api.dto.response.ProjectResponse;
import com.taskflow.api.model.Project;

public class ProjectMapper {

    private ProjectMapper() {
    }

    public static Project toEntity(CreateProjectRequest request) {
        return Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public static ProjectResponse toResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .taskCount(project.getTasks() != null ? project.getTasks().size() : 0)
                .build();
    }
}
