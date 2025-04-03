package com.java.service;

import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.model.CardStatus;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class CardStatusService {
    Logger logger = Logger.getLogger(CardStatusService.class.getName());
    private final DAOInterface<Long, CardStatus> cardStatusDAO;
    private final Connection connection;

    public CardStatusService(Connection connection) {
        this.connection = connection;
        // Получаем DAO через фабрику
        this.cardStatusDAO = DAOFactory.getCardStatusDAO(connection);
    }

    public CardStatus create(CardStatus cardStatus) {
        return cardStatusDAO.insert(cardStatus);
    }

    public boolean update(CardStatus cardStatus) {
        return cardStatusDAO.update(cardStatus);
    }

    public boolean delete(Long id) {
        return cardStatusDAO.delete(id);
    }

    public Optional<CardStatus> findById(Long id) {
        return cardStatusDAO.findById(id);
    }

    public List<CardStatus> findAll() {
        return cardStatusDAO.findAll();
    }

    // Метод для очистки всех записей из таблицы
    public boolean clearCardStatus() {
        try {
            return cardStatusDAO.deleteAll("card_status");
        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
    }

    // Метод для удаления таблицы
    public boolean removeCardStatus(CardStatus cardStatus) {
        try {
            return cardStatusDAO.dropTable("card_status");
        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
    }


}
