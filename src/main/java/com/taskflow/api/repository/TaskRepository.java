package com.taskflow.api.repository;

import com.taskflow.api.model.Task;
import com.taskflow.api.model.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.status = :status AND t.deletedAt IS NULL")
    List<Task> findByStatus(@Param("status") TaskStatus status);

    @Query("SELECT t FROM Task t WHERE t.assignedUser.id = :userId AND t.deletedAt IS NULL")
    List<Task> findByAssignedUserId(@Param("userId") Long userId);

    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND t.deletedAt IS NULL")
    List<Task> findByProjectId(@Param("projectId") Long projectId);
}
