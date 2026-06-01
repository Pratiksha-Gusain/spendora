package com.example.spendora.dto;

import com.example.spendora.model.CardType;
import com.example.spendora.model.LanguagePreference;

public record OnboardingRequestDto(
        Long bankId,
        String lastFourDigits,
        Double bankBalance,
        CardType cardType,
        String cardLastFourDigits,
        Double cardLimit,
        Double cashBalance,
        Long defaultPaymentModeId,
        LanguagePreference languagePreference
) {
}
