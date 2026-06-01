package com.example.spendora.service.auth;

import com.example.spendora.dto.AuthResponse;
import com.example.spendora.dto.LoginRequest;

public interface AuthService {
    AuthResponse login(LoginRequest request);
}
