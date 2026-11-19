package com.movieticket.user_service.service.impl;

import com.movieticket.user_service.dto.RegisterUserRequest;
import com.movieticket.user_service.dto.RegisterUserResponse;
import com.movieticket.user_service.entity.User;
import com.movieticket.user_service.enums.Role;
import com.movieticket.user_service.exception.EmailAlreadyExistsException;
import com.movieticket.user_service.repository.UserRepository;
import com.movieticket.user_service.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterUserResponse register(RegisterUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("user already exists with email "+request.getEmail());
        }
        User user=User.builder()
                .email(request.getEmail())
                .role(Role.CUSTOMER)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        User savedUser = userRepository.save(user);
        return RegisterUserResponse.builder()
                .userId(savedUser.getId())
                .message("User registered successfully")
                .build();
    }
}
