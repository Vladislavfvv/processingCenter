
import dao.hibernate.*;
import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import service.*;
import service.hibernate.*;
import util.*;
import util.jdbc.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.logging.Logger;


public class Main {
    public static void main(String[] args) throws SQLException {


        //     myHibernateInitial();
        myJDBCInitial();

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


        //  SessionFactory sessionFactory = HibernateConfig.getSessionFactory();

        // Открываем сессию
        //   try (Session session = sessionFactory.openSession()) {
        // Начинаем транзакцию
        //         Transaction transaction = session.beginTransaction();

        // Создаем новый объект Account
//            Account account = Account.builder()
//                    .accountNumber("123456789")
//                    .balance(new BigDecimal("1000.00"))
//                    .build();

        // Сохраняем объект в базе данных
        //  session.save(account);


        //      User user = User.builder().firstName("John").lastName("Doe").build();
//
//session.save(user);

//            Currency currency = currencyService.create(Currency.builder()
//                    .currencyDigitalCode("BLR")
//                    .currencyLetterCode("USD")
//                    .currencyName("Belorussian rubble").build());
//session.save(currency);

        // Фиксируем транзакцию
        //            transaction.commit();
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        } finally {
        //            // Закрываем SessionFactory
        //            sessionFactory.close();
        //
        //        }


        // Удаляем
        // cardDAO.delete(found);


        // Получаем DAO
//        PaymentSystemHibernateDaoImpl dao = PaymentSystemHibernateDaoImpl.getInstance();
//
//        // Пример вставки новой платёжной системы
//        PaymentSystem newSystem = new PaymentSystem();
//        newSystem.setPaymentSystemName("Test System");
//        dao.insert(newSystem);
//
//        // Пример поиска по ID
//        Optional<PaymentSystem> found = dao.findById(newSystem.getId());
//        found.ifPresentOrElse(
//                ps -> System.out.println("Найдена система: " + ps.getPaymentSystemName()),
//                () -> System.out.println("Система не найдена")
//        );
//
//        // Пример получения всех систем
//        System.out.println("Список всех платёжных систем:");
//        dao.findAll().forEach(ps -> System.out.println(ps.getPaymentSystemName()));


//        //-----------------------------------HIBERNATE________________________________________
//        CurrencyHibernateDaoImpl currencyHibernateDao = new CurrencyHibernateDaoImpl();
//        CurrencyHibernateService currencyHibernateService = new CurrencyHibernateService(currencyHibernateDao);
//
//        IssuingBankHibernateDaoImpl issuingHibernateDaoBankDao = new IssuingBankHibernateDaoImpl();
//        IssuingBankHibernateService issuingBankHibernateService = new IssuingBankHibernateService(issuingHibernateDaoBankDao);
//
//        AccountHibernateDaoImpl accountHibernateDao = new AccountHibernateDaoImpl();
//        AccountHibernateService accountHibernateService = new AccountHibernateService(accountHibernateDao);
//
//        CardStatusHibernateDaoImpl cardStatusHibernateDao = new CardStatusHibernateDaoImpl();
//        CardStatusHibernateService cardStatusHibernateService = new CardStatusHibernateService(cardStatusHibernateDao);
//
//        PaymentSystemHibernateDaoImpl paymentSystemHibernateDao = new PaymentSystemHibernateDaoImpl();
//        PaymentSystemHibernateService paymentSystemHibernateService = new PaymentSystemHibernateService(paymentSystemHibernateDao);
//
//        CardHibernateDaoImpl cardHibernateDao = new CardHibernateDaoImpl();
//        CardHibernateService cardHibernateService = new CardHibernateService(cardHibernateDao);
//
//        // Создать и сохранить Currency
//        Currency currency = Currency.builder()
//                .currencyName("USD")
//                .currencyDigitalCode("555")
//                .currencyLetterCode("NEW")
//                .build();
//        currency = currencyHibernateService.create(currency);
//
//        // Создать и сохранить IssuingBank
//        IssuingBank bank = IssuingBank.builder()
//                .bic("12345678)")
//                .bin("BINAR")
//                .abbreviatedName("ABBREVIATEDBANK")
//                .build();
//        bank = issuingBankHibernateService.create(bank);
//
//        // Создать и сохранить Account
//        Account account = Account.builder()
//                .accountNumber("9876543210")
//                .balance(new BigDecimal("5000.00"))
//                .currencyId(currency)
//                .issuingBankId(bank)
//                .build();
//        account = accountHibernateService.create(account);
//
//        // Создать и сохранить CardStatus
//        CardStatus status = CardStatus.builder()
//                .cardStatusName("I dont know")
//                .build();
//        status = cardStatusHibernateService.create(status);
//
//        // Создать и сохранить PaymentSystem
//        PaymentSystem paymentSystem = PaymentSystem.builder()
//                .paymentSystemName("VISAAAAAAA")
//                .build();
//        paymentSystem = paymentSystemHibernateService.create(paymentSystem);
//
//        // Создать и сохранить карту с вложенными объектами
//        Card card = Card.builder()
//                .cardNumber("1234-5678-9101-1121")
//                .expirationDate(Date.valueOf("2025-12-31"))
//                .holderName("Artur Conan Doel")
//                .cardStatusId(status)
//                .paymentSystemId(paymentSystem)
//                .accountId(account)
//                .receivedFromIssuingBank(new Timestamp(System.currentTimeMillis()))
//                .sentToIssuingBank(new Timestamp(System.currentTimeMillis()))
//                .build();
//        card = cardHibernateService.create(card);
//
//        System.out.println("Добавлена карта с id: " + card.getId());
    }


    public static void myHibernateInitial() {
        System.out.println("--------------------------Hibernate-------------------------------------------");
        CurrencyHibernateDaoImpl currencyHibernateDao = new CurrencyHibernateDaoImpl();
        CurrencyHibernateService currencyHibernateService = new CurrencyHibernateService(currencyHibernateDao);

        IssuingBankHibernateDaoImpl issuingHibernateDaoBankDao = new IssuingBankHibernateDaoImpl();
        IssuingBankHibernateService issuingBankHibernateService = new IssuingBankHibernateService(issuingHibernateDaoBankDao);

        AccountHibernateDaoImpl accountHibernateDao = new AccountHibernateDaoImpl();
        AccountHibernateService accountHibernateService = new AccountHibernateService(accountHibernateDao);

        CardStatusHibernateDaoImpl cardStatusHibernateDao = new CardStatusHibernateDaoImpl();
        CardStatusHibernateService cardStatusHibernateService = new CardStatusHibernateService(cardStatusHibernateDao);

        PaymentSystemHibernateDaoImpl paymentSystemHibernateDao = new PaymentSystemHibernateDaoImpl();
        PaymentSystemHibernateService paymentSystemHibernateService = new PaymentSystemHibernateService(paymentSystemHibernateDao);

        CardHibernateDaoImpl cardHibernateDao = new CardHibernateDaoImpl();
        CardHibernateService cardHibernateService = new CardHibernateService(cardHibernateDao);


        //loadData
        TestDataHibernateLoader.loadData(cardStatusHibernateService, paymentSystemHibernateService, currencyHibernateService,
                issuingBankHibernateService, accountHibernateService, cardHibernateService);


        //Insert card
        insertHibernateCard(currencyHibernateService, issuingBankHibernateService, accountHibernateService,
                cardStatusHibernateService, paymentSystemHibernateService, cardHibernateService);


        //delete by id
        cardHibernateService.delete(1L);

//clear tables
        cardHibernate.deleteAll("card");
        cardStatusHibernateService.delete();





    }

    public static void insertHibernateCard(CurrencyHibernateService currencyHibernateService, IssuingBankHibernateService issuingBankHibernateService,
                                           AccountHibernateService accountHibernateService, CardStatusHibernateService cardStatusHibernateService,
                                           PaymentSystemHibernateService paymentSystemHibernateService, CardHibernateService cardHibernateService) {
        // Создать и сохранить Currency
        Currency currency = Currency.builder()
                .currencyName("USD")
                .currencyDigitalCode("555")
                .currencyLetterCode("NEW")
                .build();
        currency = currencyHibernateService.create(currency);

        // Создать и сохранить IssuingBank
        IssuingBank bank = IssuingBank.builder()
                .bic("12345678)")
                .bin("BINAR")
                .abbreviatedName("ABBREVIATEDBANK")
                .build();
        bank = issuingBankHibernateService.create(bank);

        // Создать и сохранить Account
        Account account = Account.builder()
                .accountNumber("9876543210")
                .balance(new BigDecimal("5000.00"))
                .currencyId(currency)
                .issuingBankId(bank)
                .build();
        account = accountHibernateService.create(account);

        // Создать и сохранить CardStatus
        CardStatus status = CardStatus.builder()
                .cardStatusName("I dont know")
                .build();
        status = cardStatusHibernateService.create(status);

        // Создать и сохранить PaymentSystem
        PaymentSystem paymentSystem = PaymentSystem.builder()
                .paymentSystemName("VISAAAAAAA")
                .build();
        paymentSystem = paymentSystemHibernateService.create(paymentSystem);

        // Создать и сохранить карту с вложенными объектами
        Card card = Card.builder()
                .cardNumber("1234-5678-9101-1121")
                .expirationDate(Date.valueOf("2025-12-31"))
                .holderName("Artur Conan Doel")
                .cardStatusId(status)
                .paymentSystemId(paymentSystem)
                .accountId(account)
                .receivedFromIssuingBank(new Timestamp(System.currentTimeMillis()))
                .sentToIssuingBank(new Timestamp(System.currentTimeMillis()))
                .build();
        card = cardHibernateService.create(card);

        System.out.println("Добавлена карта с id: " + card.getId());
    }





    public static void myJDBCInitial() throws SQLException {
        System.out.println("--------------------------JDBC-------------------------------------------");
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
        // Создание таблиц
        CreateSchemaService createSchemaService = new CreateSchemaService(connection);
        try {
            createSchemaService.createJDBCTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        InsertCardStatusService insertCardStatusService = new InsertCardStatusService(cardStatusService);
        InsertPaymentSystemService insertPaymentSystemService = new InsertPaymentSystemService(paymentSystemService);
        InsertIssuingBankService insertIssuingBankService = new InsertIssuingBankService(new IssuingBankService(connection));
        InsertCurrencyService insertCurrencyService = new InsertCurrencyService(new CurrencyService(connection));
        InsertAccountService insertAccountService = new InsertAccountService(accountService, currencyService, issuingBankService);
        InsertCardService insertCardService = new InsertCardService(cardService, cardStatusService, paymentSystemService, accountService);


//        // Вставка таблиц

        insertCardStatusService.insertCardStatuses();
        insertPaymentSystemService.insertPaymentSystems();
        insertIssuingBankService.insertIssuingBank();
        insertCurrencyService.insertCurrencyServices();
        insertAccountService.insertAccounts();
        insertCardService.insertMultipleCards();
        //connection.commit();


        //Создание и вставка карт
        // InsertCardService insertCardService = new InsertCardService(cardService, cardStatusService, paymentSystemService, accountService);
        insertCardService.insertMultipleCards();

//        очистка таблиц
////
        ClearJDBCTables clearJDBCTables = new ClearJDBCTables(connection, transactionService, cardService, accountService, terminalService, cardStatusService, paymentSystemService,
                currencyService, issuingBankService, salesPointService, acquiringBankService, responseCodeService, transactionTypeService, merchantCategoryCodeService);
        clearJDBCTables.dropJDBCTables();
//
//
//        //Удаление таблиц
        DropSchemaService dropSchemaService = new DropSchemaService(connection);
        dropSchemaService.dropJDBCTables();


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

        //        // Закрытие соединений, если нужно
//        DAOFactory.closeConnection();
    }
}









