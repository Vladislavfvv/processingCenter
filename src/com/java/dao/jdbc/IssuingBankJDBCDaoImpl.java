package com.java.dao.jdbc;

import com.java.dao.DAOAbstract;
import com.java.dao.DAOInterface;
import com.java.exception.DaoException;
import com.java.model.Currency;
import com.java.model.IssuingBank;
import com.java.util.ConnectionManager2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class IssuingBankJDBCDaoImpl extends DAOAbstract implements DAOInterface<Long, IssuingBank> {
    private static final Logger logger = Logger.getLogger(IssuingBankJDBCDaoImpl.class.getName());

    private static final String CREATE_TABLE_ISSUING_BANK = "CREATE TABLE IF NOT EXISTS processingCenterSchema.issuing_bank\n" +
            "(\n" +
            "    id               bigserial primary key,\n" +
            "    bic              varchar(9)   not null,\n" +
            "    bin              varchar(5)   not null,\n" +
            "    abbreviated_name varchar(255) not null\n" +
            "    );";
    private static final String DELETE_ISSUING_BANK_TABLE = "DELETE FROM issuing_bank WHERE id = ?";
    private static final String GET_ALL_ISSUING_BANKS = "SELECT id, bic, bin, abbreviated_name FROM issuing_bank";
    private static final String UPDATE_ISSUING_BANK = "UPDATE issuing_bank SET bic = ?, bin = ?, abbreviated_name = ? WHERE id = ?";
    private static final String GET_ISSUING_BANK_BY_ID = "SELECT id, bic, bin, abbreviated_name FROM issuing_bank WHERE id = ?";
    private static final String DELETE_ISSUING_BANK_BY_ID = "DELETE FROM issuing_bank WHERE id = ?";
    private static final String INSERT_ISSUING_BANK = "INSERT INTO issuing_bank VALUES (?, ?, ?)";


    public IssuingBankJDBCDaoImpl(Connection connection) {
        super(connection);
    }

    private IssuingBank buildIssuingBank(ResultSet resultSet) throws SQLException {
        return new IssuingBank(
                resultSet.getLong("id"),
                resultSet.getString("bic"),
                resultSet.getString("bin"),
                resultSet.getString("abbreviated_name")
        );
    }

    @Override
    public IssuingBank insert(IssuingBank value) {
        try {
            if (!DAOAbstract.isTableExists(connection, "issuing_bank")) {
                logger.warning("Таблица issuing_bank не существует. Создаю...");
                createTableQuery(CREATE_TABLE_ISSUING_BANK);
            }
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ISSUING_BANK, Statement.RETURN_GENERATED_KEYS);////второй параметр для получения идентификатора созданной сущности
            preparedStatement.setString(1, value.getBic());
            preparedStatement.setString(2, value.getBin());
            preparedStatement.setString(3, value.getAbbreviatedName());
            preparedStatement.executeUpdate();

//            ResultSet resultSet = preparedStatement.getGeneratedKeys();
//            if (resultSet.next()) {
//                value.setBic(resultSet.getString(1));
//                value.setBin(resultSet.getString(2));
//                value.setAbbreviatedName(resultSet.getString(3));
//            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                value.setId(resultSet.getLong(1)); // Получаем сгенерированный id
                logger.info("IssuingBank with id: " + value.getId() + " was added.");
            }
            return value;
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(IssuingBank value) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ISSUING_BANK)) {
            preparedStatement.setString(1, value.getBic());
            preparedStatement.setString(2, value.getBin());
            preparedStatement.setString(3, value.getAbbreviatedName());
            preparedStatement.setLong(4, value.getId());
            logger.info("IssuingBank with id: " + value.getId() + " was updated.");
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Ошибка при обновлении IssuingBank", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ISSUING_BANK_BY_ID)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при удалении IssuingBank", e);
        }
    }

    @Override
    public Optional<IssuingBank> findById(Long id) {
            try (Connection connection = ConnectionManager2.open()) {
                return findById(id, connection);
            } catch (SQLException e) {
                logger.severe(e.getMessage());
                throw new DaoException("Ошибка при поиске issuingBank ", e);
            }
    }


    public Optional<IssuingBank> findById(long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ISSUING_BANK_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            IssuingBank issuingBank = null;
            if (resultSet.next()) {
                issuingBank = buildIssuingBank(resultSet);

                logger.info("issuingBank with ID: " + issuingBank.getId() + " was found.");
                return Optional.of(issuingBank);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при поиске issuingBank ", e);
        }
    }



    @Override
    public List<IssuingBank> findAll() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ISSUING_BANKS);) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<IssuingBank> issuingBankList = new ArrayList<>();

            while (resultSet.next()) {
                issuingBankList.add(buildIssuingBank(resultSet));
            }
            return issuingBankList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

//    @Override
//    public void createTable() {
//        try {
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(CREATE_TABLE_ISSUING_BANK);
//            logger.info("Table created");
//        } catch (SQLException e) {
//            logger.severe(e.getMessage());
//            throw new DaoException(e);
//        }
//    }

    @Override
    public boolean createTableQuery(String sql) {
        return createTableService(CREATE_TABLE_ISSUING_BANK);
    }



    @Override
    public boolean deleteAll(String s) {
        return deleteAllService("processingcenterschema.issuing_bank");
    }

    @Override
    public boolean dropTable(String s) {
        return dropTableService("processingcenterschema.issuing_bank");
    }


}
