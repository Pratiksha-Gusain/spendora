package com.example.spendora.service.ai.analytics;

public interface AiAnalyticsService {
    void storeAiInsights(String userId);
    void generateInsightsForEligibleUsers();
}
