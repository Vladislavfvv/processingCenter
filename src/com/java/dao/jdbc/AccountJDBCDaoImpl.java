package com.java.dao.jdbc;

import com.java.dao.DAOInterface;
import com.java.model.Account;

import java.util.List;
import java.util.Optional;

public class AccountJDBCDaoImpl implements DAOInterface<Long, Account> {
    @Override
    public Account insert(Account value) {
        return null;
    }

    @Override
    public boolean update(Account value) {
        return false;
    }

    @Override
    public boolean delete(Long key) {
        return false;
    }

    @Override
    public Optional<Account> findById(Long key) {
        return null;
    }


    @Override
    public List<Account> findAll() {
        return List.of();
    }

    @Override
    public void createTable() {
    }

    @Override
    public boolean dropTable() {
        return false;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }
}
