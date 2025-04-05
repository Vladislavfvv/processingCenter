package com.java.model;

public class TransactionType {
    private Long id;
    private String transactionTypeName;
    private char operator;

    public TransactionType() {
    }

    public TransactionType(Long id, String transactionTypeName, char operator) {
        this.id = id;
        this.transactionTypeName = transactionTypeName;
        this.operator = operator;
    }
}

