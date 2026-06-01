package com.example.spendora.repository;

import com.example.spendora.model.Card;
import org.springframework.data.repository.CrudRepository;

public interface CardRepo extends CrudRepository<Card, Long> {
}
