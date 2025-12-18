package com.taskflow.api.service;

import com.taskflow.api.dto.request.CreateUserRequest;
import com.taskflow.api.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    Page<UserResponse> getAllUsers(Pageable pageable);

    UserResponse getUserById(Long id);

    void deleteUser(Long id);
}
