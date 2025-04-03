package com.java.model;

import java.util.Objects;

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
        if (paymentSystemName == null) {
            throw new IllegalArgumentException("cardStatusName не может быть нулем");
        }
        if (paymentSystemName.length() > 50) {
            throw new IllegalArgumentException("cardStatusName не может содержать больше  255 символов.");
        }

        this.paymentSystemName = paymentSystemName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PaymentSystem that = (PaymentSystem) o;
        return Objects.equals(id, that.id) && Objects.equals(paymentSystemName, that.paymentSystemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentSystemName);
    }


    @Override
    public String toString() {
        return "PaymentSystem{" +
                "id=" + id +
                ", paymentSystemName='" + paymentSystemName + '\'' +
                '}';
    }
}
