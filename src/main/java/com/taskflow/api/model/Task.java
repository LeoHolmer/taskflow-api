package com.taskflow.api.model;

import com.taskflow.api.model.enums.Priority;
import com.taskflow.api.model.enums.TaskStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignedUser;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public String getTitle() {
        return title;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Priority getPriority() {
        return priority;
    }
}
