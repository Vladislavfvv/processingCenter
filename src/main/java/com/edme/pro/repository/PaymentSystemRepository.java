package com.edme.pro.repository;

import com.edme.pro.model.PaymentSystem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentSystemRepository extends JpaRepository<PaymentSystem, Integer> {
    Optional<PaymentSystem> findById(Long id);
}
