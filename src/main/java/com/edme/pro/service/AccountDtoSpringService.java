package com.edme.pro.service;


import com.edme.pro.dao.DaoInterfaceSpring;
import com.edme.pro.dto.AccountDto;
import com.edme.pro.repository.AccountRepository;
import com.edme.pro.repository.CurrencyRepository;
import com.edme.pro.repository.IssuingBankRepository;
import lombok.extern.slf4j.Slf4j;
import com.edme.pro.mapper.AccountMapper;
import com.edme.pro.model.Account;
import com.edme.pro.model.Currency;
import com.edme.pro.model.IssuingBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountDtoSpringService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private IssuingBankRepository issuingBankRepository;

    @Transactional
    public Optional<Account> save(AccountDto accountDto) {
        Optional<Account> existing = accountRepository.findByAccountNumber(accountDto.getAccountNumber());
        if (existing.isPresent()) {
            log.info("Account '{}' already exists", accountDto.getAccountNumber());
            return Optional.empty();
        }

        accountDto.setId(null); // Обнуляем ID
        Currency currency = currencyRepository.findById(accountDto.getCurrencyId())
                .orElse(Currency.builder().build());
        IssuingBank issuingBank = issuingBankRepository.findById(accountDto.getIssuingBankId())
                .orElse(IssuingBank.builder().build());

        Account account = AccountMapper.toEntity(accountDto, currency, issuingBank);
        Account saved = accountRepository.saveAndFlush(account);

        log.info("Account with ID {} saved", saved.getId());
        return Optional.of(saved);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> findByValue(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Transactional
    public Optional<Account> update(Long id, AccountDto accountDto) {
        Optional<Account> existing = accountRepository.findById(id);
        if (existing.isEmpty()) {
            log.info("Account with ID {} not found", id);
            return Optional.empty();
        }

        Account account = existing.get();
        Currency currency = currencyRepository.findById(accountDto.getCurrencyId()).orElse(null);
        IssuingBank issuingBank = issuingBankRepository.findById(accountDto.getIssuingBankId()).orElse(null);

        account.setAccountNumber(accountDto.getAccountNumber());
        account.setBalance(accountDto.getBalance());
        account.setCurrencyId(currency);
        account.setIssuingBankId(issuingBank);

        accountRepository.save(account);
        log.info("Account with ID {} updated", account.getId());
        return Optional.of(account);
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            accountRepository.delete(account.get());
            log.info("Account with ID {} deleted", id);
            return true;
        } else {
            log.info("Account with ID {} not found", id);
            return false;
        }
    }

    @Transactional
    public boolean deleteAll() {
        try {
            accountRepository.deleteAll();
            log.info("All accounts deleted");
            return true;
        } catch (Exception e) {
            log.error("Error deleting all accounts: {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean dropTable() {
        try {
            accountRepository.dropTable();
            log.info("Account table dropped");
            return true;
        } catch (Exception e) {
            log.error("Error dropping account table: {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean createTable() {
        try {
            accountRepository.createTable();
            log.info("Account table created");
            return true;
        } catch (Exception e) {
            log.error("Error creating account table: {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean initializeTable() {
        if (!createTable()) {
            return false;
        }

        try {
            accountRepository.insertDefaultValues();
            log.info("Default values inserted into account table");
            return true;
        } catch (Exception e) {
            log.error("Error inserting default values into account table: {}", e.getMessage());
            return false;
        }
    }
}
