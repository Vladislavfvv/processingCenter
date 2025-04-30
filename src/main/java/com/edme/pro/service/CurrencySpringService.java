package com.edme.pro.service;

import com.edme.pro.dao.DaoInterfaceSpring;

import com.edme.pro.dto.CurrencyDto;
import com.edme.pro.model.Currency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class CurrencySpringService {
    private final DaoInterfaceSpring<Long, Currency> currencyDao;

    public CurrencySpringService(DaoInterfaceSpring<Long, Currency> currencyDao) {
        this.currencyDao = currencyDao;
    }

//    @Transactional
//    public Optional<Currency> save(String digitalCode, String letterCode, String currencyName) {
//        if (currencyDao.findByValue(currencyName).isPresent()) {
//            return Optional.empty();
//        }
//        Currency currency = new Currency();
//        currency.setCurrencyDigitalCode(digitalCode);
//        currency.setCurrencyLetterCode(letterCode);
//        currency.setCurrencyName(currencyName);
//        Currency saved = currencyDao.insert(currency);
//        return Optional.ofNullable(saved);
//    }



    @Transactional
    public Optional<Currency> save(CurrencyDto currencyDto) {
        // Проверяем, есть ли уже сущность с такими же полями
       // List<CurrencyDto> optionalCurrency = currency.findMatching(currencyDto);
        Optional<Currency> exiting = currencyDao.findByValue(currencyDto.getCurrencyName());
        if(exiting.isPresent()) {
            log.info("Currency already exists with value: " + currencyDto.getCurrencyName());
            return Optional.empty();
        }


        Currency newCurrency = new Currency();
        newCurrency.setCurrencyDigitalCode(currencyDto.getCurrencyDigitalCode());
        newCurrency.setCurrencyLetterCode(currencyDto.getCurrencyLetterCode());
        newCurrency.setCurrencyName(currencyDto.getCurrencyName());
        Currency saved = currencyDao.insert(newCurrency);
        log.info("Currency saved: " + saved.getCurrencyName());
        return Optional.of(saved);
    }

    public Optional<Currency> findById(Long id) {
        return currencyDao.findById(id);
    }

    public List<Currency> findAll() {
        return currencyDao.findAll();
    }

    @Transactional
    public boolean delete(Long id) {
        return currencyDao.delete(id);
    }

    public Optional<Currency> findByValue(String name) {
        return currencyDao.findByValue(name);
    }

    @Transactional
    public Optional<Currency> update(Long id, CurrencyDto currencyDto) {
        if (id == null) {//если id не задан
            log.info("Comparable Currency must have an ID");
            //throw new IllegalArgumentException("ID должен быть задан для обновления");
            return Optional.empty();
        }
        Optional<Currency> exiting = currencyDao.findById(id);
        if (exiting.isEmpty()) {
            log.info("Currency does not exist with id: {}", id);
            return Optional.empty();
        }


//        Optional<Currency> existing = currencyDao.findById(currency.getId());
//        if (existing.isEmpty()) {
//            log.info("Currency does not exist with value: " + currency.getCurrencyName());
//            return Optional.empty();
//        }
        //Currency updated = currencyDao.update(currency);
        Currency updated = new Currency();
        updated.setId(id);
        updated.setCurrencyDigitalCode(currencyDto.getCurrencyDigitalCode());
        updated.setCurrencyName(currencyDto.getCurrencyName());
        updated.setCurrencyLetterCode(currencyDto.getCurrencyLetterCode());
        Currency saved = currencyDao.update(updated);
        log.info("Currency updated: " + saved.getId());
        return Optional.of(saved);
    }

    @Transactional
    public boolean deleteAll() {
        return currencyDao.deleteAll();
    }

    @Transactional
    public boolean dropTable() {
        return currencyDao.dropTable();
    }

}


