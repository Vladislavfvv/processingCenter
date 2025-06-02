package com.edme.pro.controller;

import com.edme.pro.dao.CurrencySpringDaoImpl;
import com.edme.pro.dto.AccountDto;
import com.edme.pro.mapper.AccountMapper;
import com.edme.pro.model.Account;
import com.edme.pro.service.AccountDtoSpringService;
import com.edme.pro.service.IssuingBankDtoSpringService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountDtoSpringService accountService;
    private final CurrencySpringDaoImpl currencySpringDao;
    private final IssuingBankDtoSpringService issuingBankService;


    @Autowired
    public AccountController(AccountDtoSpringService accountService, CurrencySpringDaoImpl currencySpringDao, IssuingBankDtoSpringService issuingBankService) {
        this.accountService = accountService;
        this.currencySpringDao = currencySpringDao;
        this.issuingBankService = issuingBankService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAll() {
        return ResponseEntity.ok(accountService.findAll());
    }



    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<Account> accounts = accountService.findAll();
        List<AccountDto> dtos = accounts.stream()
                .map(AccountMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return accountService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PostMapping
//    public ResponseEntity<Account> save(@RequestBody @Valid AccountDto accountDto) {
//        return accountService.save(accountDto)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.badRequest().build());
//    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid AccountDto accountDto) {
        Optional<Account> account = accountService.save(accountDto);
        if (account.isPresent()) {
            return ResponseEntity.ok(account.get());
        } else {
            return ResponseEntity.badRequest().body("Account с таким названием уже существует");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> update(@PathVariable("id") Long id, @RequestBody @Valid AccountDto accountDto) {
        return accountService.update(id, accountDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Account> delete(@PathVariable Long id) {
        return accountService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }


    @PostMapping("/createTable")
    public ResponseEntity<String> createAccountsTable() {
        log.info("Creating Accounts table");
        boolean result = accountService.createTable();
        if (result) {
            return ResponseEntity.ok("Таблица Accounts успешно создана");
        } else {
            return ResponseEntity.status(500).body("Ошибка при создании таблицы");
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllAccounts() {
        log.info("Deleting all Accounts");
        boolean result = accountService.deleteAll();
        if (result) {
            return ResponseEntity.ok("Все записи удалены");
        } else {
            return ResponseEntity.status(500).body("Ошибка при удалении всех записей");
        }
    }

    @DeleteMapping("/drop")
    public ResponseEntity<String> dropAccountsTable() {
        log.info("Dropping Accounts table");
        boolean result = accountService.dropTable();
        if (result) {
            return ResponseEntity.ok("Таблица Accounts удалена");
        } else {
            return ResponseEntity.status(500).body("Ошибка при очистке таблицы");
        }
    }

    @PostMapping("/fillTable")
    public ResponseEntity<String> fillDefaultAccounts() {
        log.info("Inserting default Accounts...");
        boolean success = accountService.initializeTable();
        if (success) {
            return ResponseEntity.ok("Значения по умолчанию успешно добавлены.");
        } else {
            return ResponseEntity.status(500).body("Не удалось добавить значения по умолчанию.");
        }
    }

}
