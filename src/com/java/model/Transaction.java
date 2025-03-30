package com.java.model;

import java.sql.Timestamp;
import java.util.Date;

public class Transaction {
    private Long id;
    private Date transactionDate;
    private double sum;
    private String transactionName;
    private Long accountId; //change!!!
    private Long transactionTypeId;  //change!!!
    private Long cardId;  //change!!!
    private Long terminalId;  //change!!!
    private Long responseCodeId;  //change!!!
    private String authorizationCode;
    private Timestamp receivedFromIssuingBank;
    private Timestamp sentToIssuingBank;

}
