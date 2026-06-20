package com.example.spendora.dto;

public interface MonthlyCashFlowProjection {
    Integer getYear();
    Integer getMonth();
    Double getIncome();
    Double getExpense();
}
