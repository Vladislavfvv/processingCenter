package com.java.service;

import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.model.Account;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class AccountService  implements ServiceInterface<Account, Long>{

    private final DAOInterface<Long, Account> accountDao;
    private final Connection connection;

    public AccountService(Connection connection) {
        this.accountDao = DAOFactory.getAccountDAO(connection);
        this.connection = connection;
    }


    @Override
    public Account create(Account value) {
        return accountDao.insert(value);
    }

    @Override
    public boolean update(Account value) {
        return accountDao.update(value);
    }

    @Override
    public boolean delete(Long id) {
        return accountDao.delete(id);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountDao.findById(id);
    }

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public void createTable() {
        accountDao.createTable();
    }

    @Override
    public boolean deleteAll(String s) {
        return accountDao.deleteAll(s);
    }

    @Override
    public boolean dropTable(String s) {
        return accountDao.dropTable(s);
    }
}
