CREATE DATABASE processingCenter;
CREATE SCHEMA processingCenterSchema;

DROP TABLE IF EXISTS processingCenterSchema.card_status;
DROP TABLE IF EXISTS processingCenterSchema.payment_system;
DROP TABLE IF EXISTS processingCenterSchema.currency;
DROP TABLE IF EXISTS processingCenterSchema.issuing_bank;
DROP TABLE IF EXISTS processingCenterSchema.acquiring_bank;
DROP TABLE IF EXISTS processingCenterSchema.sales_point;
DROP TABLE IF EXISTS processingCenterSchema.terminal;
DROP TABLE IF EXISTS processingCenterSchema.response_code;
DROP TABLE IF EXISTS processingCenterSchema.transaction_type;
DROP TABLE IF EXISTS processingCenterSchema.account;
DROP TABLE IF EXISTS processingCenterSchema.card;
DROP TABLE IF EXISTS processingCenterSchema.transaction;
DROP SCHEMA IF EXISTS processingCenter.processingCenterSchema;
DROP DATABASE IF EXISTS processingCenter;


CREATE TABLE IF NOT EXISTS processingCenterSchema.card_status
(
    id               bigserial primary key,
    card_status_name varchar(255) UNIQUE not null
    );
CREATE TABLE IF NOT EXISTS processingCenterSchema.payment_system
(
    id                  bigserial primary key,
    payment_system_name varchar(50) UNIQUE not null
    );
CREATE TABLE IF NOT EXISTS processingCenterSchema.currency
(
    id                    bigserial primary key,
    currency_digital_code varchar(3)   not null,
    currency_letter_code  varchar(3)   not null,
    currency_name         varchar(255) not null
    );
CREATE TABLE IF NOT EXISTS processingCenterSchema.issuing_bank
(
    id               bigserial primary key,
    bic              varchar(9)   not null,
    bin              varchar(5)   not null,
    abbreviated_name varchar(255) not null
    );
CREATE TABLE IF NOT EXISTS acquiring_bank
(
    id               bigserial primary key,
    bic              varchar(9)   not null,
    abbreviated_name varchar(255) not null
    );
CREATE TABLE IF NOT EXISTS processingCenterSchema.sales_point
(
    id                bigserial primary key,
    pos_name          varchar(255) not null,
    pos_address       varchar(255) not null,
    pos_inn           varchar(12)  not null,
    acquiring_bank_id bigint REFERENCES processingCenterSchema.acquiring_bank (id) ON DELETE CASCADE
                                                            ON UPDATE CASCADE
    );
CREATE TABLE IF NOT EXISTS processingCenterSchema.merchant_category_code
(
    id       bigserial primary key,
    mcc      varchar(4),
    mcc_name varchar(255)
    );
CREATE TABLE IF NOT EXISTS processingCenterSchema.terminal
(
    id          bigserial primary key,
    terminal_id varchar(9),
    mcc_id      bigint REFERENCES processingCenterSchema.merchant_category_code (id) ON DELETE CASCADE
                                                              ON UPDATE CASCADE,
    pos_id      bigint REFERENCES processingCenterSchema.sales_point (id) ON DELETE CASCADE
                                                              ON UPDATE CASCADE
    );
CREATE TABLE IF NOT EXISTS processingCenterSchema.response_code
(
    id                bigserial primary key,
    error_code        varchar(2),
    error_description varchar(255),
    error_level       varchar(255)
    );
CREATE TABLE IF NOT EXISTS processingCenterSchema.transaction_type
(
    id                    bigserial primary key,
    transaction_type_name varchar(255),
    operator              varchar(1)
    );
CREATE TABLE IF NOT EXISTS processingCenterSchema.account
(
    id              bigserial primary key,
    account_number  varchar(50),
    balance         decimal,
    currency_id     bigint REFERENCES processingCenterSchema.currency (id) ON DELETE CASCADE
                                                    ON UPDATE CASCADE,
    issuing_bank_id bigint REFERENCES processingCenterSchema.issuing_bank (id) ON DELETE CASCADE
                                                    ON UPDATE CASCADE
    );
CREATE TABLE IF NOT EXISTS processingCenterSchema.card
(
    id                         bigserial primary key,
    card_number                varchar(50),
    expiration_date            date,
    holder_name                varchar(50),
    card_status_id             bigint REFERENCES processingCenterSchema.card_status (id) ON DELETE CASCADE
                                                                  ON UPDATE CASCADE,
    payment_system_id          bigint REFERENCES processingCenterSchema.payment_system (id) ON DELETE CASCADE
                                                                  ON UPDATE CASCADE,
    account_id                 bigint REFERENCES processingCenterSchema.account ON DELETE CASCADE
                                                                  ON UPDATE CASCADE,
    received_from_issuing_bank timestamp,
    sent_to_issuing_bank       timestamp
    );
CREATE TABLE IF NOT EXISTS processingCenterSchema.transaction
(
    id                         bigserial primary key,
    transaction_date           date,
    sum                        decimal,
    transaction_name           varchar(255),
    account_id                 bigint REFERENCES processingCenterSchema.account ON DELETE CASCADE
                                                         ON UPDATE CASCADE,
    transaction_type_id        bigint REFERENCES processingCenterSchema.transaction_type (id) ON DELETE CASCADE
                                                         ON UPDATE CASCADE,
    card_id                    bigint REFERENCES processingCenterSchema.card (id) ON DELETE CASCADE
                                                         ON UPDATE CASCADE,
    terminal_id                bigint REFERENCES processingCenterSchema.terminal ON DELETE CASCADE
                                                         ON UPDATE CASCADE,
    response_code_id           bigint REFERENCES processingCenterSchema.response_code ON DELETE CASCADE
                                                         ON UPDATE CASCADE,
    authorization_code         varchar(6),
    received_from_issuing_bank timestamp,
    sent_to_issuing_bank       timestamp
    );


