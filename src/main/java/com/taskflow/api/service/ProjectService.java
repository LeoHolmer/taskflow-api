package com.taskflow.api.service;

import com.taskflow.api.dto.request.CreateProjectRequest;
import com.taskflow.api.dto.response.ProjectResponse;

import java.util.List;

public interface ProjectService {

    ProjectResponse createProject(CreateProjectRequest request);

    List<ProjectResponse> getAllProjects();

    ProjectResponse getProjectById(Long id);
}
