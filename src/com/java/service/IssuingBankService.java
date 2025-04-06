package com.java.service;

import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.model.IssuingBank;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class IssuingBankService implements ServiceInterface<IssuingBank, Long> {
    private final DAOInterface<Long, IssuingBank> issuingBankDAO;
    private final Connection connection;

    public IssuingBankService(Connection connection) {
        this.connection = connection;
        this.issuingBankDAO = DAOFactory.getIssuingBankDAO(connection);
    }

    @Override
    public IssuingBank create(IssuingBank value) {
        return issuingBankDAO.insert(value);
    }

    @Override
    public boolean update(IssuingBank value) {
        return issuingBankDAO.update(value);
    }

    @Override
    public boolean delete(Long id) {
        return issuingBankDAO.delete(id);
    }

    @Override
    public Optional<IssuingBank> findById(Long id) {
        return issuingBankDAO.findById(id);
    }

    @Override
    public List<IssuingBank> findAll() {
        return issuingBankDAO.findAll();
    }

    @Override
    public void createTable(String sql) {
        issuingBankDAO.createTableQuery(sql);
    }

    @Override
    public boolean deleteAll(String s) {
        return issuingBankDAO.deleteAll(s);
    }

    @Override
    public boolean dropTable(String s) {
        return issuingBankDAO.dropTable(s);
    }
}
