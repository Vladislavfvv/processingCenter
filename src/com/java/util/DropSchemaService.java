package com.java.util;

import com.java.service.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DropSchemaService {
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



    public DropSchemaService(Connection connection) {
        this.connection = connection;
        transactionService = new TransactionService(connection);
        cardService = new CardService(connection);
        accountService = new AccountService(connection);
        terminalService = new TerminalService(connection);
        cardStatusService = new CardStatusService(connection);
        paymentSystemService = new PaymentSystemService(connection);
        currencyService = new CurrencyService(connection);
        issuingBankService = new IssuingBankService(connection);
        salesPointService = new SalesPointService(connection);
        acquiringBankService = new AcquiringBankService(connection);
        responseCodeService = new ResponseCodeService(connection);
        transactionTypeService = new TransactionTypeService(connection);
        merchantCategoryCodeService = new MerchantCategoryCodeService(connection);
    }

    public void dropJDBCTables() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            connection.setAutoCommit(false);
            transactionService.dropTable("processingcenterschema.transaction");
            cardService.dropTable("processingcenterschema.card");
            accountService.dropTable("processingcenterschema.account");
            terminalService.dropTable("processingcenterschema.terminal");
            cardStatusService.dropTable("processingcenterschema.card_status");
            paymentSystemService.dropTable("processingcenterschema.payment_system");
            currencyService.dropTable("processingcenterschema.currency");
            issuingBankService.dropTable("processingcenterschema.issuing_bank");
            salesPointService.dropTable("processingcenterschema.sales_point");
            acquiringBankService.dropTable("processingcenterschema.acquiring_bank");
            responseCodeService.dropTable("processingcenterschema.response_code");
            transactionTypeService.dropTable("processingcenterschema.transaction_type");
            merchantCategoryCodeService.dropTable("processingcenterschema.merchant_category_code");

            connection.commit();
            System.out.println("Все таблицы успешно удалены и транзакция зафиксирована.");
        } catch (SQLException e) {
            connection.rollback();
            System.err.println("Ошибка при удалении таблиц: " + e.getMessage());
           // throw e;
        }
    }
}

