package com.movieticket.user_service.service;

import com.movieticket.user_service.dto.LoginRequest;
import com.movieticket.user_service.dto.LoginResponse;
import com.movieticket.user_service.dto.RefreshTokenRequest;
import com.movieticket.user_service.dto.RefreshTokenResponse;
import jakarta.validation.Valid;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    RefreshTokenResponse refreshToken(RefreshTokenRequest request);

    void logout(@Valid RefreshTokenRequest request);
}
