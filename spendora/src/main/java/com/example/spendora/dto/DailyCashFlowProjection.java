package com.example.spendora.dto;

import java.time.LocalDate;

public interface DailyCashFlowProjection {
    LocalDate getTransactionDate();
    Double getIncome();
    Double getExpense();
}
