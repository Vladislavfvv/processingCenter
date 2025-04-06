package com.java.service;

import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.model.Transaction;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class TransactionService implements ServiceInterface<Transaction, Long> {

    private final DAOInterface<Long, Transaction> transactionDAO;

    private final Connection connection;

    public TransactionService(Connection connection) {
        this.transactionDAO = DAOFactory.getTransactionDAO(connection);
        this.connection = connection;
    }

    @Override
    public Transaction create(Transaction value) {
        return transactionDAO.insert(value);
    }

    @Override
    public boolean update(Transaction value) {
        return transactionDAO.update(value);
    }

    @Override
    public boolean delete(Long id) {
        return transactionDAO.delete(id);
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return transactionDAO.findById(id);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionDAO.findAll();
    }

    @Override
    public void createTable(String sql) {
        transactionDAO.createTableQuery(sql);
    }

    @Override
    public boolean deleteAll(String s) {
        return transactionDAO.deleteAll(s);
    }

    @Override
    public boolean dropTable(String s) {
        return transactionDAO.dropTable(s);
    }
}
