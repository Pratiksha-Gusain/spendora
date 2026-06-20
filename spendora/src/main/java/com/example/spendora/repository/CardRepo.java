package com.example.spendora.repository;

import com.example.spendora.model.Card;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardRepo extends CrudRepository<Card, Long> {
    List<Card> findAllByAppUserId(String userId);}
