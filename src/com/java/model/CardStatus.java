package com.java.model;

public class CardStatus{
    private Long id;
    private String cardStatusName;


    public CardStatus(Long id, String cardStatusName) {
       this.id = id;
        this.cardStatusName = cardStatusName;
    }

    public String getCardStatusName() {
        return cardStatusName;
    }

    public void setCardStatusName(String cardStatusName) {
        this.cardStatusName = cardStatusName;
    }
}
