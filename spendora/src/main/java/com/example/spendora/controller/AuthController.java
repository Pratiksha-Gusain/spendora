package com.example.spendora.controller;

import com.example.spendora.dto.LoginRequest;
import com.example.spendora.dto.RegisterRequest;
import com.example.spendora.service.appuser.AppUserServiceImpl;
import com.example.spendora.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AppUserServiceImpl userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        // Register the user with the provided registration details
        userService.registerUser(request);
        // Create a login request from the registered email and password
        final var loginRequest = new LoginRequest(request.email(), request.password());
        // Authenticate the user using the login credentials
        final var authResponse = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        var authResponse = authService.login(request);
        return ResponseEntity.ok(authResponse);
    }
}
