package com.java.service;

import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.model.TransactionType;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class TransactionTypeService implements ServiceInterface<TransactionType, Long>{
private static final Logger logger = Logger.getLogger(TransactionTypeService.class.getName());
private final DAOInterface<Long, TransactionType> transactionTypeDAO;
private final Connection connection;


    public TransactionTypeService(Connection connection) {
        this.transactionTypeDAO = DAOFactory.getTransactionTypeDAO(connection);
        this.connection = connection;
    }


    @Override
    public TransactionType create(TransactionType value) {
        return transactionTypeDAO.insert(value);
    }

    @Override
    public boolean update(TransactionType value) {
        return transactionTypeDAO.update(value);
    }

    @Override
    public boolean delete(Long id) {
        return transactionTypeDAO.delete(id);
    }

    @Override
    public Optional<TransactionType> findById(Long id) {
        return transactionTypeDAO.findById(id);
    }

    @Override
    public List<TransactionType> findAll() {
        return transactionTypeDAO.findAll();
    }

    @Override
    public void createTable(String sql) {
        transactionTypeDAO.createTableQuery(sql);

    }

    @Override
    public boolean deleteAll(String s) {
        return transactionTypeDAO.deleteAll(s);
    }

    @Override
    public boolean dropTable(String s) {
        return transactionTypeDAO.dropTable(s);
    }
}
