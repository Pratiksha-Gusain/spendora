package com.example.spendora.dto;

public record AuthResponse(String accessToken, String tokenType, long expiresInSeconds, boolean onboarded) {
}
