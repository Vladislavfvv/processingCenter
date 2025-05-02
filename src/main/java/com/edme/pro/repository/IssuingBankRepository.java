package com.edme.pro.repository;

import com.edme.pro.model.IssuingBank;
import com.edme.pro.model.PaymentSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface IssuingBankRepository extends JpaRepository<IssuingBank, Integer> {
    Optional<IssuingBank> findById(Long id);


    Optional<IssuingBank> findByBic(String bic);

    @Modifying
    @Transactional
    @Query(value = "CREATE TABLE IF NOT EXISTS processingcenterschema.issuing_bank (" +
                   "id SERIAL PRIMARY KEY, " +
                   "bic varchar(9) UNIQUE not null,"+
                   "bin varchar(5) not null," +
                   "abbreviated_name VARCHAR(255) NOT NULL)", nativeQuery = true)
    int createTable();


    @Modifying
    @Transactional
    @Query(value = "DROP TABLE IF EXISTS processingcenterschema.issuing_bank CASCADE", nativeQuery = true)
    int dropTable();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO processingcenterschema.issuing_bank (bic, bin, abbreviated_name) VALUES " +
                   "('041234569', '12345', 'ОАО Приорбанк ')," +
                   "('041234570', '45256', 'ОАО Сбербанк ')," +
                   "('041234571', '98725', 'ЗАО МТБ Банк ')"
                  , nativeQuery = true)
    int insertDefaultValues();
}
