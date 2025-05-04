package com.edme.pro.service;

import com.edme.pro.dao.DaoInterfaceSpring;

import com.edme.pro.dto.CurrencyDto;
import com.edme.pro.model.Currency;
import com.edme.pro.repository.CurrencyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class CurrencySpringService {
    @Autowired
    private CurrencyRepository currencyRepository;

    @Transactional
    public Optional<Currency> save(CurrencyDto currencyDto) {
        Optional<Currency> exiting = currencyRepository.findByCurrencyName(currencyDto.getCurrencyName());
        if(exiting.isPresent()) {
            log.info("Currency already exists with value: " + currencyDto.getCurrencyName());
            return Optional.empty();
        }


        Currency newCurrency = new Currency();
        newCurrency.setCurrencyDigitalCode(currencyDto.getCurrencyDigitalCode());
        newCurrency.setCurrencyLetterCode(currencyDto.getCurrencyLetterCode());
        newCurrency.setCurrencyName(currencyDto.getCurrencyName());
        Currency saved = currencyRepository.saveAndFlush(newCurrency);
        log.info("Currency saved: " + saved.getCurrencyName());
        return Optional.of(saved);
    }

    public Optional<Currency> findById(Long id) {
        return currencyRepository.findById(id);
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    @Transactional
    public Optional<Currency> update(Long id, CurrencyDto currencyDto) {
        Optional<Currency> exiting = currencyRepository.findById(id);
        if (exiting.isEmpty()) {
            log.info("Currency does not exist with id: {}", id);
            return Optional.empty();
        }

        Currency updated = exiting.get();
        //updated.setId(id);
        updated.setCurrencyDigitalCode(currencyDto.getCurrencyDigitalCode());
        updated.setCurrencyName(currencyDto.getCurrencyName());
        updated.setCurrencyLetterCode(currencyDto.getCurrencyLetterCode());
        currencyRepository.save(updated);
        log.info("Currency updated: " + updated.getId());
        return Optional.of(updated);
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<Currency> exiting = currencyRepository.findById(id);
        if (exiting.isPresent()) {
            currencyRepository.delete(exiting.get());
            log.info("Currency deleted: " + exiting.get().getCurrencyName());
            return true;
        } else {
            log.info("Currency does not exist with id: {}", id);
            return false;
        }
    }

    @Transactional
    public boolean deleteAll() {
        try {
            currencyRepository.deleteAll();
            log.info("Currency deleted.");
            return true;
        } catch (Exception e) {
            log.error("Ошибка при удалении всех записей из currencies", e);
            return false;
        }
    }

    @Transactional
    public boolean createTable() {
        try {
            currencyRepository.createTable();
            log.info("Currency table created.");
            return true;
        } catch (Exception e) {
            log.error("Error when attempt to create table paymentSystem: {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean dropTable() {
        try {
            currencyRepository.dropTable();
            log.info("Currency table dropped.");
            return true;
        } catch (Exception e) {
            log.error("Error when attempt to drop table paymentSystem: {}", e.getMessage());
            return false;
        }
    }


    @Transactional
    public boolean initializeTable() {
       createTable();
        try {
            currencyRepository.insertDefaultValues();
            log.info("Currency table initialized.");
            return true;
        } catch (Exception e) {
            log.error("Error when attempt to initialize table paymentSystem: {}", e.getMessage());
            return false;
        }
    }
}


