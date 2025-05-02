package com.edme.pro.repository;

import com.edme.pro.model.CardStatus;
import com.edme.pro.model.PaymentSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CardStatusRepository extends JpaRepository<CardStatus, Integer> {
    Optional<CardStatus> findById(Long id);


    Optional<CardStatus> findByCardStatusName(String cardStatusName);

    @Modifying
    @Transactional
    @Query(value = "CREATE TABLE IF NOT EXISTS processingcenterschema.card_status (" +
                   "id SERIAL PRIMARY KEY, " +
                   "card_status_name VARCHAR(255) UNIQUE NOT NULL)", nativeQuery = true)
    int createTable();

    @Modifying
    @Transactional
    @Query(value = "DROP TABLE IF EXISTS processingcenterschema.card_status CASCADE", nativeQuery = true)
    int dropTable();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO processingcenterschema.card_status (card_status_name) VALUES " +
                   "('Card is not active')," +
                   "       ('Card is valid')," +
                   "       ('Card is temporarily blocked')," +
                   "       ('Card is lost')," +
                   "       ('Card is compromised')", nativeQuery = true)
    int insertDefaultValues();
}
