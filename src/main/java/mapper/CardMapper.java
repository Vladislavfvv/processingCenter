package mapper;

import dto.CardDto;
import model.Account;
import model.Card;
import model.CardStatus;
import model.PaymentSystem;

public class CardMapper {
    public static Card toEntity(CardDto dto, CardStatus cardStatus, PaymentSystem paymentSystem, Account account) {
        Card card = new Card();
        card.setId(dto.getId());
        card.setCardNumber(String.valueOf(dto.getCardNumber()));
        card.setExpirationDate(dto.getExpirationDate());
        card.setHolderName(dto.getHolderName());
        card.setCardStatusId(cardStatus);
        card.setPaymentSystemId(paymentSystem);
        card.setAccountId(account);
        card.setReceivedFromIssuingBank(dto.getReceivedFromIssuingBank());
        card.setSentToIssuingBank(dto.getSentToIssuingBank());

        return card;
    }

    public static CardDto toDto(Card card) {
        CardDto dto = new CardDto();
        dto.setId(card.getId());
        dto.setCardNumber(card.getCardNumber());
        dto.setExpirationDate(card.getExpirationDate());
        dto.setHolderName(card.getHolderName());
        dto.setCardStatusId(card.getCardStatusId().getId());
        dto.setPaymentSystemId(card.getPaymentSystemId().getId());
        dto.setAccountId(card.getAccountId().getId());
        dto.setReceivedFromIssuingBank(card.getReceivedFromIssuingBank());
        dto.setSentToIssuingBank(card.getSentToIssuingBank());
        return dto;
    }

}
