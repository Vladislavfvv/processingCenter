INSERT INTO processingCenterSchema.card_status(card_status_name)
VALUES ('Card is not active'),
       ('Card is valid'),
       ('Card is temporarily blocked'),
       ('Card is lost'),
       ('Card is compromised');

insert into processingCenterSchema.payment_system (payment_system_name)
values ('VISA International Service Association'),
       ('Mastercard'),
       ('JCB'),
       ('American Express'),
       ('Diners Club International'),
       ('China UnionPay ');

INSERT INTO processingCenterSchema.currency(currency_digital_code,
                                            currency_letter_code,
                                            currency_name)
VALUES ('643', 'RUB', 'Russian Ruble'),
       ('980', 'UAN', 'Hryvnia'),
       ('840', 'USD', 'US Dollar'),
       ('978', 'EUR', 'Euro'),
       ('392', 'JPY', 'Yen'),
       ('156', 'CNY', 'Yuan Renminbi'),
       ('826', 'GBP', 'Pound Sterling');

INSERT INTO processingCenterSchema.issuing_bank (bic,
                                                 bin,
                                                 abbreviated_name)
VALUES ('041234569', '12345', 'ОАО Приорбанк '),
       ('041234570', '45256', 'ОАО Сбербанк '),
       ('041234571', '98725', 'ЗАО МТБ Банк ');

INSERT INTO processingCenterSchema.acquiring_bank (bic,
                                                   abbreviated_name)
VALUES ('041234567', 'ПАО Банк-эквайер №1'),
       ('041234568', 'ПАО Банк-эквайер №2'),
       ('041234569', 'ПАО Банк-эквайер №3');

INSERT INTO processingCenterSchema.sales_point (pos_name,
                                                pos_address,
                                                pos_inn,
                                                acquiring_bank_id)
VALUES ('Shop №1', 'City 1-st 1', '12345678', 1),
       ('Shop №2 ', 'City, 2-st 2 ', '1591487', 2),
       ('Shop №3', 'City, 3-st 3 ', '1235962', 1);

INSERT INTO processingCenterSchema.merchant_category_code (mcc,
                                                           mcc_name)
VALUES ('5309', 'Беспошлинные магазины Duty Free'),
       ('5651', 'Одежда для всей семьи'),
       ('5691', 'Магазины мужской и женской одежды'),
       ('5812', 'Места общественного питания, рестораны'),
       ('5814', 'Фастфуд');

INSERT INTO processingCenterSchema.terminal (terminal_id,
                                             mcc_id,
                                             pos_id)
VALUES ('00000001', 1, 1),
       ('00000002', 2, 2),
       ('00000003', 3, 3);


INSERT INTO processingCenterSchema.response_code (error_code,
                                                  error_description,
                                                  error_level)
VALUES ('00', 'одобрено и завершено', 'Все в порядке'),
       ('01', 'авторизация отклонена, обратиться в банк-эмитент', 'не критическая'),
       ('03', 'незарегистрированная торговая точка или агрегатор платежей', 'не критическая'),
       ('05', 'авторизация отклонена, оплату не проводить', 'критическая'),
       ('41', 'карта утеряна, изъять', 'критическая'),
       ('51', 'недостаточно средств на счёте', 'сервисная или аппаратная ошибка'),
       ('55', 'неправильный PIN', 'не критическая');


INSERT INTO processingCenterSchema.transaction_type (transaction_type_name,
                                                     operator)
VALUES ('Списание со счета ', '-'),
       ('Пополнение счета', '+');


INSERT INTO processingCenterSchema.account (account_number,
                                            balance,
                                            currency_id,
                                            issuing_bank_id)
VALUES ('40817810800000000001', 649.7, 1, 1),
       ('40817810100000000002', 48702.07, 1, 1),
       ('40817810400000000003', 715000.01, 1, 1),
       ('40817810400000000003', 10000.0, 3, 1);


INSERT INTO processingCenterSchema.card (card_number,
                                         expiration_date,
                                         holder_name,
                                         card_status_id,
                                         payment_system_id,
                                         account_id,
                                         received_from_issuing_bank,
                                         sent_to_issuing_bank)
VALUES ('4123450000000019', '2025-12-31', 'IVAN I. IVANOV', 2, 1, 1, '2022-10-21 15:26:06.175', '2022-10-21 15:27:08.271'),
       ('5123450000000024', '2025-12-31', 'SEMION E. PETROV', 3,2,2, '2022-04-05 10:23:05.372', '2022-04-05 10:24:02.175');


INSERT INTO processingCenterSchema.transaction (transaction_date,
                                                sum,
                                                transaction_name,
                                                account_id,
                                                transaction_type_id,
                                                card_id,
                                                terminal_id,
                                                response_code_id,
                                                authorization_code,
                                                received_from_issuing_bank,
                                                sent_to_issuing_bank)
VALUES ('2022-10-22', 10.11, 'Cash deposit', 1, 2, null, null, null, null, '2022-10-22 09:05:23.129', '2022-10-22 09:00:00.000'),
       ('2022-04-06', 50.92, 'Cash deposit', 2, 2, null, null, null, null,'2022-04-06 12:03:41.861', '2022-04-06 11:50:20.125');