package com.java.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Account{
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private Currency currencyId;
    private IssuingBank issuingBankId;

    public Account() {
    }

    public Account(Long id, String accountNumber, BigDecimal balance, Currency currencyId, IssuingBank issuingBankId) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currencyId = currencyId;
        this.issuingBankId = issuingBankId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        if (accountNumber.length() > 255) {
            throw new IllegalArgumentException("accountNumber не может содержать больше 50 символов.");
        }
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Currency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Currency currencyId) {
        this.currencyId = currencyId;
    }

    public IssuingBank getIssuingBankId() {
        return issuingBankId;
    }

    public void setIssuingBankId(IssuingBank issuingBankId) {
        this.issuingBankId = issuingBankId;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(balance, account.balance) && Objects.equals(id, account.id) && Objects.equals(accountNumber, account.accountNumber) && Objects.equals(currencyId, account.currencyId) && Objects.equals(issuingBankId, account.issuingBankId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, balance, currencyId, issuingBankId);
    }


    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", currencyId=" + currencyId +
                ", issuingBankId=" + issuingBankId +
                '}';
    }
}
