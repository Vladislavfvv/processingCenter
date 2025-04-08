import com.java.model.Card;
import com.java.service.*;
import com.java.util.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Logger;


public class Main {
    public static void main(String[] args) throws SQLException {
        Logger logger = Logger.getLogger(Main.class.getClassLoader().getClass().getName());
        Connection connection = ConnectionManager.open(); // Получаем соединение из пула
        CardStatusService cardStatusService = new CardStatusService(connection);
        AcquiringBankService acquiringBankService = new AcquiringBankService(connection);
        MerchantCategoryCodeService merchantCategoryCodeService = new MerchantCategoryCodeService(connection);
        ResponseCodeService responseCodeService = new ResponseCodeService(connection);
        SalesPointService salesPointService = new SalesPointService(connection);
        TransactionTypeService transactionTypeService = new TransactionTypeService(connection);
        PaymentSystemService paymentSystemService = new PaymentSystemService(connection);
        AccountService accountService = new AccountService(connection);
        CurrencyService currencyService = new CurrencyService(connection);  // Создаем CurrencyService
        IssuingBankService issuingBankService = new IssuingBankService(connection);
        TerminalService terminalService = new TerminalService(connection);
        CardService cardService = new CardService(connection);
        TransactionService transactionService = new TransactionService(connection);



//        // Инициализация пула соединений
//        // Пул соединений уже инициализируется в ConnectionManager2 статически
//
//        // Получаем DAO через фабрику
//        DAOInterface<AcquiringBank> acquiringBankDAO = DAOFactory.getAcquiringBankDAO();
//
//        // Работа с DAO
//
//        // Закрытие соединений, если нужно
//        DAOFactory.closeConnection();


        System.out.println("--------------------------JDBC-------------------------------------------");


        // Создание таблиц
        CreateSchemaService createSchemaService = new CreateSchemaService(connection);
        createSchemaService.createJDBCTables();
        InsertCardStatusService insertCardStatusService = new InsertCardStatusService(cardStatusService);
        InsertPaymentSystemService insertPaymentSystemService = new InsertPaymentSystemService(paymentSystemService);
        InsertIssuingBankService insertIssuingBankService = new InsertIssuingBankService(new IssuingBankService(connection));
        InsertCurrencyService insertCurrencyService = new InsertCurrencyService(new CurrencyService(connection));
        InsertAccountService insertAccountService = new InsertAccountService(accountService, currencyService, issuingBankService);
         InsertCardService insertCardService = new InsertCardService(cardService, cardStatusService, paymentSystemService, accountService);


//
//        // Вставка таблиц
      //  try {
        insertCardStatusService.insertCardStatuses();
        insertPaymentSystemService.insertPaymentSystems();
        insertIssuingBankService.insertIssuingBank();
        insertCurrencyService.insertCurrencyServices();
        insertAccountService.insertAccounts();
        insertCardService.insertMultipleCards();
        //   connection.commit();
//        } catch (SQLException e) {
//            connection.rollback(); // отменить всё, если ошибка
//            throw e;
//        } finally {
//            connection.close();
//        }



        // Шаг 3: Создание и вставка карт
//        InsertCardService insertCardService = new InsertCardService(
//                cardService, cardStatusService, paymentSystemService, accountService
//        );
//        insertCardService.insertMultipleCards();

        //очистка таблиц
//
        ClearJDBCTables clearJDBCTables = new ClearJDBCTables(connection, transactionService, cardService, accountService, terminalService, cardStatusService, paymentSystemService,
        currencyService, issuingBankService, salesPointService, acquiringBankService, responseCodeService, transactionTypeService, merchantCategoryCodeService);
        clearJDBCTables.dropJDBCTables();


        //Удаление таблиц
        DropSchemaService dropSchemaService = new DropSchemaService(connection);
        dropSchemaService.dropJDBCTables();




    }


}
