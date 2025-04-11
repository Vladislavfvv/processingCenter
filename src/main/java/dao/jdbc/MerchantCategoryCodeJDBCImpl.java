package dao.jdbc;

import dao.DAOAbstract;
import dao.DAOInterface;
import exception.DaoException;
import model.MerchantCategoryCode;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class MerchantCategoryCodeJDBCImpl extends DAOAbstract implements DAOInterface<Long, MerchantCategoryCode> {
    private static final Logger logger = Logger.getLogger(MerchantCategoryCodeJDBCImpl.class.getName());

    private static final String CREATE_TABLE_MERCHANT = "CREATE TABLE IF NOT EXISTS processingCenterSchema.merchant_category_code\n" +
            "(\n" +
            "    id       bigserial primary key,\n" +
            "    mcc      varchar(4) not null,\n" +
            "    mcc_name varchar(255) not null\n" +
            "    );";
    private static final String DELETE_TABLE = "DELETE FROM merchant_category_code WHERE id = ?";
    private static final String GET_ALL = "SELECT id, mcc, mcc_name FROM merchant_category_code";
    private static final String UPDATE = "UPDATE merchant_category_code SET mcc = ?, mcc_name = ? WHERE id = ?";
    private static final String GET_BY_ID = "SELECT id, mcc, mcc_name FROM merchant_category_code WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM merchant_category_code WHERE id = ?";
    private static final String INSERT = "INSERT INTO merchant_category_code (mcc, mcc_name) VALUES (?, ?)";

    private MerchantCategoryCode buildMerchantCategoryCode(ResultSet resultSet) throws SQLException {
        return new MerchantCategoryCode(resultSet.getLong("id"),
                resultSet.getString("mcc"),
                resultSet.getString("mcc_name")
        );
    }

    public MerchantCategoryCodeJDBCImpl(Connection connection) {
        super(connection);
    }

    @Override
    public MerchantCategoryCode insert(MerchantCategoryCode value) {
        try {
            if (!DAOAbstract.isTableExists(connection, "merchant_category_code")) {
                logger.warning("Таблица merchant_category_code не существует. Создаю...");
                createTableQuery(CREATE_TABLE_MERCHANT);
            }
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);////второй параметр для получения идентификатора созданной сущности
            preparedStatement.setString(1, value.getMcc());
            preparedStatement.setString(2, value.getMcc_name());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                value.setMcc(resultSet.getString(1));
                value.setMcc_name(resultSet.getString(2));
            }
            logger.info("MerchantCategoryCode with id: " + value.getId() + " was added.");
            return value;
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(MerchantCategoryCode value) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, value.getMcc());
            preparedStatement.setString(2, value.getMcc_name());
            preparedStatement.setLong(3, value.getId());
            logger.info("MerchantCategoryCode with ID: " + value.getId() + " was updated.");
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Ошибка при обновлении MerchantCategoryCode", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при удалении", e);
        }
    }

    @Override
    public Optional<MerchantCategoryCode> findById(Long id) {
        try (Connection connection = ConnectionManager.open()) {
            return findById(id, connection);
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при поиске merchantCategoryCode ", e);
        }
    }

    public Optional<MerchantCategoryCode> findById(long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            MerchantCategoryCode merchantCategoryCode = null;
            if (resultSet.next()) {
                merchantCategoryCode = buildMerchantCategoryCode(resultSet);

                logger.info("MerchantCategoryCode with ID: " + merchantCategoryCode.getId() + " was found.");
                return Optional.of(merchantCategoryCode);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при поиске merchantCategoryCode ", e);
        }
    }



    @Override
    public List<MerchantCategoryCode> findAll() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL);) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<MerchantCategoryCode> merchantCategoryCodeList = new ArrayList<>();

            while (resultSet.next()) {
                merchantCategoryCodeList.add(buildMerchantCategoryCode(resultSet));
            }
            return merchantCategoryCodeList;
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
        return createTableService(CREATE_TABLE_MERCHANT);
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
    public Optional<MerchantCategoryCode> findByValue(String cardNumber) {
        return Optional.empty();
    }


}

