package com.java.dao.jdbc;

import com.java.dao.DAOInterface;
import com.java.model.Card;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class CardJDBCDaoImpl implements DAOInterface<Card> {
    private final Connection connection;

    public CardJDBCDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Card insert(Card value) {
        return null;
    }

    @Override
    public boolean update(Card value) {
        return false;
    }

    @Override
    public boolean delete(Long key) {
        return false;
    }

    @Override
    public Optional<Card> findById(Long key) {
        return null;
    }


    @Override
    public List<Card> getAll() {
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
