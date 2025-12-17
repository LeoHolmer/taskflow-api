package com.taskflow.api.mapper;

import com.taskflow.api.dto.request.CreateUserRequest;
import com.taskflow.api.dto.response.UserResponse;
import com.taskflow.api.model.User;

public class UserMapper {

    private UserMapper() {
    }

    public static User toEntity(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.isActive()
        );
    }
}
