package service.hibernate;

import model.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class TestDataHibernateLoader {
    public static void loadData(
            CardStatusHibernateService cardStatusHibernateService,
            PaymentSystemHibernateService paymentSystemHibernateService,
            CurrencyHibernateService currencyHibernateService,
            IssuingBankHibernateService issuingBankHibernateService,
            //AcquiringBankHibernateService acquiringBankService,
            //SalesPointHibernateService salesPointService,
            //MerchantCategoryCodeHibernateService merchantCategoryCodeService,
            //ResponseCodeHibernateService responseCodeService,
            //TransactionTypeHibernateService transactionTypeService,
            AccountHibernateService accountHibernateService,
            CardHibernateService cardHibernateService
    ) {

        String[] statuses = {
                "Card is not active", "Card is valid", "Card is temporarily blocked",
                "Card is lost", "Card is compromised"
        };
        for (String statusName : statuses) {
            cardStatusHibernateService.create(CardStatus.builder()
                    .cardStatusName(statusName)
                    .build());
        }


        String[] systems = {
                "VISA International Service Association", "Mastercard", "JCB",
                "American Express", "Diners Club International", "China UnionPay"
        };
        for (String systemName : systems) {
            paymentSystemHibernateService.create(PaymentSystem.builder()
                    .paymentSystemName(systemName)
                    .build());
        }


        String[][] currencies = {
                {"643", "RUB", "Russian Ruble"},
                {"980", "UAN", "Hryvnia"},
                {"840", "USD", "US Dollar"},
                {"978", "EUR", "Euro"},
                {"392", "JPY", "Yen"},
                {"156", "CNY", "Yuan Renminbi"},
                {"826", "GBP", "Pound Sterling"}
        };
        for (String[] data : currencies) {
            currencyHibernateService.create(Currency.builder()
                    .currencyDigitalCode(data[0])
                    .currencyLetterCode(data[1])
                    .currencyName(data[2])
                    .build());
        }


        String[][] banks = {
                {"041234569", "12345", "ОАО Приорбанк"},
                {"041234570", "45256", "ОАО Сбербанк"},
                {"041234571", "98725", "ЗАО МТБ Банк"}
        };
        for (String[] data : banks) {
            issuingBankHibernateService.create(IssuingBank.builder()
                    .bic(data[0])
                    .bin(data[1])
                    .abbreviatedName(data[2])
                    .build());
        }


//        String[][] acquiringBanks = {
//                {"041234567", "ПАО Банк-эквайер №1"},
//                {"041234568", "ПАО Банк-эквайер №2"},
//                {"041234569", "ПАО Банк-эквайер №3"}
//        };
//        for (String[] data : acquiringBanks) {
//            acquiringBankHibernateService.create(AcquiringBank.builder()
//                    .bic(data[0])
//                    .abbreviatedName(data[1])
//                    .build());
//        }


//        salesPointService.create(SalesPoint.builder()
//                .posName("Shop №1")
//                .posAddress("City 1-st 1")
//                .posInn("123456788888")
//                .acquiringBankId(acquiringBankService.findById(1))
//                .build());
//
//        salesPointService.create(SalesPoint.builder()
//                .posName("Shop №2")
//                .posAddress("City 1-st 2")
//                .posInn("123456788889")
//                .acquiringBankId(acquiringBankService.findById(2))
//                .build());
//
//
//        String[][] mccs = {
//                {"5309", "Беспошлинные магазины Duty Free"},
//                {"5651", "Одежда для всей семьи"},
//                {"5691", "Магазины мужской и женской одежды"},
//                {"5812", "Места общественного питания, рестораны"},
//                {"5814", "Фастфуд"}
//        };
//        for (String[] data : mccs) {
//            merchantCategoryCodeService.create(MerchantCategoryCode.builder()
//                    .mcc(data[0])
//                    .mccName(data[1])
//                    .build());
//        }


//        String[][] responses = {
//                {"00", "одобрено и завершено", "Все в порядке"},
//                {"01", "авторизация отклонена, обратиться в банк-эмитент", "не критическая"},
//                {"03", "незарегистрированная торговая точка или агрегатор платежей", "не критическая"},
//                {"05", "авторизация отклонена, оплату не проводить", "критическая"},
//                {"41", "карта утеряна, изъять", "критическая"},
//                {"51", "недостаточно средств на счёте", "сервисная или аппаратная ошибка"},
//                {"55", "неправильный PIN", "не критическая"}
//        };
//        for (String[] data : responses) {
//            responseCodeService.create(ResponseCode.builder()
//                    .errorCode(data[0])
//                    .errorDescription(data[1])
//                    .errorLevel(data[2])
//                    .build());
//        }


//        String[][] transactionTypes = {
//                {"Списание со счета", "-"},
//                {"Пополнение счета", "+"}
//        };
//        for (String[] data : transactionTypes) {
//            transactionTypeService.create(TransactionType.builder()
//                    .transactionTypeName(data[0])
//                    .operator(data[1])
//                    .build());
//        }

        // Account
        accountHibernateService.create(Account.builder()
                .accountNumber("40817810800000000001")
                .balance(new BigDecimal("649.7"))
                .currencyId(currencyHibernateService.findById(1L).orElseThrow())
                .issuingBankId(issuingBankHibernateService.findById(1L).orElseThrow())
                .build());

        // Card
        cardHibernateService.create(Card.builder()
                .cardNumber("4123450000000019")
                .expirationDate(Date.valueOf("2025-12-31"))
                .holderName("IVAN I. IVANOV")
                .cardStatusId(cardStatusHibernateService.findById(2L).orElseThrow())
                .paymentSystemId(paymentSystemHibernateService.findById(1L).orElseThrow())
                .accountId(accountHibernateService.findById(1L).orElseThrow())
                .receivedFromIssuingBank(Timestamp.valueOf("2022-10-21 15:26:06.175"))
                .sentToIssuingBank(Timestamp.valueOf("2022-10-21 15:27:08.271"))
                .build());
    }
}
