package com.java.model;

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
}


