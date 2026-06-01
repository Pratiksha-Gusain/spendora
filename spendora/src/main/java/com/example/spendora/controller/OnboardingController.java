package com.example.spendora.controller;

import com.example.spendora.dto.OnboardingRequestDto;
import com.example.spendora.service.onboarding.OnboardingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/api/onboarding")
    @RequiredArgsConstructor
    public class OnboardingController {
        private final OnboardingService onboardingService;

        @PostMapping
        public ResponseEntity<Void> onboard(
                @RequestBody OnboardingRequestDto request,
                @AuthenticationPrincipal String userId
        ) {
            onboardingService.onboard(userId, request);
            return ResponseEntity.ok().build();
        }
    }

