package com.example.spendora.service.appuser;

import com.example.spendora.dto.RegisterRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {
    void registerUser(RegisterRequest request);
}
