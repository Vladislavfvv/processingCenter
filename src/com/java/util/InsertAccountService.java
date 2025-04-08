package com.java.util;

import com.java.model.Account;
import com.java.model.Currency;
import com.java.model.IssuingBank;
import com.java.service.AccountService;
import com.java.service.CurrencyService;
import com.java.service.IssuingBankService;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class InsertAccountService {
    private static final Logger logger = Logger.getLogger(InsertAccountService.class.getName());

    private final AccountService accountService;
    private final CurrencyService currencyService;
    private final IssuingBankService issuingBankService;

    public InsertAccountService(AccountService accountService, CurrencyService currencyService, IssuingBankService issuingBankService) {
        this.accountService = accountService;
        this.currencyService = currencyService;
        this.issuingBankService = issuingBankService;
    }

    public void insertAccounts() {
        try (Connection connection = ConnectionManager.open()) {
            List<Account> accountLists = new ArrayList<>();

            accountLists.add(accountService.create(
                    new Account(null, "40817810800000000001", new BigDecimal("649.7"),
                            currencyService.findById(1L).orElseThrow(),
                            issuingBankService.findById(1L).orElseThrow())));
            accountLists.add(accountService.create(
                    new Account(null, "40817810100000000002", new BigDecimal("48702.07"),
                            currencyService.findById(1L).orElseThrow(), issuingBankService.findById(1L).orElseThrow())));
            accountLists.add(accountService.create(
                    new Account(null, "40817810400000000003", new BigDecimal("715000.01"),
                            currencyService.findById(1L).orElseThrow(), issuingBankService.findById(1L).orElseThrow())));
            accountLists.add(accountService.create(
                    new Account(null, "40817810400000000003", new BigDecimal("10000.0"),
                            currencyService.findById(3L).orElseThrow(), issuingBankService.findById(1L).orElseThrow())));


//            for (Account account : accountLists) {
//                //Account created = accountService.create(account);
//             //   if (created != null) {
//                    logger.info("Аккаунт " + created.getAccountNumber() + " успешно добавлен.");
//                } else {
//                    logger.warning("Не удалось вставить аккаунт " + account.getAccountNumber());
//                }
//            }
        } catch (SQLException e) {
            logger.severe("Ошибка при работе с соединением: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            logger.severe("Ошибка при вставке аккаунтов: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
