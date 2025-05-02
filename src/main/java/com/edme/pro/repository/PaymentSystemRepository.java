package com.edme.pro.repository;

import com.edme.pro.model.PaymentSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PaymentSystemRepository extends JpaRepository<PaymentSystem, Integer> {
    Optional<PaymentSystem> findById(Long id);


    Optional<PaymentSystem> findByPaymentSystemName(String name);

    @Modifying
    @Transactional
    @Query(value = "CREATE TABLE IF NOT EXISTS processingcenterschema.payment_system (" +
                   "id SERIAL PRIMARY KEY, " +
                   "payment_system_name VARCHAR(255) UNIQUE NOT NULL)", nativeQuery = true)
    int createTable();

    @Modifying
    @Transactional
    @Query(value = "DROP TABLE IF EXISTS processingcenterschema.payment_system CASCADE", nativeQuery = true)
    int dropTable();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO processingcenterschema.payment_system (payment_system_name) VALUES " +
                   "('VISA International Service Association')," +
                   "       ('Mastercard')," +
                   "       ('JCB')," +
                   "       ('American Express')," +
                   "       ('Diners Club International')," +
                   "       ('China UnionPay'), " +
                   "       ('VISA'), " +
                   "       ('Maestro')", nativeQuery = true)
    int insertDefaultValues();
}
