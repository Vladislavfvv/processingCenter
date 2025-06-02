package com.edme.pro.repository;

import com.edme.pro.model.Account;
import com.edme.pro.model.IssuingBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(Long id);


    Optional<Account> findByAccountNumber(String accountNumber);

    @Modifying
    @Transactional
    @Query(value = "CREATE TABLE IF NOT EXISTS processingcenterschema.account (" +
                   "id SERIAL PRIMARY KEY," +
                   "account_number varchar(50) UNIQUE not null," +
                   "balance decimal," +
                   "currency_id bigint REFERENCES processingcenterschema.currency (id) ON DELETE CASCADE ON UPDATE CASCADE," +
                   "issuing_bank_id bigint REFERENCES processingcenterschema.issuing_bank (id) ON DELETE CASCADE ON UPDATE CASCADE)", nativeQuery = true)
    int createTable();


    @Modifying
    @Transactional
    @Query(value = "DROP TABLE IF EXISTS processingcenterschema.account CASCADE", nativeQuery = true)
    int dropTable();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO processingcenterschema.account (account_number, balance, currency_id, issuing_bank_id) VALUES " +
                   "('40817810800000000001', 649.7, 1, 1)," +
                   "('40817810100000000002', 48702.07, 1, 1)," +
                   "('40817810400000000003', 715000.01, 1, 1)," +
                   "('40817810400000000003', 10000.0, 3, 1)", nativeQuery = true)
    int insertDefaultValues();
}
