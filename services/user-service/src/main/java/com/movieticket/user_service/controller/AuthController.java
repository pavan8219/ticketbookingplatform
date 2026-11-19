package com.movieticket.user_service.controller;

import com.movieticket.user_service.dto.LoginRequest;
import com.movieticket.user_service.dto.LoginResponse;
import com.movieticket.user_service.dto.RefreshTokenRequest;
import com.movieticket.user_service.dto.RefreshTokenResponse;
import com.movieticket.user_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {

        return ResponseEntity.ok(
                authService.refreshToken(request)
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest request){
        authService.logout(request);
        return ResponseEntity.ok("logout successful");
    }



    @GetMapping("/hello")
    public String hello() {
        return "JWT Authentication Successful";
    }
}
