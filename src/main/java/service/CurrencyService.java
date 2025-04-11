package service;

import dao.DAOFactory;
import dao.DAOInterface;

import java.sql.Connection;
import model.Currency;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class CurrencyService implements ServiceInterface<Currency, Long> {
    Logger logger = Logger.getLogger(CurrencyService.class.getName());
    private final Connection connection;
    private final DAOInterface<Long, Currency> currencyDAO;

    public CurrencyService(Connection connection) {
        this.currencyDAO = DAOFactory.getCurrencyDAO(connection);
        this.connection = connection;
    }

    @Override
    public Currency create(Currency currency) {
        return this.currencyDAO.insert(currency);
    }

    @Override
    public boolean update(Currency currency) {
        return currencyDAO.update(currency);
    }

    @Override
    public boolean delete(Long id) {
        return currencyDAO.delete(id);
    }

    @Override
    public Optional<Currency> findById(Long id) {
        return currencyDAO.findById(id);
    }

    @Override
    public List<Currency> findAll() {
        return currencyDAO.findAll();
    }

    @Override
    public void createTable(String sql) {
        currencyDAO.createTableQuery(sql);
    }

    @Override
    public boolean deleteAll(String s) {
        return currencyDAO.deleteAll(s);
    }

    @Override
    public boolean dropTable(String s) {
        return currencyDAO.dropTable(s);
    }

//    public Optional<Currency> findByCode(String code) {
//        return currencyDAO.findAll().stream()
//                .filter(c -> code.equalsIgnoreCase(c.getCode()))
//                .findFirst();
//    }


}
