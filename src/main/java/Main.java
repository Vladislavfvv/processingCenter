
import dao.hibernate.*;
import dto.*;
import lombok.extern.slf4j.Slf4j;
import model.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.*;
import service.hibernate.*;
import service.spring.*;
import util.*;
import util.jdbc.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Slf4j
public class Main {
    public static void main(String[] args) throws SQLException {


        // myHibernateInitial();
        // myJDBCInitial();

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

        // Создаем Spring-контекст на основе Java-конфигурации
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Получаем CurrencySpringService из контекста
        CurrencySpringService cs = context.getBean(CurrencySpringService.class);
        PaymentSystemDtoSpringService ps = context.getBean(PaymentSystemDtoSpringService.class);
        CardStatusDtoSpringService css = context.getBean(CardStatusDtoSpringService.class);
        IssuingBankDtoSpringService ib = context.getBean(IssuingBankDtoSpringService.class);
        AccountDtoSpringService as = context.getBean(AccountDtoSpringService.class);
        CardDtoSpringService card = context.getBean(CardDtoSpringService.class);

        //insertData(context, cs, ps, css, ib, as, card);


        //clearTablesSpring(context, cs, ps, css, ib, as, card);
        printDataSpring(context, cs, ps, css, ib, as, card);
       // dropTablesSpring(context, cs, ps, css, ib, as, card);



        context.close();
    }

    private static void clearTablesSpring(AnnotationConfigApplicationContext context, CurrencySpringService cs, PaymentSystemDtoSpringService ps, CardStatusDtoSpringService css, IssuingBankDtoSpringService ib, AccountDtoSpringService as, CardDtoSpringService card) {
        card.deleteAll();
        as.deleteAll();
        cs.deleteAll();
        ps.deleteAll();
        css.deleteAll();
        ib.deleteAll();
    }

    private static void dropTablesSpring(AnnotationConfigApplicationContext context, CurrencySpringService cs, PaymentSystemDtoSpringService ps, CardStatusDtoSpringService css, IssuingBankDtoSpringService ib, AccountDtoSpringService as, CardDtoSpringService card) {
        card.dropTable();
        as.dropTable();
        cs.dropTable();
        ps.dropTable();
        css.dropTable();
        ib.dropTable();
    }


    public static void insertData(AnnotationConfigApplicationContext context, CurrencySpringService cs, PaymentSystemDtoSpringService ps,
                                  CardStatusDtoSpringService css, IssuingBankDtoSpringService ib, AccountDtoSpringService as, CardDtoSpringService card) {
        css.save(new CardStatusDto(null, "Card is not active"));
        css.save(new CardStatusDto(null, "Card is valid"));
        css.save(new CardStatusDto(null, "Card is temporarily blocked"));
        css.save(new CardStatusDto(null, "Card is lost"));
        css.save(new CardStatusDto(null, "Card is compromised"));

        ps.save(new PaymentSystemDto(null, "VISA International Service Association"));
        ps.save(new PaymentSystemDto(null, "Mastercard"));
        ps.save(new PaymentSystemDto(null, "JCB"));
        ps.save(new PaymentSystemDto(null, "American Express"));
        ps.save(new PaymentSystemDto(null, "Diners Club International"));
        ps.save(new PaymentSystemDto(null, "China UnionPay"));


        cs.save("643", "RUB", "Russian Dollar");
        cs.save("980", "UAN", "Hryvnia");
        cs.save("840", "USD", "US Dollar");
        cs.save("978", "EUR", "Euro");
        cs.save("392", "JPY", "Yen");
        cs.save("156", "CNY", "Yuan Renminbi");
        cs.save("826", "GBP", "Pound Sterling");

        ib.save(new IssuingBankDto(null, "041234569", "12345", "ОАО Приорбанк"));
        ib.save(new IssuingBankDto(null, "041234570", "45256", "ОАО Сбербанк"));
        ib.save(new IssuingBankDto(null, "041234571", "98725", "ЗАО МТБ Банк"));
        ib.save(new IssuingBankDto(null, "345746433", "54321", "ЗАО РРБ Банк"));


        as.save(new AccountDto(null, "40817810800000000001", new BigDecimal("649.70"), 6L, 1L));
        as.save(new AccountDto(null, "40817810100000000002", new BigDecimal("48702.07"), 5L, 2L));
        as.save(new AccountDto(null, "40817810400000000003", new BigDecimal("715000.01"), 1L, 3L));
        as.save(new AccountDto(null, "40817810400000000004", new BigDecimal("10000.0"), 3L, 4L));


        card.save(new CardDto(null, "4123450000000019", Date.valueOf("2025-12-10"), "IVAN I. IVANOV", 2L, 1L, 1L, Timestamp.valueOf("2022-10-21 15:26:06.175"), Timestamp.valueOf("2022-10-21 15:27:08.271")));
        card.save(new CardDto(null, "5123450000000024", Date.valueOf("2025-12-31"), "SEMION E. PETROV", 3L, 2L, 2L, Timestamp.valueOf("2022-04-05 10:23:05.372"), Timestamp.valueOf("2022-04-05 10:24:02.175")));
    }


    public static void printDataSpring(AnnotationConfigApplicationContext context, CurrencySpringService cs, PaymentSystemDtoSpringService ps,
                                       CardStatusDtoSpringService css, IssuingBankDtoSpringService ib, AccountDtoSpringService as, CardDtoSpringService card) {

        card.findAll().forEach(System.out::println);
        cs.findAll().forEach(System.out::println);
        ps.findAll().forEach(System.out::println);
        css.findAll().forEach(System.out::println);
        ib.findAll().forEach(System.out::println);
    }


    //---------------------------------------------------------------------------------
    public static void mySpringFirst() {
        // Создаем Spring-контекст на основе Java-конфигурации
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Получаем CurrencySpringService из контекста
        CurrencySpringService currencyService = context.getBean(CurrencySpringService.class);

        Optional<Currency> addedCurrency = currencyService.save("RUS", "555", "EUR");
        Optional<Currency> addedCurrency2 = currencyService.save("BYD", "777", "USD");
        if (addedCurrency.isPresent()) {
            System.out.println("Добавлена валюта: " + addedCurrency.get());
        } else {
            System.out.println("Валюта уже существует.");
        }

        if (addedCurrency2.isPresent()) {
            System.out.println("Добавлена валюта: " + addedCurrency2.get());
        } else {
            System.out.println("Валюта уже существует.");
        }
        System.out.println("List jf currencies");
        List<Currency> listCurrencies = currencyService.findAll();
        listCurrencies.forEach(System.out::println);

        context.close();
    }


    public static void myMethodAddCardStatus() {
        // Создаем Spring-контекст на основе Java-конфигурации
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Получаем CurrencySpringService из контекста
        CardStatusDtoSpringService cardStatusDtoSpringService = context.getBean(CardStatusDtoSpringService.class);


        // Пример использования
        Optional<CardStatusDto> addedCardStatus = cardStatusDtoSpringService.save(CardStatusDto.builder().cardStatusName("newCardStatus").build());
        Optional<CardStatusDto> addedCardStatus2 = cardStatusDtoSpringService.save(CardStatusDto.builder().cardStatusName("newCardStatus222").build());
        Optional<CardStatusDto> addedCardStatus3 = cardStatusDtoSpringService.save(CardStatusDto.builder().cardStatusName("newCardStatus333").build());
//        if (addedCardStatus.isPresent()) {
//            System.out.println("Добавлен статус:" + addedCardStatus.get());
//        }
//        else System.out.println("Не добавлен статус:" + addedCardStatus.get());
//
//        List<CardStatus> allStatuses = cardStatusDtoSpringService.findAll();
//        if (allStatuses.isEmpty()) {
//            System.out.println("Список статусов пуст.");
//        } else {
//            System.out.println("Список статусов#");
//            allStatuses.forEach(System.out::println);
//        }

//        cardStatusDtoSpringService.update(CardStatusDto.builder().id(2L).cardStatusName("updateCardStatus").build());
//
//        List<CardStatusDto> allStatuses2 = cardStatusDtoSpringService.getAll();
//        if (allStatuses2.isEmpty()) {
//            System.out.println("Список статусов пуст.");
//        } else {
//            System.out.println("Список статусов#");
//            allStatuses2.forEach(System.out::println);
//        }

        context.close();
    }


    public static void findCardStatusList() {
        // Создаем Spring-контекст на основе Java-конфигурации
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Получаем CurrencySpringService из контекста
        CardStatusDtoSpringService cardStatusDtoSpringService = context.getBean(CardStatusDtoSpringService.class);


        List<CardStatusDto> allStatuses = cardStatusDtoSpringService.findAll();
        if (allStatuses.isEmpty()) {
            System.out.println("Список статусов пуст.");
        } else {
            System.out.println("Список статусов#");
            allStatuses.forEach(System.out::println);
        }


        context.close();
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

//        createTables(currencyHibernateService, issuingBankHibernateService, accountHibernateService,
//                cardStatusHibernateService, paymentSystemHibernateService, cardHibernateService);


        //loadData
        TestDataHibernateLoader.loadData(cardStatusHibernateService, paymentSystemHibernateService, currencyHibernateService,
                issuingBankHibernateService, accountHibernateService, cardHibernateService);


        //Insert card
//        insertHibernateCard(currencyHibernateService, issuingBankHibernateService, accountHibernateService,
//                cardStatusHibernateService, paymentSystemHibernateService, cardHibernateService);


        //delete by id
//        cardHibernateService.delete(1L);
//
////clear tables
//        cardHibernate.deleteAll("card");
//        cardStatusHibernateService.delete();


//        clearTables(currencyHibernateService, issuingBankHibernateService, accountHibernateService,
//                cardStatusHibernateService, paymentSystemHibernateService, cardHibernateService);

//dropTables(currencyHibernateService, issuingBankHibernateService, accountHibernateService, cardStatusHibernateService,
//        paymentSystemHibernateService, cardHibernateService);

    }


    public static void createTables(CurrencyHibernateService currencyHibernateService, IssuingBankHibernateService issuingBankHibernateService,
                                    AccountHibernateService accountHibernateService, CardStatusHibernateService cardStatusHibernateService,
                                    PaymentSystemHibernateService paymentSystemHibernateService, CardHibernateService cardHibernateService) {

        currencyHibernateService.createTable("processingCenterSchema.currency");
        issuingBankHibernateService.createTable("processingCenterSchema.issuing_bank");
        accountHibernateService.createTable("processingCenterSchema.account");
        cardStatusHibernateService.createTable("processingCenterSchema.card_status");
        paymentSystemHibernateService.createTable("processingCenterSchema.payment_system");
        cardHibernateService.createTable("processingCenterSchema.card");
    }

    public static void clearTables(CurrencyHibernateService currencyHibernateService, IssuingBankHibernateService issuingBankHibernateService,
                                   AccountHibernateService accountHibernateService, CardStatusHibernateService cardStatusHibernateService,
                                   PaymentSystemHibernateService paymentSystemHibernateService, CardHibernateService cardHibernateService) {

        cardHibernateService.deleteAll("processingCenterSchema.card");
        accountHibernateService.deleteAll("processingCenterSchema.account");
        currencyHibernateService.deleteAll("processingCenterSchema.currency");
        issuingBankHibernateService.deleteAll("processingCenterSchema.issuing_bank");
        cardStatusHibernateService.deleteAll("processingCenterSchema.card_status");
        paymentSystemHibernateService.deleteAll("processingCenterSchema.payment_system");
    }

    public static void dropTables(CurrencyHibernateService currencyHibernateService, IssuingBankHibernateService issuingBankHibernateService,
                                  AccountHibernateService accountHibernateService, CardStatusHibernateService cardStatusHibernateService,
                                  PaymentSystemHibernateService paymentSystemHibernateService, CardHibernateService cardHibernateService) {
        cardHibernateService.dropTable("processingCenterSchema.card");
        accountHibernateService.dropTable("processingCenterSchema.account");
        currencyHibernateService.dropTable("processingCenterSchema.currency");
        issuingBankHibernateService.dropTable("processingCenterSchema.issuing_bank");
        cardStatusHibernateService.dropTable("processingCenterSchema.card_status");
        paymentSystemHibernateService.dropTable("processingCenterSchema.payment_system");

    }


    public static void insertHibernateCard(CurrencyHibernateService currencyHibernateService, IssuingBankHibernateService issuingBankHibernateService,
                                           AccountHibernateService accountHibernateService, CardStatusHibernateService cardStatusHibernateService,
                                           PaymentSystemHibernateService paymentSystemHibernateService, CardHibernateService cardHibernateService) {

        Currency currency = Currency.builder()
                .currencyName("USD")
                .currencyDigitalCode("555")
                .currencyLetterCode("NEW")
                .build();
        currency = currencyHibernateService.create(currency);


        IssuingBank bank = IssuingBank.builder()
                .bic("12345678)")
                .bin("BINAR")
                .abbreviatedName("ABBREVIATEDBANK")
                .build();
        bank = issuingBankHibernateService.create(bank);


        Account account = Account.builder()
                .accountNumber("9876543210")
                .balance(new BigDecimal("5000.00"))
                .currencyId(currency)
                .issuingBankId(bank)
                .build();
        account = accountHibernateService.create(account);


        CardStatus status = CardStatus.builder()
                .cardStatusName("I dont know")
                .build();
        status = cardStatusHibernateService.create(status);


        PaymentSystem paymentSystem = PaymentSystem.builder()
                .paymentSystemName("VISAAAAAAA")
                .build();
        paymentSystem = paymentSystemHibernateService.create(paymentSystem);


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









