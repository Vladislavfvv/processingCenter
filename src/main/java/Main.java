

import dto.*;
import lombok.extern.slf4j.Slf4j;
import model.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import service.spring.*;

import util.jdbc.*;

import java.math.BigDecimal;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@Slf4j
public class Main {
    public static void main(String[] args) throws SQLException {

        // Создаем Spring-контекст на основе Java-конфигурации
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Получаем CurrencySpringService из контекста
        CurrencySpringService cs = context.getBean(CurrencySpringService.class);
        PaymentSystemDtoSpringService ps = context.getBean(PaymentSystemDtoSpringService.class);
        CardStatusDtoSpringService css = context.getBean(CardStatusDtoSpringService.class);
        IssuingBankDtoSpringService ib = context.getBean(IssuingBankDtoSpringService.class);
        AccountDtoSpringService as = context.getBean(AccountDtoSpringService.class);
        CardDtoSpringService card = context.getBean(CardDtoSpringService.class);

        insertData(context, cs, ps, css, ib, as, card);


        //clearTablesSpring(context, cs, ps, css, ib, as, card);
        printDataSpring(context, cs, ps, css, ib, as, card);
      //  dropTablesSpring(context, cs, ps, css, ib, as, card);
      //  printDataSpring(context, cs, ps, css, ib, as, card);



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
}









