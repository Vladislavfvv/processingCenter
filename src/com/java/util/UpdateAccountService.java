package com.java.util;

import com.java.model.Account;
import com.java.service.AccountService;
import com.java.service.CurrencyService;
import com.java.service.IssuingBankService;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UpdateAccountService {

    private static final Logger logger = Logger.getLogger(UpdateAccountService.class.getName());

    private final AccountService accountService;
    private final CurrencyService currencyService;
    private final IssuingBankService issuingBankService;

    public UpdateAccountService(AccountService accountService, CurrencyService currencyService, IssuingBankService issuingBankService) {
        this.accountService = accountService;
        this.currencyService = currencyService;
        this.issuingBankService = issuingBankService;
    }

    public void updateAccount() {
        try (Connection connection = ConnectionManager.open()) {


         accountService.update(
                    new Account(null, "40817810800000000001", new BigDecimal("649.7"),
                            currencyService.findById(1L).orElseThrow(),
                            issuingBankService.findById(1L).orElseThrow()));


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

