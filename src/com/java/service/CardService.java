package com.java.service;

import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.model.Card;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class CardService {
    private final DAOInterface<Long, Card> cardDAO;
    private final Connection connection;
    public CardService(Connection connection) {
        // Получаем DAO через фабрику
        this.connection = connection;
        this.cardDAO = DAOFactory.getCardDAO(connection);
    }

    public Card createCard(Card card) {
        return cardDAO.insert(card);
    }

    public boolean updateCard(Card card) {
        return cardDAO.update(card);
    }

    public boolean deleteCard(Long cardId) {
        return cardDAO.delete(cardId);
    }

    public Optional<Card> getCard(Long cardId) {
        return cardDAO.findById(cardId);
    }

    public List<Card> getAllCards() {
        return cardDAO.findAll();
    }
}
