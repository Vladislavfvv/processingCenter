package com.java.service;

import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.model.CardStatus;
import com.java.model.ResponseCode;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ResponseCodeService {
    Logger logger = Logger.getLogger(ResponseCodeService.class.getName());
    private final DAOInterface<Long, ResponseCode> responseCodeDAO;
    private final Connection connection;

    public ResponseCodeService(Connection connection) {
        this.connection = connection;
        // Получаем DAO через фабрику
        this.responseCodeDAO = DAOFactory.getResponseCodeDAO(connection);
    }

    public ResponseCode create(ResponseCode responseCode) {
        return responseCodeDAO.insert(responseCode);
    }

    public boolean update(ResponseCode responseCode) {
        return responseCodeDAO.update(responseCode);
    }

    public boolean delete(Long id) {
        return responseCodeDAO.delete(id);
    }

    public Optional<ResponseCode> findById(Long id) {
        return responseCodeDAO.findById(id);
    }

    public List<ResponseCode> findAll() {
        return responseCodeDAO.findAll();
    }

    // Метод для очистки всех записей из таблицы
    public boolean clearCardStatus() {
        try {
            return responseCodeDAO.deleteAll("response_code");
        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
    }

    // Метод для удаления таблицы
    public boolean removeCardStatus(ResponseCode responseCode) {
        try {
            return responseCodeDAO.dropTable("response_code");
        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
    }
}
