package com.edme.pro.mapper;

import com.edme.pro.dto.CardDto;
import com.edme.pro.model.Account;
import com.edme.pro.model.Card;
import com.edme.pro.model.CardStatus;
import com.edme.pro.model.PaymentSystem;

import java.sql.Timestamp;

public class CardMapper {
    public static Card toEntity(CardDto dto, CardStatus cardStatus, PaymentSystem paymentSystem, Account account) {
        Card card = new Card();
        card.setId(dto.getId());
        card.setCardNumber(dto.getCardNumber());
        //card.setExpirationDate(dto.getExpirationDate());
        card.setExpirationDate(java.sql.Date.valueOf(dto.getExpirationDate()));
        card.setHolderName(dto.getHolderName());
        card.setCardStatusId(cardStatus);
        card.setPaymentSystemId(paymentSystem);
        card.setAccountId(account);
        card.setReceivedFromIssuingBank(Timestamp.valueOf(dto.getReceivedFromIssuingBank()));
        card.setSentToIssuingBank(Timestamp.valueOf(dto.getSentToIssuingBank()));

        return card;
    }

    public static CardDto toDto(Card card) {
        CardDto dto = new CardDto();
        dto.setId(card.getId());
        dto.setCardNumber(card.getCardNumber());
        //dto.setExpirationDate(card.getExpirationDate());
//        dto.setExpirationDate(card.getExpirationDate().toLocalDate());
        dto.setExpirationDate(card.getExpirationDate() != null
                ? card.getExpirationDate().toLocalDate()
                : null);
        dto.setHolderName(card.getHolderName());
        dto.setCardStatusId(card.getCardStatusId().getId());
        dto.setPaymentSystemId(card.getPaymentSystemId().getId());
        dto.setAccountId(card.getAccountId().getId());
        dto.setReceivedFromIssuingBank(card.getReceivedFromIssuingBank().toLocalDateTime());
        //dto.setSentToIssuingBank(card.getSentToIssuingBank());
        dto.setSentToIssuingBank(card.getSentToIssuingBank().toLocalDateTime());
        return dto;
    }

}
