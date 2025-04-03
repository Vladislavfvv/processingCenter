package com.java.model;

public class SalesPoint {
    private Long id;
    private String posName;
    private String posAddress;
    private String posInn;
    public AcquiringBank acquiringBank; //ok

    public SalesPoint() {
    }

    public SalesPoint(String posName, String posAddress, String posInn, AcquiringBank acquiringBank) {
        setPosName(posName);
        setPosAddress(posAddress);
        setPosInn(posInn);
        setAcquiringBank(acquiringBank);
    }

    public SalesPoint(Long id, String posName, String posAddress, String posInn, AcquiringBank acquiringBank) {
        this(posName, posAddress, posInn, acquiringBank);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        if (posName == null) {
            throw new IllegalArgumentException("posName не может быть нулем");
        }
        if (posName.length() > 255) {
            throw new IllegalArgumentException("posName не может содержать больше  255 символов.");
        }
        this.posName = posName;
    }

    public String getPosAddress() {
        return posAddress;
    }

    public void setPosAddress(String posAddress) {
        if (posAddress == null) {
            throw new IllegalArgumentException("posAddress не может быть нулем");
        }
        if (posAddress.length() > 255) {
            throw new IllegalArgumentException("posAddress не может содержать больше  255 символов.");
        }
        this.posAddress = posAddress;
    }

    public String getPosInn() {
        return posInn;
    }

    public void setPosInn(String posInn) {
        if (posInn == null) {
            throw new IllegalArgumentException("ИНН не должен быть пустым");
        } else if (posInn.length() != 12) {
            throw new IllegalArgumentException("ИНН должен содержать ровно 12 символов.");
        }
       else this.posInn = posInn;
    }

    public AcquiringBank getAcquiringBank() {
        return acquiringBank;
    }

    public void setAcquiringBank(AcquiringBank acquiringBank) {
        this.acquiringBank = acquiringBank;
    }

    @Override
    public String toString() {
        return "SalesPoint{" +
                "id=" + id +
                ", posName='" + posName + '\'' +
                ", posAddress='" + posAddress + '\'' +
                ", posInn='" + posInn + '\'' +
                ", acquiringBank=" + acquiringBank +
                '}';
    }
}
