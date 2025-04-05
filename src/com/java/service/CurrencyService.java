package com.java.service;

import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;

import java.sql.Connection;
import com.java.model.Currency;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class CurrencyService {
    Logger logger = Logger.getLogger(CurrencyService.class.getName());
    private final DAOInterface<Long, Currency> currencyDAO;
    private final Connection connection;

    public CurrencyService(Connection connection) {
        this.currencyDAO = DAOFactory.getCurrencyDAO(connection);
        this.connection = connection;
    }


    public Currency create (Currency currency) { return this.currencyDAO.insert(currency); }
    public boolean update (Currency currency) { return currencyDAO.update(currency);}
    public boolean delete (Long id) { return currencyDAO.delete(id);}
    public Optional<Currency> findById(Long id) { return currencyDAO.findById(id);}
    public List<Currency> findAll() { return currencyDAO.findAll();}
    public boolean deleteAll (String s) {return deleteAll(s);}
    public boolean dropTable (String s) {return dropTable(s);}

}
