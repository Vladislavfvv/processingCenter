package com.edme.pro.repository;

import com.edme.pro.model.Account;
import com.edme.pro.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findById(Long id);


    Optional<Card> findByCardNumber(String cardNumber);

    @Modifying
    @Transactional
    @Query(value = "CREATE TABLE IF NOT EXISTS processingcenterschema.card (" +
                   "id SERIAL PRIMARY KEY," +
                   "card_number varchar(50) UNIQUE not null," +
                   "expiration_date date," +
                   "holder_name varchar(50) not null," +
                   "card_status_id bigint REFERENCES processingcenterschema.card_status (id) ON DELETE CASCADE ON UPDATE CASCADE," +
                   "payment_system_id bigint REFERENCES processingcenterschema.payment_system (id) ON DELETE CASCADE ON UPDATE CASCADE," +
                   "account_id bigint REFERENCES processingcenterschema.account ON DELETE CASCADE ON UPDATE CASCADE, " +
                   "received_from_issuing_bank timestamp," +
                   " sent_to_issuing_bank timestamp)", nativeQuery = true)
    int createTable();


    @Modifying
    @Transactional
    @Query(value = "DROP TABLE IF EXISTS processingcenterschema.card CASCADE", nativeQuery = true)
    int dropTable();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO processingcenterschema.card (card_number, expiration_date, holder_name, card_status_id, payment_system_id, account_id, received_from_issuing_bank, sent_to_issuing_bank) VALUES " +
                   "('4123450000000019', '2025-12-31', 'IVAN I. IVANOV', 2, 1, 1, '2022-10-21 15:26:06.175', '2022-10-21 15:27:08.271')," +
                   "('5123450000000024', '2025-12-31', 'SEMION E. PETROV', 3,2,2, '2022-04-05 10:23:05.372', '2022-04-05 10:24:02.175')", nativeQuery = true)
    int insertDefaultValues();
}
