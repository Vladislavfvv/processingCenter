package com.java.service;

import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.model.SalesPoint;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class SalesPointService  {
    private final DAOInterface<Long, SalesPoint> salesPointDAO;
    private final Connection connection;
    public SalesPointService(Connection connection) {
        // Получаем DAO через фабрику
        this.connection = connection;
        this.salesPointDAO = DAOFactory.getSalesPointDAO(connection);
    }

    public SalesPoint create(SalesPoint salesPoint) {
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

    @Override
    public boolean clearSalesPoints() {
        return false;
    }

    // Метод для очистки всех записей из таблицы sales_point
    public boolean deleteAll() {
        try {
            return salesPointDAO.deleteAll("processingcenterschema.sales_point");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Метод для удаления таблицы sales_point
    public boolean removeSalesPointsTable() {
        try {
            return salesPointDAO.dropTable("processingcenterschema.sales_point");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
