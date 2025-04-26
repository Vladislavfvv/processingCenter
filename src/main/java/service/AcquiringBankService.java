package service;

import dao.DAOFactory;
import dao.DAOInterface;
import model.AcquiringBank;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class AcquiringBankService implements ServiceInterface<AcquiringBank, Long> {

    private final DAOInterface<Long, AcquiringBank> acquiringBankDAO;
    private final Connection connection;

    public AcquiringBankService(Connection connection) {
        this.connection = connection;
        // Получаем DAO через фабрику
        this.acquiringBankDAO = DAOFactory.getAcquiringBankDAO(connection);
    }

    @Override
    public AcquiringBank create(AcquiringBank acquiringBank) {
        return acquiringBankDAO.insert(acquiringBank);
    }

    @Override
    public boolean update(AcquiringBank acquiringBank) {

        return acquiringBankDAO.update(acquiringBank);
    }

    @Override
    public boolean delete(Long acquiringBankId) {

        return acquiringBankDAO.delete(acquiringBankId);
    }

    @Override
    public Optional<AcquiringBank> findById(Long acquiringBankId) {
        return acquiringBankDAO.findById(acquiringBankId);
    }

    @Override
    public List<AcquiringBank> findAll() {

        return acquiringBankDAO.findAll();
    }

    @Override
    public void createTable(String sql) {
        acquiringBankDAO.createTableQuery(sql);
    }

    @Override
    public boolean deleteAll(String s) {
        return acquiringBankDAO.deleteAll(s);
    }

    @Override
    public boolean dropTable(String s) {
        return acquiringBankDAO.dropTable(s);
    }

}
