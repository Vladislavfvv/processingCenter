package com.java.dao.jdbc;

import com.java.dao.DAOAbstract;
import com.java.dao.DAOInterface;
import com.java.exception.DaoException;
import com.java.model.PaymentSystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class PaymentSystemJDBCDaoImpl extends DAOAbstract implements DAOInterface<Long, PaymentSystem> {

    private static final Logger logger = Logger.getLogger(CardStatusJDBCDaoImpl.class.getName());

    private static final String CREATE_TABLE_PAYMENT_SYSTEM = "CREATE TABLE IF NOT EXISTS processingCenterSchema.payment_system\n" +
            "(\n" +
            "    id                  bigserial primary key,\n" +
            "    payment_system_name varchar(50) UNIQUE not null\n" +
            "    );";
    private static final String CREATE_CARD_STATUS = "INSERT INTO payment_system VALUES (?, ?)";
    private static final String DELETE_CARD_STATUS = "DELETE FROM payment_system WHERE id = ?";
    private static final String GET_ALL_CARD_STATUS = "SELECT id, payment_system_name FROM payment_system";
    private static final String UPDATE_CARD_STATUS = "UPDATE payment_system SET payment_system_name = ? WHERE id = ?";
    private static final String GET_CARD_STATUS_BY_ID = "SELECT id, payment_system_name FROM payment_system WHERE id = ?";
    private static final String DELETE_CARD_STATUS_BY_ID = "DELETE FROM payment_system WHERE id = ?";
    private static final String INSERT_SQL = "INSERT INTO payment_system VALUES (?, ?)";

    public PaymentSystemJDBCDaoImpl(Connection connection) {
        super(connection);
    }

    private PaymentSystem buildPaymentSystem(ResultSet resultSet) throws SQLException {
        return new PaymentSystem(resultSet.getLong("id"),
                resultSet.getString("payment_system_name"));
    }


    @Override
    public PaymentSystem insert(PaymentSystem paymentSystem) {
        try {
            if (!DAOAbstract.isTableExists(connection, "payment_system")) {
                logger.warning("Таблица payment_system не существует. Создаю...");
                createTableQuery(CREATE_TABLE_PAYMENT_SYSTEM);
            }
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);////второй параметр для получения идентификатора созданной сущности
            preparedStatement.setString(1, paymentSystem.getPaymentSystemName());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                paymentSystem.setPaymentSystemName(resultSet.getString(1));
            }
            logger.info("paymentSystem with ID: " + paymentSystem.getId() + " was added.");
            return paymentSystem;
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(PaymentSystem paymentSystem) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CARD_STATUS)) {
            preparedStatement.setString(1, paymentSystem.getPaymentSystemName());
            preparedStatement.setLong(2, paymentSystem.getId());
            logger.info("paymentSystem with ID: " + paymentSystem.getId() + " was updated.");
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Ошибка при обновлении paymentSystem", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CARD_STATUS_BY_ID)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при удалении", e);
        }
    }

    @Override
    public Optional<PaymentSystem> findById(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_CARD_STATUS_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            PaymentSystem paymentSystem = null;
            if (resultSet.next()) {
                paymentSystem = new PaymentSystem(resultSet.getLong("id"),
                        resultSet.getString("payment_system_name"));

                logger.info("paymentSystem with ID: " + paymentSystem.getId() + " was found.");
                return Optional.ofNullable(paymentSystem);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при поиске paymentSystem", e);
        }
    }

    @Override
    public List<PaymentSystem> findAll() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CARD_STATUS);) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PaymentSystem> paymentSystemList = new ArrayList<>();

            while (resultSet.next()) {
                paymentSystemList.add(buildPaymentSystem(resultSet));
            }
            return paymentSystemList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

//    @Override
//    public void createTable() {
//        try {
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(CREATE_TABLE_SQL);
//            logger.info("Table created");
//        } catch (SQLException e) {
//            logger.severe(e.getMessage());
//            throw new DaoException(e);
//        }
//    }

    @Override
    public boolean createTableQuery(String sql) {
        return createTableService(CREATE_TABLE_PAYMENT_SYSTEM);
    }


    @Override
    public boolean deleteAll(String s) {
        return deleteAllService("processingcenterschema.payment_system");
    }

    @Override
    public boolean dropTable(String s) {
        return dropTableService("processingcenterschema.payment_system");
    }
}
