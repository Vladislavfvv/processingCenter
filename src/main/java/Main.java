
import dao.hibernate.CardHibernateDaoImpl;
import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import service.*;
import util.*;
import util.jdbc.*;

import java.math.BigDecimal;
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
//        CreateSchemaService createSchemaService = new CreateSchemaService(connection);
//        createSchemaService.createJDBCTables();
//        InsertCardStatusService insertCardStatusService = new InsertCardStatusService(cardStatusService);
//        InsertPaymentSystemService insertPaymentSystemService = new InsertPaymentSystemService(paymentSystemService);
//        InsertIssuingBankService insertIssuingBankService = new InsertIssuingBankService(new IssuingBankService(connection));
//        InsertCurrencyService insertCurrencyService = new InsertCurrencyService(new CurrencyService(connection));
//        InsertAccountService insertAccountService = new InsertAccountService(accountService, currencyService, issuingBankService);
//         InsertCardService insertCardService = new InsertCardService(cardService, cardStatusService, paymentSystemService, accountService);


//
//        // Вставка таблиц
        //  try {
//        insertCardStatusService.insertCardStatuses();
//        insertPaymentSystemService.insertPaymentSystems();
//        insertIssuingBankService.insertIssuingBank();
//        insertCurrencyService.insertCurrencyServices();
//        insertAccountService.insertAccounts();
//        insertCardService.insertMultipleCards();
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
//        ClearJDBCTables clearJDBCTables = new ClearJDBCTables(connection, transactionService, cardService, accountService, terminalService, cardStatusService, paymentSystemService,
//        currencyService, issuingBankService, salesPointService, acquiringBankService, responseCodeService, transactionTypeService, merchantCategoryCodeService);
//        clearJDBCTables.dropJDBCTables();
//
//
//        //Удаление таблиц
//        DropSchemaService dropSchemaService = new DropSchemaService(connection);
//        dropSchemaService.dropJDBCTables();


//        CardHibernateDaoImpl cardDAO = new CardHibernateDaoImpl();
//
//        // Создаем карточку (подгружаем нужные связанные объекты через сессию)
//        Session session = HibernateConfig.getSessionFactory().openSession();
//        Card card = Card.builder()
//                .cardNumber("9999-1234-5678-0000")
//                .expirationDate(Date.valueOf("2028-06-30"))
//                .holderName("Петр Петров")
//                .cardStatusId(session.get(model.CardStatus.class, 1L))
//                .paymentSystemId(session.get(model.PaymentSystem.class, 1L))
//                .accountId(session.get(model.Account.class, 1L))
//                .receivedFromIssuingBank(new Timestamp(System.currentTimeMillis()))
//                .build();
//        session.close();
//
//        // Сохраняем
//        cardDAO.save(card);
//
//        // Находим по ID
//        Card found = cardDAO.findById(card.getId());
//        System.out.println("Найдена карта: " + found.getCardNumber());
//
//        // Обновляем
//        found.setHolderName("Обновленное Имя");
//        cardDAO.update(found);


//
//        Session session = HibernateConfig.getSessionFactory().openSession();
//        Transaction tx = session.beginTransaction();
//
//        Card card = new Card();
//        card.setCardNumber("1234-5678-9012-3456");
//        card.setExpirationDate(Date.valueOf("2027-12-31"));
//        card.setHolderName("Ivan Ivanov");
//
//        CardStatus status = session.get(CardStatus.class, 1L);
//        PaymentSystem ps = session.get(PaymentSystem.class, 1L);
//        Account acc = session.get(Account.class, 1L);
//
//        card.setCardStatusId(status);
//        card.setPaymentSystemId(ps);
//        card.setAccountId(acc);
//        card.setReceivedFromIssuingBank(new Timestamp(System.currentTimeMillis()));
//        card.setSentToIssuingBank(null);
//
//        session.save(card);
//        tx.commit();
//        session.close();


        SessionFactory sessionFactory = HibernateConfig.getSessionFactory();

        // Открываем сессию
        try (Session session = sessionFactory.openSession()) {
            // Начинаем транзакцию
            Transaction transaction = session.beginTransaction();

            // Создаем новый объект Account
//            Account account = Account.builder()
//                    .accountNumber("123456789")
//                    .balance(new BigDecimal("1000.00"))
//                    .build();

            // Сохраняем объект в базе данных
            //  session.save(account);

            User user = User.builder().firstName("John").lastName("Doe").build();
//
session.save(user);

//            Currency currency = currencyService.create(Currency.builder()
//                    .currencyDigitalCode("BLR")
//                    .currencyLetterCode("USD")
//                    .currencyName("Belorussian rubble").build());
//session.save(currency);

            // Фиксируем транзакцию
//            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Закрываем SessionFactory
            sessionFactory.close();

        }


        // Удаляем
        // cardDAO.delete(found);


    }


}
