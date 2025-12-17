package com.taskflow.api.repository;

import com.taskflow.api.model.Task;
import com.taskflow.api.model.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByAssignedUserId(Long userId);

    List<Task> findByProjectId(Long projectId);
}
