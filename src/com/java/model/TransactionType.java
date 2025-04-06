package com.java.model;

import java.util.Objects;

public class TransactionType {
    private Long id;
    private String transactionTypeName;
    private String operator;

    public TransactionType() {
    }

    public TransactionType(Long id, String transactionTypeName, String operator) {
        this.id = id;
        this.transactionTypeName = transactionTypeName;
        this.operator = operator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionTypeName() {
        return transactionTypeName;
    }

    public void setTransactionTypeName(String transactionTypeName) {
        this.transactionTypeName = transactionTypeName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        if (operator.length() != 1) throw new IllegalArgumentException("Название не должно превышать 1 символ.");
        this.operator = operator;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransactionType that = (TransactionType) o;
        return Objects.equals(operator, that.operator) && Objects.equals(id, that.id) && Objects.equals(transactionTypeName, that.transactionTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionTypeName, operator);
    }


    @Override
    public String toString() {
        return "TransactionType{" +
                "id=" + id +
                ", transactionTypeName='" + transactionTypeName + '\'' +
                ", operator=" + operator +
                '}';
    }
}

