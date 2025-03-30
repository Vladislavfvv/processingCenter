package com.java.model;

import java.sql.Timestamp;
import java.util.Date;

public class Card{

    private Long id;
    private String cardNumber;
    private Date expirationDate;
    private String holderName;
    private Long cardStatusId; //change!!!
    private Long paymentSystemId; //change!!!
    private Long accountId; //change!!!
    private Timestamp receivedFromIssuingBank;
    private Timestamp sentToIssuingBank;

    public Card() {
    }


}
