package com.example.spendora.service.onboarding;

import com.example.spendora.dto.OnboardingRequestDto;

public interface OnboardingService {
    void onboard(String userId, OnboardingRequestDto request);
}
