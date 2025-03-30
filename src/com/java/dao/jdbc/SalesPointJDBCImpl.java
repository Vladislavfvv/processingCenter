package com.java.dao.jdbc;

import com.java.dao.DAOInterface;
import com.java.model.SalesPoint;

import java.util.List;
import java.util.Optional;

public class SalesPointJDBCImpl implements DAOInterface<Long, SalesPoint> {
    @Override
    public SalesPoint insert(SalesPoint value) {
        return null;
    }

    @Override
    public boolean update(SalesPoint value) {
        return false;
    }

    @Override
    public boolean delete(Long key) {
        return false;
    }

    @Override
    public Optional<SalesPoint> findById(Long key) {
        return Optional.empty();
    }

    @Override
    public List<SalesPoint> getAll() {
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
