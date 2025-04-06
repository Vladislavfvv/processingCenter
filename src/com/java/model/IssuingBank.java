package com.java.model;

import java.util.Objects;

public class IssuingBank {
    private Long id;
    private String bic;
    private String bin;
    private String abbreviatedName;

    public IssuingBank() {
    }

    public IssuingBank(Long id, String bic, String bin, String abbreviatedName) {
        this.id = id;
        this.bic = bic;
        this.bin = bin;
        this.abbreviatedName = abbreviatedName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getAbbreviatedName() {
        return abbreviatedName;
    }

    public void setAbbreviatedName(String abbreviatedName) {
        this.abbreviatedName = abbreviatedName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        IssuingBank that = (IssuingBank) o;
        return Objects.equals(id, that.id) && Objects.equals(bic, that.bic) && Objects.equals(bin, that.bin) && Objects.equals(abbreviatedName, that.abbreviatedName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bic, bin, abbreviatedName);
    }

    @Override
    public String toString() {
        return "IssuingBank{" +
                "id=" + id +
                ", bic='" + bic + '\'' +
                ", bin='" + bin + '\'' +
                ", abbreviatedName='" + abbreviatedName + '\'' +
                '}';
    }
}
