package com.java.model;

import java.util.Objects;

public class MerchantCategoryCode {
    private Long id;
    private String mcc;
    private String mcc_name;

    public MerchantCategoryCode() {
    }

    public MerchantCategoryCode(Long id, String mcc, String mcc_name  ) {
        this.id = id;
        this.mcc = mcc;
        this.mcc_name = mcc_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        if (mcc.length() != 4) {
            throw new IllegalArgumentException("ErrorCode должен содержать ровно 2 символа.");
        }
        this.mcc = mcc;
    }

    public String getMcc_name() {
        return mcc_name;
    }

    public void setMcc_name(String mcc_name) {
        this.mcc_name = mcc_name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MerchantCategoryCode that = (MerchantCategoryCode) o;
        return Objects.equals(id, that.id) && Objects.equals(mcc, that.mcc) && Objects.equals(mcc_name, that.mcc_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mcc, mcc_name);
    }

    @Override
    public String toString() {
        return "MerchantCategoryCode{" +
                "id=" + id +
                ", mcc='" + mcc + '\'' +
                ", mcc_name='" + mcc_name + '\'' +
                '}';
    }
}

