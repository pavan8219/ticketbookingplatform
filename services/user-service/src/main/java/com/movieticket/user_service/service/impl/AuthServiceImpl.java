package com.movieticket.user_service.service.impl;

import com.movieticket.user_service.config.JwtProperties;
import com.movieticket.user_service.dto.LoginRequest;
import com.movieticket.user_service.dto.LoginResponse;
import com.movieticket.user_service.dto.RefreshTokenRequest;
import com.movieticket.user_service.dto.RefreshTokenResponse;
import com.movieticket.user_service.entity.RefreshToken;
import com.movieticket.user_service.entity.User;
import com.movieticket.user_service.exception.InvalidRefreshTokenException;
import com.movieticket.user_service.repository.RefreshTokenRepository;
import com.movieticket.user_service.repository.UserRepository;
import com.movieticket.user_service.security.CustomUserDetails;
import com.movieticket.user_service.security.CustomUserDetailsService;
import com.movieticket.user_service.service.AuthService;
import com.movieticket.user_service.service.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;


    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        // Generate Access Token
        String accessToken = jwtService.generateAccessToken(userDetails);

        // Generate Refresh Token
        String refreshToken = UUID.randomUUID().toString();

        // Save Refresh Token
        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .token(refreshToken)
                .user(user)
                .expiryDate(Instant.now().plusMillis(jwtProperties.getRefreshTokenExpiration()))
                .revoked(false)
                .build();

        refreshTokenRepository.save(refreshTokenEntity);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() -> new InvalidRefreshTokenException("Invalid refresh token"));

        if (refreshToken.isRevoked()) {
            throw new InvalidRefreshTokenException("Refresh token has been revoked");
        }

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            throw new InvalidRefreshTokenException("Refresh token has expired");
        }

        User user = refreshToken.getUser();
        CustomUserDetails userDetails = new CustomUserDetails(user);
        String accessToken = jwtService.generateAccessToken(userDetails);

        return RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .build();
    }

    @Override
    @Transactional
    public void logout(RefreshTokenRequest request) {
        RefreshToken refreshToken=refreshTokenRepository.findByToken(request.getRefreshToken()).orElseThrow(()->new RuntimeException("No token refresh found"));
        if(!refreshToken.isRevoked())
            refreshToken.setRevoked(true);
    }
}
