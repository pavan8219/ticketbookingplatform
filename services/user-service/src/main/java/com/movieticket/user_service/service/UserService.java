package com.movieticket.user_service.service;

import com.movieticket.user_service.dto.RegisterUserRequest;
import com.movieticket.user_service.dto.RegisterUserResponse;

public interface UserService {
    RegisterUserResponse register(RegisterUserRequest request);
}
