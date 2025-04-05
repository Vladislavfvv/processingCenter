package com.java.model;

import java.util.Objects;

public class Terminal {
    private Long id;
    private String terminalId;
    private MerchantCategoryCode mccId;
    private SalesPoint posId;

    public Terminal() {
    }

    public Terminal(Long id, String terminalId, MerchantCategoryCode mccId, SalesPoint posId) {
        this.id = id;
        this.terminalId = terminalId;
        this.mccId = mccId;
        this.posId = posId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public MerchantCategoryCode getMccId() {
        return mccId;
    }

    public void setMccId(MerchantCategoryCode mccId) {
        this.mccId = mccId;
    }

    public SalesPoint getPosId() {
        return posId;
    }

    public void setPosId(SalesPoint posId) {
        this.posId = posId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Terminal terminal = (Terminal) o;
        return Objects.equals(id, terminal.id) && Objects.equals(terminalId, terminal.terminalId) && Objects.equals(mccId, terminal.mccId) && Objects.equals(posId, terminal.posId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, terminalId, mccId, posId);
    }

    @Override
    public String toString() {
        return "Terminal{" +
                "id=" + id +
                ", terminalId='" + terminalId + '\'' +
                ", mccId=" + mccId +
                ", posId=" + posId +
                '}';
    }
}
