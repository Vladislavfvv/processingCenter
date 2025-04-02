package com.java.service;

import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.model.AcquiringBank;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class AcquiringBankService {

    private final DAOInterface<Long, AcquiringBank> acquiringBankDAO;
    private final Connection connection;

    public AcquiringBankService(Connection connection) {
        this.connection = connection;
        // Получаем DAO через фабрику
        this.acquiringBankDAO = DAOFactory.getAcquiringBankDAO(connection);
    }

    public AcquiringBank create(AcquiringBank acquiringBank) {
        return acquiringBankDAO.insert(acquiringBank);
    }

    public boolean update(AcquiringBank acquiringBank) {
        return acquiringBankDAO.update(acquiringBank);
    }

    public boolean delete(Long acquiringBankId) {
        return acquiringBankDAO.delete(acquiringBankId);
    }

    public Optional<AcquiringBank> findById(Long acquiringBankId) {
        return acquiringBankDAO.findById(acquiringBankId);
    }

    public List<AcquiringBank> findAll() {
        return acquiringBankDAO.findAll();
    }


    // Метод для очистки всех записей из таблицы acquiring_bank
    public boolean clearAcquiringBank() {
        try {
            return acquiringBankDAO.deleteAll("processingcenterschema.acquiring_bank");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Метод для удаления таблицы acquiring_bank
    public boolean removeAcquiringBankTable() {
        try {
            return acquiringBankDAO.dropTable("processingcenterschema.acquiring_bank");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
