package service;

import dao.DAOFactory;
import dao.DAOInterface;
import model.SalesPoint;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class SalesPointService implements ServiceInterface<SalesPoint, Long> {
    private final DAOInterface<Long, SalesPoint> salesPointDAO;
    private final Connection connection;

    public SalesPointService(Connection connection) {
        // Получаем DAO через фабрику
        this.connection = connection;
        this.salesPointDAO = DAOFactory.getSalesPointDAO(connection);
    }

    @Override
    public SalesPoint create(SalesPoint salesPoint) {

        return salesPointDAO.insert(salesPoint);
    }

    @Override
    public boolean update(SalesPoint salesPoint) {
        return salesPointDAO.update(salesPoint);
    }

    @Override
    public boolean delete(Long salesPointId) {
        return salesPointDAO.delete(salesPointId);
    }

    @Override
    public Optional<SalesPoint> findById(Long salesPointId) {
        return salesPointDAO.findById(salesPointId);
    }

    @Override
    public List<SalesPoint> findAll() {
        return salesPointDAO.findAll();
    }

    @Override
    public void createTable(String sql) {
        salesPointDAO.createTableQuery(sql);
    }

    @Override
    public boolean deleteAll(String s) {
        return salesPointDAO.deleteAll(s);
    }

    @Override
    public boolean dropTable(String s) {
        return salesPointDAO.dropTable(s);
    }
}
