package com.java.dao.jdbc;

import com.java.dao.DAOAbstract;
import com.java.dao.DAOInterface;
import com.java.exception.DaoException;
import com.java.model.Currency;
import com.java.model.TransactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class TransactionTypeJDBCDaoImpl extends DAOAbstract implements DAOInterface<Long, TransactionType> {
    private static final Logger logger = Logger.getLogger(TransactionTypeJDBCDaoImpl.class.getName());

    private static final String CREATE_TABLE_TRANSACTION_TYPE
            = "CREATE TABLE IF NOT EXISTS processingCenterSchema.transaction_type\n" +
            "(\n" +
            "    id                    bigserial primary key,\n" +
            "    transaction_type_name varchar(255),\n" +
            "    operator              char\n" +
            "    );";
    private static final String DELETE_TABLE_TRANSACTION_TYPE = "DELETE FROM transaction_type WHERE id = ?";
    private static final String GET_ALL_TRANSACTION_TYPES = "SELECT id, transaction_type_name, operator FROM transaction_type";
    private static final String UPDATE_TRANSACTION_TYPE = "UPDATE transaction_type SET transaction_type_name = ?, operator = ? WHERE id = ?";
    private static final String GET_TRANSACTION_TYPE_BY_ID = "SELECT id, transaction_type_name, operator FROM transaction_type WHERE id = ?";
    private static final String DELETE_TRANSACTION_TYPE_BY_ID = "DELETE FROM transaction_type WHERE id = ?";
    private static final String INSERT_TRANSACTION_TYPE = "INSERT INTO transaction_type VALUES (?, ?)";

    public TransactionTypeJDBCDaoImpl(Connection connection) {
        super(connection);

    }

    private TransactionType buildTransactionType(ResultSet resultSet) throws SQLException {
        return new TransactionType(
                resultSet.getLong("id"),
                resultSet.getString("transaction_type_name"),
                resultSet.getString("operator")
        );
    }

    @Override
    public TransactionType insert(TransactionType value) {
        try {
            if (!DAOAbstract.isTableExists(connection, "transaction_type")) {
                logger.warning("Таблица transaction_type не существует. Создаю...");
                createTableQuery(CREATE_TABLE_TRANSACTION_TYPE);
            }
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TRANSACTION_TYPE, Statement.RETURN_GENERATED_KEYS);////второй параметр для получения идентификатора созданной сущности
            preparedStatement.setString(1, value.getTransactionTypeName());
            preparedStatement.setString(2, value.getOperator());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                value.setTransactionTypeName(resultSet.getString(1));
                value.setOperator(resultSet.getString(2));
            }
            logger.info("TransactionType with TransactionTypeName: " + value.getTransactionTypeName() + " was added.");
            return value;
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(TransactionType value) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TRANSACTION_TYPE)) {
            preparedStatement.setString(1, value.getTransactionTypeName());
            preparedStatement.setString(2, value.getOperator());
            preparedStatement.setLong(3, value.getId());
            logger.info("TransactionType with CurrencyName: " + value.getId() + " was updated.");
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Ошибка при обновлении TransactionType", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TRANSACTION_TYPE_BY_ID)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при удалении", e);
        }
    }

    @Override
    public Optional<TransactionType> findById(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_TRANSACTION_TYPE_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            TransactionType transactionType = null;
            if (resultSet.next()) {
                transactionType = buildTransactionType(resultSet);

                logger.info("transactionType with ID: " + transactionType.getId() + " was found.");
                return Optional.of(transactionType);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при поиске transactionType ", e);
        }
    }

    @Override
    public List<TransactionType> findAll() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_TRANSACTION_TYPES);) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TransactionType> transactionTypeList = new ArrayList<>();

            while (resultSet.next()) {
                transactionTypeList.add(buildTransactionType(resultSet));
            }
            return transactionTypeList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

//    @Override
//    public void createTable() {
//        try {
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(CREATE_TABLE_TRANSACTION_TYPE);
//            logger.info("Table created");
//        } catch (SQLException e) {
//            logger.severe(e.getMessage());
//            throw new DaoException(e);
//        }
//    }

    @Override
    public boolean createTableQuery(String sql) {
        return createTableService(sql);
    }


    @Override
    public boolean deleteAll(String s) {
        return deleteAllService(s);
    }

    @Override
    public boolean dropTable(String s) {
        return dropTableService(s);
    }

    @Override
    public Optional<TransactionType> findByValue(String cardNumber) {
        return Optional.empty();
    }
}
