package com.java.model;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.Objects;

public class Card{

    private Long id;
    private String cardNumber;
    private Date expirationDate;
    private String holderName;
    private CardStatus cardStatusId;
    private PaymentSystem paymentSystemId; //change!!!
    private Account accountId; //change!!!
    private Timestamp receivedFromIssuingBank;
    private Timestamp sentToIssuingBank;

    public Card() {
    }

    public Card(Long id, String cardNumber, Date expirationDate, String holderName, CardStatus cardStatusId, PaymentSystem paymentSystemId, Account accountId, Timestamp receivedFromIssuingBank, Timestamp sentToIssuingBank) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.holderName = holderName;
        this.cardStatusId = cardStatusId;
        this.paymentSystemId = paymentSystemId;
        this.accountId = accountId;
        this.receivedFromIssuingBank = receivedFromIssuingBank;
        this.sentToIssuingBank = sentToIssuingBank;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public CardStatus getCardStatusId() {
        return cardStatusId;
    }

    public void setCardStatusId(CardStatus cardStatusId) {
        this.cardStatusId = cardStatusId;
    }

    public PaymentSystem getPaymentSystemId() {
        return paymentSystemId;
    }

    public void setPaymentSystemId(PaymentSystem paymentSystemId) {
        this.paymentSystemId = paymentSystemId;
    }

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    public Timestamp getReceivedFromIssuingBank() {
        return receivedFromIssuingBank;
    }

    public void setReceivedFromIssuingBank(Timestamp receivedFromIssuingBank) {
        this.receivedFromIssuingBank = receivedFromIssuingBank;
    }

    public Timestamp getSentToIssuingBank() {
        return sentToIssuingBank;
    }

    public void setSentToIssuingBank(Timestamp sentToIssuingBank) {
        this.sentToIssuingBank = sentToIssuingBank;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id) && Objects.equals(cardNumber, card.cardNumber) && Objects.equals(expirationDate, card.expirationDate) && Objects.equals(holderName, card.holderName) && Objects.equals(cardStatusId, card.cardStatusId) && Objects.equals(paymentSystemId, card.paymentSystemId) && Objects.equals(accountId, card.accountId) && Objects.equals(receivedFromIssuingBank, card.receivedFromIssuingBank) && Objects.equals(sentToIssuingBank, card.sentToIssuingBank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNumber, expirationDate, holderName, cardStatusId, paymentSystemId, accountId, receivedFromIssuingBank, sentToIssuingBank);
    }


    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate=" + expirationDate +
                ", holderName='" + holderName + '\'' +
                ", cardStatusId=" + cardStatusId +
                ", paymentSystemId=" + paymentSystemId +
                ", accountId=" + accountId +
                ", receivedFromIssuingBank=" + receivedFromIssuingBank +
                ", sentToIssuingBank=" + sentToIssuingBank +
                '}';
    }
}
