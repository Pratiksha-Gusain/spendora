package com.example.spendora.service.account.strategy;

import com.example.spendora.service.account.strategy.AccountBalanceStrategy;
import com.example.spendora.service.transaction.TransactionBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AccountBalanceStrategyFactory {
    private final Map<TransactionBehavior, AccountBalanceStrategy> balanceStrategies;
    public AccountBalanceStrategyFactory(List<AccountBalanceStrategy> balanceStrategies){
        this.balanceStrategies = balanceStrategies.stream()
                .collect(Collectors.toMap(AccountBalanceStrategy::getType, e->e));
    }
    public AccountBalanceStrategy getBalanceStrategy(TransactionBehavior type){
        return balanceStrategies.get(type);
    }

}
