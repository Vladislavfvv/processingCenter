package com.java.model;

import java.util.Objects;

public class AcquiringBank {
    private Long id;
    private String bic;
    private String abbreviatedName;

    public AcquiringBank() {
    }

    public AcquiringBank(String bic, String abbreviatedName) {
        setBic(bic);
        setAbbreviatedName(abbreviatedName);
    }

    public AcquiringBank(Long id, String bic, String abbreviatedName) {
        this(bic, abbreviatedName);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbbreviatedName() {
        return abbreviatedName;
    }

    public void setAbbreviatedName(String abbreviatedName) {
        if (abbreviatedName == null) {
            throw new IllegalArgumentException("Название не может быть нулем");
        }
        if(abbreviatedName.length() > 255){
            throw new IllegalArgumentException("Название не должно превышать 255 символов.");
        }
        this.abbreviatedName = abbreviatedName;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        if (bic == null) {
            throw new IllegalArgumentException("BIC не может быть нулем");
        }
        if (bic.length() != 9) {
            throw new IllegalArgumentException("BIC должен содержать ровно 9 символов.");
        }
        this.bic = bic;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AcquiringBank that = (AcquiringBank) o;
        return Objects.equals(id, that.id) && Objects.equals(bic, that.bic) && Objects.equals(abbreviatedName, that.abbreviatedName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bic, abbreviatedName);
    }

    @Override
    public String toString() {
        return "AcquiringBank{" +
                "id=" + id +
                ", bic='" + bic + '\'' +
                ", abbreviatedName='" + abbreviatedName + '\'' +
                '}';
    }
}
