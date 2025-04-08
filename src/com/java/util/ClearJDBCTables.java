package com.java.util;

import com.java.service.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ClearJDBCTables {
    private final Connection connection;
    TransactionService transactionService;
    CardService cardService;
    AccountService accountService;
    TerminalService terminalService;
    CardStatusService cardStatusService;
    PaymentSystemService paymentSystemService;
    CurrencyService currencyService;
    IssuingBankService issuingBankService;
    SalesPointService salesPointService;
    AcquiringBankService acquiringBankService;
    ResponseCodeService responseCodeService;
    TransactionTypeService transactionTypeService;
    MerchantCategoryCodeService merchantCategoryCodeService;

    public ClearJDBCTables(Connection connection,
                           TransactionService transactionService,
                           CardService cardService,
                           AccountService accountService,
                           TerminalService terminalService,
                           CardStatusService cardStatusService,
                           PaymentSystemService paymentSystemService,
                           CurrencyService currencyService,
                           IssuingBankService issuingBankService,
                           SalesPointService salesPointService,
                           AcquiringBankService acquiringBankService,
                           ResponseCodeService responseCodeService,
                           TransactionTypeService transactionTypeService,
                           MerchantCategoryCodeService merchantCategoryCodeService) {
        this.connection = connection;
        this.transactionService = transactionService;
        this.cardService = cardService;
        this.accountService = accountService;
        this.terminalService = terminalService;
        this.cardStatusService = cardStatusService;
        this.paymentSystemService = paymentSystemService;
        this.currencyService = currencyService;
        this.issuingBankService = issuingBankService;
        this.salesPointService = salesPointService;
        this.acquiringBankService = acquiringBankService;
        this.responseCodeService = responseCodeService;
        this.transactionTypeService = transactionTypeService;
        this.merchantCategoryCodeService = merchantCategoryCodeService;
    }

    public void dropJDBCTables() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            connection.setAutoCommit(false);
            transactionService.deleteAll("processingcenterschema.transaction");
            cardService.deleteAll("processingcenterschema.card");
            accountService.deleteAll("processingcenterschema.account");
            terminalService.deleteAll("processingcenterschema.terminal");
            cardStatusService.deleteAll("processingcenterschema.card_status");
            paymentSystemService.deleteAll("processingcenterschema.payment_system");
            currencyService.deleteAll("processingcenterschema.currency");
            issuingBankService.deleteAll("processingcenterschema.issuing_bank");
            salesPointService.deleteAll("processingcenterschema.sales_point");
            acquiringBankService.deleteAll("processingcenterschema.acquiring_bank");
            responseCodeService.deleteAll("processingcenterschema.response_code");
            transactionTypeService.deleteAll("processingcenterschema.transaction_type");
            merchantCategoryCodeService.deleteAll("processingcenterschema.merchant_category_code");

            connection.commit();
            System.out.println("Все таблицы успешно очищены и транзакция зафиксирована.");
        } catch (SQLException e) {
            connection.rollback();
            System.err.println("Ошибка при очистке таблиц: " + e.getMessage());
           // throw e;
        }
    }
}
