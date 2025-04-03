package com.java.service;

import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.model.CardStatus;
import com.java.model.PaymentSystem;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class PaymentSystemService {
    Logger logger = Logger.getLogger(PaymentSystemService.class.getName());
    private final DAOInterface<Long, PaymentSystem> paymentSystemDAO;
    private final Connection connection;

    public PaymentSystemService(Connection connection) {
        this.paymentSystemDAO = DAOFactory.getPaymentSystemDAO(connection);
        this.connection = connection;
    }


    public PaymentSystem create(PaymentSystem paymentSystem) {
        return paymentSystemDAO.insert(paymentSystem);
    }

    public boolean update(PaymentSystem paymentSystem) {
        return paymentSystemDAO.update(paymentSystem);
    }

    public boolean delete(Long id) {
        return paymentSystemDAO.delete(id);
    }

    public Optional<PaymentSystem> findById(Long id) {
        return paymentSystemDAO.findById(id);
    }

    public List<PaymentSystem> findAll() {
        return paymentSystemDAO.findAll();
    }

    // Метод для очистки всех записей из таблицы
    public boolean clearCardStatus() {
        try {
            return paymentSystemDAO.deleteAll("payment_system");
        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
    }

    // Метод для удаления таблицы
    public boolean removeCardStatus(CardStatus cardStatus) {
        try {
            return paymentSystemDAO.dropTable("payment_system");
        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
    }
}
