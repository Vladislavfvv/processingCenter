package com.java.model;

public class PaymentSystem {
    private Long id;
    private String paymentSystemName;

    public PaymentSystem() {
    }

    public PaymentSystem(Long id, String paymentSystemName) {
        this.id = id;
        this.paymentSystemName = paymentSystemName;
    }

    public String getPaymentSystemName() {
        return paymentSystemName;
    }

    public void setPaymentSystemName(String paymentSystemName) {
        this.paymentSystemName = paymentSystemName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
