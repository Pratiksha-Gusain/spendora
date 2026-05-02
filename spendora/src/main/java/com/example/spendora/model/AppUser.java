package com.example.spendora.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

    @Entity
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class AppUser{
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private String id;

        private String name;
        private String email;

        private Long createdAt;
        private Long updatedAt;
        private Long lastLoginAt;
        private Long lastInsightAt;

        @OneToOne(mappedBy = "appUser")
        private UserConfig userConfig;

        @OneToMany(mappedBy = "appUser", fetch = FetchType.LAZY)
        private Set<Account> accountSet;

        @OneToMany(mappedBy = "appUser", fetch = FetchType.LAZY)
        private Set<AiParsingTask> aiParsingTaskSet;

        @OneToMany(mappedBy = "appUser", fetch = FetchType.LAZY)
        private Set<AiInsightTask> aiInsightTaskSet;

        @OneToMany(mappedBy = "appUser", fetch = FetchType.LAZY)
        private Set<Transaction> transactionSet;

        @OneToMany(mappedBy = "appUser", fetch = FetchType.LAZY)
        private Set<Category> categorySet;



}


