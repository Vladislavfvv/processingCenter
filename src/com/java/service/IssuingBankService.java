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
        return null;
    }

    @Override
    public boolean update(IssuingBank value) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Optional<IssuingBank> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<IssuingBank> findAll() {
        return List.of();
    }

    @Override
    public void createTable() {

    }

    @Override
    public boolean deleteAll(String s) {
        return false;
    }

    @Override
    public boolean dropTable(String s) {
        return false;
    }
}
