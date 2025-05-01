package com.edme.pro.repository;

import com.edme.pro.model.CardStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardStatusRepository extends JpaRepository<CardStatus, Integer> {
}
