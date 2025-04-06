package com.java.service;

import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.model.Card;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class CardService implements ServiceInterface<Card, Long> {
    private final DAOInterface<Long, Card> cardDAO;
    private final Connection connection;

    public CardService(Connection connection) {
        // Получаем DAO через фабрику
        this.cardDAO = DAOFactory.getCardDAO(connection);
        this.connection = connection;
    }

    public Card create(Card card) {
        return cardDAO.insert(card);
    }

    public boolean update(Card card) {
        return cardDAO.update(card);
    }

    public boolean delete(Long cardId) {
        return cardDAO.delete(cardId);
    }

    public Optional<Card> findById(Long cardId) {
        return cardDAO.findById(cardId);
    }

    public List<Card> findAll() {
        return cardDAO.findAll();
    }

    @Override
    public void createTable(String sql) {
        cardDAO.createTableQuery(sql);
    }

    @Override
    public boolean deleteAll(String s) {
        return cardDAO.deleteAll(s);
    }

    @Override
    public boolean dropTable(String s) {
        return cardDAO.dropTable(s);
    }
}
