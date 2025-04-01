package com.java.service;

import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.model.SalesPoint;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class SalesPointService {
    private final DAOInterface<Long, SalesPoint> salesPointDAO;
    private final Connection connection;
    public SalesPointService(Connection connection) {
        // Получаем DAO через фабрику
        this.connection = connection;
        this.salesPointDAO = DAOFactory.getSalesPointDAO(connection);
    }

    public SalesPoint createSalesPoint(SalesPoint salesPoint) {
        return salesPointDAO.insert(salesPoint);
    }

    public boolean update(SalesPoint salesPoint) {
        return salesPointDAO.update(salesPoint);
    }

    public boolean delete(Long salesPointId) {
        return salesPointDAO.delete(salesPointId);
    }

    public Optional<SalesPoint> findById(Long salesPointId) {
        return salesPointDAO.findById(salesPointId);
    }

    public List<SalesPoint> findAll() {
        return salesPointDAO.findAll();
    }


}
/////////