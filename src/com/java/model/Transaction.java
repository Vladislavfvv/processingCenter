package com.java.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.Objects;

public class Transaction {
    private Long id;
    private Date transactionDate;
    private BigDecimal sum;
    private String transactionName;
    private Account accountId; //change!!!
    private TransactionType transactionTypeId;  //change!!!
    private Card cardId;  //change!!!
    private Terminal terminalId;  //change!!!
    private ResponseCode responseCodeId;  //change!!!
    private String authorizationCode;
    private Timestamp receivedFromIssuingBank;
    private Timestamp sentToIssuingBank;


    public Transaction() {
    }

    public Transaction(Long id, Date transactionDate, BigDecimal sum, String transactionName, Account accountId, TransactionType transactionTypeId, Card cardId, Terminal terminalId, ResponseCode responseCodeId, String authorizationCode, Timestamp receivedFromIssuingBank, Timestamp sentToIssuingBank) {
        this.id = id;
        this.transactionDate = transactionDate;
        this.sum = sum;
        this.transactionName = transactionName;
        this.accountId = accountId;
        this.transactionTypeId = transactionTypeId;
        this.cardId = cardId;
        this.terminalId = terminalId;
        this.responseCodeId = responseCodeId;
        this.authorizationCode = authorizationCode;
        this.receivedFromIssuingBank = receivedFromIssuingBank;
        this.sentToIssuingBank = sentToIssuingBank;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    public TransactionType getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(TransactionType transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public Card getCardId() {
        return cardId;
    }

    public void setCardId(Card cardId) {
        this.cardId = cardId;
    }

    public Terminal getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Terminal terminalId) {
        this.terminalId = terminalId;
    }

    public ResponseCode getResponseCodeId() {
        return responseCodeId;
    }

    public void setResponseCodeId(ResponseCode responseCodeId) {
        this.responseCodeId = responseCodeId;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        if (authorizationCode.length() != 6) {
            throw new IllegalArgumentException("authorizationCode должен содержать 6 символов.");
        }
        this.authorizationCode = authorizationCode;
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
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && Objects.equals(sum, that.sum) && Objects.equals(transactionName, that.transactionName) && Objects.equals(accountId, that.accountId) && Objects.equals(transactionTypeId, that.transactionTypeId) && Objects.equals(cardId, that.cardId) && Objects.equals(terminalId, that.terminalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sum, transactionName, accountId, transactionTypeId, cardId, terminalId);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", transactionDate=" + transactionDate +
                ", sum=" + sum +
                ", transactionName='" + transactionName + '\'' +
                ", accountId=" + accountId +
                ", transactionTypeId=" + transactionTypeId +
                ", cardId=" + cardId +
                ", terminalId=" + terminalId +
                ", responseCodeId=" + responseCodeId +
                ", authorizationCode='" + authorizationCode + '\'' +
                ", receivedFromIssuingBank=" + receivedFromIssuingBank +
                ", sentToIssuingBank=" + sentToIssuingBank +
                '}';
    }
}
