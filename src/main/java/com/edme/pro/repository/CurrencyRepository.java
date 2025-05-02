package com.edme.pro.repository;

import com.edme.pro.model.Currency;
import com.edme.pro.model.IssuingBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Optional<Currency> findById(Long id);


    Optional<Currency> findByCurrencyName(String currencyName);

    @Modifying
    @Transactional
    @Query(value = "CREATE TABLE IF NOT EXISTS processingcenterschema.currency (" +
                   "id SERIAL PRIMARY KEY, " +
                   "currency_digital_code varchar(3) not null," +
                   "currency_letter_code varchar(3) not null," +
                   "currency_name VARCHAR(255) UNIQUE NOT NULL)", nativeQuery = true)
    int createTable();


    @Modifying
    @Transactional
    @Query(value = "DROP TABLE IF EXISTS processingcenterschema.currency CASCADE", nativeQuery = true)
    int dropTable();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO processingcenterschema.currency (currency_digital_code, currency_letter_code, currency_name) VALUES " +
                   "('643', 'RUB', 'Russian Ruble')," +
                   "       ('980', 'UAN', 'Hryvnia')," +
                   "       ('840', 'USD', 'US Dollar')," +
                   "       ('978', 'EUR', 'Euro')," +
                   "       ('392', 'JPY', 'Yen')," +
                   "       ('156', 'CNY', 'Yuan Renminbi')," +
                   "       ('826', 'GBP', 'Pound Sterling')", nativeQuery = true)
    int insertDefaultValues();
}
