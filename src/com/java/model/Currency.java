package com.java.model;

import java.util.Objects;

public class Currency {
    private Long id;
    private String currencyDigitalCode;
    private String currencyLetterCode;
    private String currencyName;

    public Currency(Long id, String currencyDigitalCode, String currencyLetterCode, String currencyName) {
        if (currencyDigitalCode.length() != 3 || currencyLetterCode.length() != 3) {
            throw new IllegalArgumentException("Код должен содержать ровно 3 символа");
        }
        this.id = id;
        this.currencyDigitalCode = currencyDigitalCode;
        this.currencyLetterCode = currencyLetterCode;
        this.currencyName = currencyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyDigitalCode() {
        return currencyDigitalCode;
    }

    public void setCurrencyDigitalCode(String currencyDigitalCode) {
        this.currencyDigitalCode = currencyDigitalCode;
    }

    public String getCurrencyLetterCode() {
        return currencyLetterCode;
    }

    public void setCurrencyLetterCode(String currencyLetterCode) {
        this.currencyLetterCode = currencyLetterCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(id, currency.id) && Objects.equals(currencyDigitalCode, currency.currencyDigitalCode) && Objects.equals(currencyLetterCode, currency.currencyLetterCode) && Objects.equals(currencyName, currency.currencyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currencyDigitalCode, currencyLetterCode, currencyName);
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", currencyDigitalCode='" + currencyDigitalCode + '\'' +
                ", currencyLetterCode='" + currencyLetterCode + '\'' +
                ", currencyName='" + currencyName + '\'' +
                '}';
    }
}


