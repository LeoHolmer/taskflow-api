package com.taskflow.api.service.impl;

import com.taskflow.api.dto.request.CreateUserRequest;
import com.taskflow.api.dto.response.UserResponse;
import com.taskflow.api.exception.EmailAlreadyExistsException;
import com.taskflow.api.exception.ResourceNotFoundException;
import com.taskflow.api.mapper.UserMapper;
import com.taskflow.api.model.User;
import com.taskflow.api.repository.UserRepository;
import com.taskflow.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        log.info("Creando usuario con email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Intento de crear usuario con email duplicado: {}", request.getEmail());
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = UserMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User saved = userRepository.save(user);

        log.info("Usuario creado exitosamente con id: {}", saved.getId());
        return UserMapper.toResponse(saved);
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserMapper::toResponse);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
        return UserMapper.toResponse(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
        user.delete();
        userRepository.save(user);
        log.info("Usuario marcado como eliminado: {}", id);
    }
}
