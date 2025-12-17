package com.taskflow.api.service.impl;

import com.taskflow.api.dto.request.CreateUserRequest;
import com.taskflow.api.dto.response.UserResponse;
import com.taskflow.api.exception.EmailAlreadyExistsException;
import com.taskflow.api.mapper.UserMapper;
import com.taskflow.api.model.User;
import com.taskflow.api.repository.UserRepository;
import com.taskflow.api.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = UserMapper.toEntity(request);
        User saved = userRepository.save(user);

        return UserMapper.toResponse(saved);
    }


    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toResponse(user);
    }
}
