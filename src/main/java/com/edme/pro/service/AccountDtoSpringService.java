package com.edme.pro.service;


import com.edme.pro.dao.DaoInterfaceSpring;
import com.edme.pro.dto.AccountDto;
import lombok.extern.slf4j.Slf4j;
import com.edme.pro.mapper.AccountMapper;
import com.edme.pro.model.Account;
import com.edme.pro.model.Currency;
import com.edme.pro.model.IssuingBank;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountDtoSpringService {
    private final DaoInterfaceSpring<Long, Currency> currencyDao;
    private final DaoInterfaceSpring<Long, IssuingBank> issuingBankDao;
    private final DaoInterfaceSpring<Long, Account> accountDao;

    public AccountDtoSpringService(DaoInterfaceSpring<Long, Currency> currencyDao, DaoInterfaceSpring<Long, IssuingBank> issuingBankDao, DaoInterfaceSpring<Long, Account> accountDao) {
        this.currencyDao = currencyDao;
        this.issuingBankDao = issuingBankDao;
        this.accountDao = accountDao;
    }

    @Transactional
    public Optional<Account> save(AccountDto accountDto) {
//        if (accountDto.getId() != null) {
//            Optional<Account> existsAccount = accountDao.findById(accountDto.getId());
//            if (existsAccount.isPresent()) {
//                log.info("Account with ID {} already exists", accountDto.getId());
//                return Optional.of(AccountMapper.toDto(existsAccount.get()));
//            }
//        }

        //проверка есть ли такой еще
        Optional<Account> existingAccount = accountDao.findByValue(accountDto.getAccountNumber());
        if (existingAccount.isPresent()) {
            log.info("Account with ID {} already exists", existingAccount.get().getId());
            return Optional.empty();
        }
        //иначе создаем новый
        // но сразу проверяем есть ли для него currency и issuingBank
        accountDto.setId(null);
        Currency currency = currencyDao.findById(accountDto.getCurrencyId()).orElse(Currency.builder().build()); // или заглушка
        IssuingBank issuingBank = issuingBankDao.findById(accountDto.getIssuingBankId()).orElse(IssuingBank.builder().build()); //то же


        Account account = AccountMapper.toEntity(accountDto, currency, issuingBank);
        Account accountSaved = accountDao.insert(account);

        log.info("Account with ID {} saved", accountSaved.getId());
        return Optional.of(accountSaved);
    }

    public List<Account> findAll() {
//        try{
//            return accountDao.findAll().stream().map(AccountMapper::toDto).collect(Collectors.toList());
//        }catch (Exception e) {
//            log.info("Error finding all accounts");
//            return new ArrayList<>();
//        }
        return accountDao.findAll();
    }

    public Optional<Account> findById(Long id) {

       // return accountDao.findById(id).map(AccountMapper::toDto);
        return accountDao.findById(id);
    }

    public Optional<Account> findByValue(String value) {
        return accountDao.findByValue(value);
    }

    public Optional<Account> update(Long id, AccountDto accountDto) {
        if (id == null) {
            log.info("Comparable Account must have an ID");
            return Optional.empty();
        }
        Optional<Account> existingAccount = accountDao.findById(id);
        if (existingAccount.isEmpty()) {
           log.info("Account with ID {} does not exist", id);
           return Optional.empty();
        }

        Account accountEntity = existingAccount.get();
        Currency currency = currencyDao.findById(accountDto.getCurrencyId()).orElse(null);
        IssuingBank issuingBank = issuingBankDao.findById(accountDto.getIssuingBankId()).orElse(null);
        accountEntity.setAccountNumber(accountDto.getAccountNumber());
        accountEntity.setBalance(accountDto.getBalance());
        accountEntity.setCurrencyId(currency);
        accountEntity.setIssuingBankId(issuingBank);
        accountDao.update(accountEntity);
        log.info("Account with ID {} updated", accountEntity.getId());
        return Optional.of(accountEntity);
    }

    @Transactional
    public boolean delete(Long id) {
        return accountDao.delete(id);
    }

    @Transactional
    public boolean deleteAll() {
        try {
            return accountDao.deleteAll();
        } catch (Exception e) {
            log.error("Ошибка при удалении всех записей из accounts", e);
            throw e; // важно пробросить, чтобы Spring знал о причине rollback
        }
    }

    @Transactional
    public boolean dropTable() {
        return accountDao.dropTable();
    }

    @Transactional
    public boolean initializeTable() {
        boolean tableCreated = accountDao.createTable();
        if (!tableCreated) {
            log.warn("Не удалось создать таблицу accounts");
            return false;
        }

        boolean valuesInserted = accountDao.insertDefaultValues();
        if (!valuesInserted) {
            log.warn("Таблица создана, но значения не вставлены");
            return false;
        }

        log.info("Таблица создана и значения успешно добавлены");
        return true;
    }

    @Transactional
    public boolean createTable() {
        return accountDao.createTable();
    }
}
