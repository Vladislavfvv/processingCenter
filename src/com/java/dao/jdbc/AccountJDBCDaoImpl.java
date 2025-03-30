package com.java.dao.jdbc;

import com.java.dao.DAOInterface;
import com.java.model.Account;

import java.util.List;
import java.util.Optional;

public class AccountJDBCDaoImpl implements DAOInterface<Account> {
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
    public List<Account> getAll() {
        return List.of();
    }

    @Override
    public boolean createTable() {
        return false;
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
