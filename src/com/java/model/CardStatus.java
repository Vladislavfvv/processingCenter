package com.java.model;

import java.util.Objects;

public class CardStatus {
    private Long id;
    private String cardStatusName;

    public CardStatus() {
    }

    public CardStatus(Long id, String cardStatusName) {
        this.id = id;
        this.cardStatusName = cardStatusName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardStatusName() {
        return cardStatusName;
    }

    public void setCardStatusName(String cardStatusName) {
        if (cardStatusName == null) {
            throw new IllegalArgumentException("cardStatusName не может быть нулем");
        }
        if (cardStatusName.length() > 255) {
            throw new IllegalArgumentException("cardStatusName не может содержать больше  255 символов.");
        }
        this.cardStatusName = cardStatusName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CardStatus that = (CardStatus) o;
        return Objects.equals(id, that.id) && Objects.equals(cardStatusName, that.cardStatusName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardStatusName);
    }

    @Override
    public String toString() {
        return "CardStatus{" +
                "id=" + id +
                ", cardStatusName='" + cardStatusName + '\'' +
                '}';
    }
}