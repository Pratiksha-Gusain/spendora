package com.example.spendora.service.analytics;

import com.example.spendora.dto.AiInsightResponseDto;
import com.example.spendora.dto.CategoryDistributionDto;
import com.example.spendora.dto.DailyCashFlowProjection;
import com.example.spendora.dto.MonthlyCashFlowProjection;

import java.time.LocalDate;
import java.util.List;

public interface AnalyticsService {
    List<DailyCashFlowProjection> getDailyCashFlow(String userId, LocalDate startDate, LocalDate endDate);
    List<MonthlyCashFlowProjection> getMonthlyCashFlow(String userId, LocalDate startDate, LocalDate endDate);
    List<AiInsightResponseDto> getAiInsights(String userId);
    List<CategoryDistributionDto> getCategoryDistribution(String userId, LocalDate startDate, LocalDate endDate);
}
