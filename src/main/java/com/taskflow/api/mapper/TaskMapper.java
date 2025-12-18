package com.taskflow.api.mapper;

import com.taskflow.api.dto.request.CreateTaskRequest;
import com.taskflow.api.dto.response.TaskResponse;
import com.taskflow.api.model.Project;
import com.taskflow.api.model.Task;
import com.taskflow.api.model.User;

public class TaskMapper {

    private TaskMapper() {
    }

    public static Task toEntity(CreateTaskRequest request, User user, Project project) {
        return Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .assignedUser(user)
                .project(project)
                .build();
    }

    public static TaskResponse toResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .userId(task.getAssignedUser().getId())
                .userName(task.getAssignedUser().getName())
                .projectId(task.getProject().getId())
                .projectName(task.getProject().getName())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}
