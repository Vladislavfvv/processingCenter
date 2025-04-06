package com.java.service;

import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.model.AcquiringBank;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class AcquiringBankService implements ServiceInterface<AcquiringBank, Long> {

    private final DAOInterface<Long, AcquiringBank> acquiringBankDAO;
    private final Connection connection;

    public AcquiringBankService(Connection connection) {
        this.connection = connection;
        // Получаем DAO через фабрику
        this.acquiringBankDAO = DAOFactory.getAcquiringBankDAO(connection);
    }

    @Override
    public AcquiringBank create(AcquiringBank acquiringBank) {
        return acquiringBankDAO.insert(acquiringBank);
    }

    @Override
    public boolean update(AcquiringBank acquiringBank) {

        return acquiringBankDAO.update(acquiringBank);
    }

    @Override
    public boolean delete(Long acquiringBankId) {

        return acquiringBankDAO.delete(acquiringBankId);
    }

    @Override
    public Optional<AcquiringBank> findById(Long acquiringBankId) {
        return acquiringBankDAO.findById(acquiringBankId);
    }

    @Override
    public List<AcquiringBank> findAll() {

        return acquiringBankDAO.findAll();
    }

    @Override
    public void createTable() {
        acquiringBankDAO.createTable();
    }

    @Override
    public boolean deleteAll(String s) {
        return acquiringBankDAO.deleteAll(s);
    }

    @Override
    public boolean dropTable(String s) {
        return acquiringBankDAO.dropTable(s);
    }


//    // Метод для очистки всех записей из таблицы acquiring_bank
//    public boolean clearAcquiringBank() {
//        try {
//            return acquiringBankDAO.deleteAll("processingcenterschema.acquiring_bank");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Метод для удаления таблицы acquiring_bank
//    public boolean removeAcquiringBankTable() {
//        try {
//            return acquiringBankDAO.dropTable("processingcenterschema.acquiring_bank");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

}
