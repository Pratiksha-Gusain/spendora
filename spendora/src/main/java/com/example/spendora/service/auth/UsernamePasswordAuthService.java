package com.example.spendora.service.auth;

import com.example.spendora.config.JwtProps;
import com.example.spendora.dto.AuthResponse;
import com.example.spendora.dto.LoginRequest;
import com.example.spendora.repository.AppUserRepo;
import com.example.spendora.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UsernamePasswordAuthService implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final SecretKey secretKey;
    private final JwtProps jwtProps;
    private final AppUserRepo appUserRepo;

    @Override
    public AuthResponse login(LoginRequest request) {
        // Create unauthenticated email(username) and password token
        final var unauthenticatedToken = UsernamePasswordAuthenticationToken.unauthenticated(request.email(),
                request.password());

        // Authenticated Token
        final var authenticatedToken = authenticationManager.authenticate(unauthenticatedToken);

        // Generate Access Token & Refresh Token
        // Extract email from authenticated token
        final var email = ((UserDetails) Objects.requireNonNull(authenticatedToken.getPrincipal())).getUsername(); // subject

        final Collection<? extends GrantedAuthority> roles = authenticatedToken.getAuthorities();

        final var expirationTimeAccessToken = jwtProps.getExpirationTimeAccessToken();
        final var accessToken = JwtUtils.generateAccessToken(email, roles, secretKey, expirationTimeAccessToken);

        final var onboarded = appUserRepo.findById(email)
                .map(com.example.spendora.model.AppUser::isOnboardingComplete)
                .orElse(false);

        return new AuthResponse(
                accessToken,
                "Bearer",
                expirationTimeAccessToken,
                onboarded
        );

    }

}
