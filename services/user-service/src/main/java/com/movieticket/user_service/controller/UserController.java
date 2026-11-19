package com.movieticket.user_service.controller;

import com.movieticket.user_service.dto.RegisterUserRequest;
import com.movieticket.user_service.dto.RegisterUserResponse;
import com.movieticket.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody RegisterUserRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }
}
