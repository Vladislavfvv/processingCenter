package com.java.dao.jdbc;

import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.exception.DaoException;
import com.java.model.AcquiringBank;
import com.java.model.SalesPoint;
import com.java.service.AcquiringBankService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class SalesPointJDBCImpl implements DAOInterface<Long, SalesPoint> {
    private static final Logger logger = Logger.getLogger(AcquiringBankJDBCDaoImpl.class.getClassLoader().getClass().getName());
    private final Connection connection;

    private static final String FIND_BY_ID = "SELECT id, pos_name, pos_address, pos_inn, acquiring_bank_id, bic, abbreviatedName FROM salespoint";

    private final AcquiringBankService acquiringBankService = DAOFactory.getAcquiringBankDAO(connection);

    public SalesPointJDBCImpl(Connection connection) {
        this.connection = connection;
    }
    //AcquiringBank
    private SalesPoint buildSalesPoint(ResultSet resultSet) throws SQLException {
        AcquiringBank acquiringBank = new AcquiringBank(
                resultSet.getLong("id"),
                resultSet.getString("bic"),
                resultSet.getString("abbreviatedName"));


        SalesPoint salesPoint = new SalesPoint(
                resultSet.getLong("id"),
                resultSet.getString("pos_name"),
                resultSet.getString("pos_address"),
                resultSet.getString("pos_inn"),
                //acquiringBank
                acquiringBank
        );
        return salesPoint;
    }

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
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
        preparedStatement.setLong(1, key);
        try(ResultSet resultSet = preparedStatement.executeQuery()) {}
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<SalesPoint> findById(Long key, Connection connection) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, key);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {}
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<SalesPoint> findAll() {
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
