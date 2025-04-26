package dao.jdbc;

import dao.DAOAbstract;
import dao.DAOInterface;
import exception.DaoException;
import model.ResponseCode;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ResponseCodeJDBCImpl extends DAOAbstract implements DAOInterface<Long, ResponseCode> {
    private static final Logger logger = Logger.getLogger(ResponseCodeJDBCImpl.class.getName());

    public ResponseCodeJDBCImpl(Connection connection) {
        super(connection);
    }

    private static final String CREATE_TABLE_RESPONSE_CODE = "CREATE TABLE IF NOT EXISTS processingCenterSchema.response_code\n" +
            "(\n" +
            "    id                bigserial primary key,\n" +
            "    error_code        varchar(2),\n" +
            "    error_description varchar(255),\n" +
            "    error_level       varchar(255)\n" +
            "    );";
    private static final String DELETE_RESPONSE_CODE_TABLE = "DELETE FROM response_code WHERE id = ?";
    private static final String GET_ALL_RESPONSE_CODES = "SELECT id, error_code, error_description, error_level FROM response_code";
    private static final String UPDATE_RESPONSE_CODE = "UPDATE response_code SET error_code = ?, error_description = ?, error_level = ? WHERE id = ?";
    private static final String GET_RESPONSE_CODE_BY_ID = "SELECT id,  error_code, error_description, error_level FROM response_code WHERE id = ?";
    private static final String DELETE_RESPONSE_CODE_BY_ID = "DELETE FROM response_code WHERE id = ?";
    private static final String INSERT_RESPONSE_CODE = "INSERT INTO response_code VALUES (?, ?)";

    private ResponseCode buildResponseCode(ResultSet resultSet) throws SQLException {
        return new ResponseCode(resultSet.getLong("id"),
                resultSet.getString("error_code"),
                resultSet.getString("error_description"),
                resultSet.getString("error_level"));
    }


    @Override
    public ResponseCode insert(ResponseCode responseCode) {
        try {
            if (!DAOAbstract.isTableExists(connection, "response_code")) {
                logger.warning("Таблица response_code не существует. Создаю...");
                createTableQuery(CREATE_TABLE_RESPONSE_CODE);
            }
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RESPONSE_CODE, Statement.RETURN_GENERATED_KEYS);////второй параметр для получения идентификатора созданной сущности
            preparedStatement.setString(1, responseCode.getErrorCode());
            preparedStatement.setString(2, responseCode.getErrorDescription());
            preparedStatement.setString(3, responseCode.getErrorLevel());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                responseCode.setErrorCode(resultSet.getString(1));
                responseCode.setErrorDescription(resultSet.getString(2));
                responseCode.setErrorLevel(resultSet.getString(3));
            }
            logger.info("responseCode with responseCodeName: " + responseCode.getId() + " was added.");
            return responseCode;
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(ResponseCode responseCode) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RESPONSE_CODE)) {
            preparedStatement.setString(1, responseCode.getErrorCode());
            preparedStatement.setString(1, responseCode.getErrorDescription());
            preparedStatement.setString(1, responseCode.getErrorLevel());
            preparedStatement.setLong(2, responseCode.getId());
            logger.info("cardStatus with cardStatusName: " + responseCode.getId() + " was updated.");
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Ошибка при обновлении cardStatus", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESPONSE_CODE_BY_ID)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при удалении", e);
        }
    }

    @Override
    public Optional<ResponseCode> findById(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_RESPONSE_CODE_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResponseCode responseCode = null;
            if (resultSet.next()) {
                responseCode = new ResponseCode(resultSet.getLong("id"),
                        resultSet.getString("error_code"),
                        resultSet.getString("error_description"),
                        resultSet.getString("error_level"));

                logger.info("responseCode with ID: " + responseCode.getId() + " was found.");
                return Optional.of(responseCode);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при поиске responseCode ", e);
        }
    }

    @Override
    public List<ResponseCode> findAll() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_RESPONSE_CODES);) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ResponseCode> responseCodeList = new ArrayList<>();

            while (resultSet.next()) {
                responseCodeList.add(buildResponseCode(resultSet));
            }
            return responseCodeList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }



    @Override
    public boolean createTableQuery(String sql) {
        return createTableService(CREATE_TABLE_RESPONSE_CODE);
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
    public Optional<ResponseCode> findByValue(String cardNumber) {
        return Optional.empty();
    }
}
