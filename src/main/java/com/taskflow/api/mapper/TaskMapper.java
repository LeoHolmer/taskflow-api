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
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        task.setAssignedUser(user);
        task.setProject(project);
        return task;
    }

    public static TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getAssignedUser().getId(),
                task.getProject().getId()
        );
    }
}
