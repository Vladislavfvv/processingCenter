package service;

import dao.DAOFactory;
import dao.DAOInterface;
import model.Account;
import model.Currency;
import model.IssuingBank;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class AccountService  implements ServiceInterface<Account, Long>{

    private final DAOInterface<Long, Account> accountDao;
    private final DAOInterface<Long, Currency> currencyDao;
    private final DAOInterface<Long, IssuingBank> issuingBankDao;
    private final Connection connection;

    public AccountService(Connection connection) {
        this.connection = connection;
        this.accountDao = DAOFactory.getAccountDAO(connection);
        this.currencyDao = DAOFactory.getCurrencyDAO(connection);
        this.issuingBankDao = DAOFactory.getIssuingBankDAO(connection);
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
    public void createTable(String sql) {
        accountDao.createTableQuery(sql);
    }

    @Override
    public boolean deleteAll(String s) {
        return accountDao.deleteAll(s);
    }

    @Override
    public boolean dropTable(String tableName) {
        return accountDao.dropTable(tableName);
    }



    public Currency getCurrencyByLetterCode(String code) {
        return currencyDao.findAll()
                .stream()
                .filter(c -> c.getCurrencyLetterCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);
    }

    public IssuingBank getIssuingBankByName(String name) {
        return issuingBankDao.findAll()
                .stream()
                .filter(b -> b.getAbbreviatedName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
