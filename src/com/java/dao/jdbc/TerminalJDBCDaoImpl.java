package com.java.dao.jdbc;

import com.java.dao.DAOAbstract;
import com.java.dao.DAOInterface;
import com.java.exception.DaoException;
import com.java.model.MerchantCategoryCode;
import com.java.model.SalesPoint;
import com.java.model.Terminal;
import com.java.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class TerminalJDBCDaoImpl extends DAOAbstract implements DAOInterface<Long, Terminal> {
    private static final Logger logger = Logger.getLogger(ResponseCodeJDBCImpl.class.getName());

    private final MerchantCategoryCodeJDBCImpl merchantCategoryCodeJDBCImpl;
    private final SalesPointJDBCDaoImpl salesPointJDBCDaoImpl;

    public TerminalJDBCDaoImpl(Connection connection) {
        super(connection);
        this.merchantCategoryCodeJDBCImpl = new MerchantCategoryCodeJDBCImpl(connection);
        this.salesPointJDBCDaoImpl = new SalesPointJDBCDaoImpl(connection);
    }

    private static final String CREATE_TABLE_TERMINAL = "CREATE TABLE IF NOT EXISTS processingCenterSchema.terminal\n" +
            "(\n" +
            "    id          bigserial primary key,\n" +
            "    terminal_id varchar(9) not null,\n" +
            "    mcc_id      bigint REFERENCES processingCenterSchema.merchant_category_code (id) ON DELETE CASCADE\n" +
            "                                                              ON UPDATE CASCADE,\n" +
            "    pos_id      bigint REFERENCES processingCenterSchema.sales_point (id) ON DELETE CASCADE\n" +
            "                                                              ON UPDATE CASCADE\n" +
            "    );";
    private static final String DELETE_TERMINAL = "DELETE FROM terminal WHERE id = ?";
    private static final String GET_ALL_TERMINALS = "SELECT id, terminal_id, mcc_id, pos_id FROM terminal";
    private static final String UPDATE_TERMINAL = "UPDATE terminal SET terminal_id = ?, mcc_id = ?, pos_id = ? WHERE id = ?";
    private static final String GET_TERMINAL_BY_ID = "SELECT id, terminal_id, mcc_id, pos_id FROM terminal WHERE id = ?";
    private static final String DELETE_TERMINAL_BY_ID = "DELETE FROM terminal WHERE id = ?";
    private static final String INSERT_IN_TERMINAL = "INSERT INTO terminal(terminal_id, mcc_id, pos_id) VALUES (?, ?)";
    private static final String TERMINAL = "DROP TABLE IF EXISTS terminal";

    private Terminal buildTerminal(ResultSet resultSet) throws SQLException {
        Optional<MerchantCategoryCode> mcc = merchantCategoryCodeJDBCImpl.findById(resultSet.getLong("mcc_id"), connection);
        MerchantCategoryCode merchant = mcc.orElse(null);
        Optional<SalesPoint> pos = salesPointJDBCDaoImpl.findById(resultSet.getLong("pos_id"), connection);
        SalesPoint salesPoint = pos.orElse(null);
        return new Terminal(resultSet.getLong("id"),
                resultSet.getString("terminal_id"),
                merchant,
                salesPoint);
    }


    @Override
    public Terminal insert(Terminal value) {
        try {
            // Проверяем наличие таблицы перед вставкой
//            if (!DAOAbstract.isTableExists(connection, "merchant_category_code")) {
//                logger.warning("Таблица merchant_category_code не существует. Создаю...");
//                createTable();
//            }
//            if (!DAOAbstract.isTableExists(connection, "sales_point")) {
//                logger.warning("Таблица sales_point не существует. Создаю...");
//                createTable();
//            }
            if (!DAOAbstract.isTableExists(connection, "terminal")) {
                logger.warning("Таблица terminal не существует. Создаю...");
                createTableQuery(CREATE_TABLE_TERMINAL);
            }
            // String INSERT_SQL = "INSERT INTO processingCenterSchema.sales_point (pos_name, pos_address, pos_inn, acquiring_bank_id) VALUES (?, ?, ?, ?) RETURNING id";
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_IN_TERMINAL, Statement.RETURN_GENERATED_KEYS);//второй параметр для получения идентификатора созданной сущности
            preparedStatement.setString(1, value.getTerminalId());
            preparedStatement.setString(2, value.getMccId().getId().toString());
            preparedStatement.setString(3, value.getPosId().getId().toString());


            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Ошибка: не удалось создать терминал.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    value.setId(generatedKeys.getLong(1));
                }
            }
            logger.info("Terminal c  id = " + value.getId() + " был добавлен");
            return value;

        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException(e);
        }


    }

    @Override
    public boolean update(Terminal value) {
        if (value == null || value.getId() == null) {
            return false; // Нельзя обновить объект без ID
        }

        logger.info("Обновление SalesPoint с ID = " + value.getId() +
                ", posName = " + value.getTerminalId() +
                ", posAddress = " + value.getMccId() +
                ", posInn = " + value.getPosId());

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_TERMINAL)) {
            statement.setString(1, value.getTerminalId());
            statement.setString(2, value.getMccId().getId().toString());
            statement.setString(3, value.getPosId().getId().toString());
            statement.setLong(5, value.getId()); // WHERE id = ?

            int rowsUpdated = statement.executeUpdate();
            logger.info("Terminal c  id = " + value.getId() + " был обновлен");
            return rowsUpdated > 0; // Возвращаем true, если обновилась хотя бы 1 строка
        } catch (SQLException e) {
            logger.severe("Ошибка при обновлении Terminal: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            connection.setAutoCommit(false); // Отключаем автокоммит

            try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM terminal WHERE id = ?")) {
                preparedStatement.setLong(1, id);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    logger.info("terminal with id = " + id + " was deleted");
                }

                connection.commit(); // Фиксируем удаление
                return rowsAffected > 0;
            } catch (SQLException e) {
                connection.rollback(); // Откат в случае ошибки
                logger.severe("Ошибка при удалении: " + e.getMessage());
                throw new DaoException(e);
            }
        } catch (SQLException e) {
            logger.severe("Ошибка транзакции: " + e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Terminal> findById(Long id) {
        try (Connection connection = ConnectionManager.open()) {
            return findById(id, connection);
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException(e);
        }
    }

    public Optional<Terminal> findById(Long key, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_TERMINAL_BY_ID)) {
            preparedStatement.setLong(1, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            Terminal terminal = null;
            if (resultSet.next()) {
                terminal = buildTerminal(resultSet);
            }
            logger.info("Найдено c  id = " + key);
            return Optional.ofNullable(terminal);
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException(e);
        }
    }


    @Override
    public List<Terminal> findAll() {
        List<Terminal> terminalList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_TERMINALS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Terminal terminal = buildTerminal(resultSet);
                terminalList.add(terminal);
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка при получении списка точек продаж", e);
        }
        return terminalList;
    }

//    @Override
//    public void createTable() {
//        try {
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(CREATE_TABLE_TERMINAL);
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
//        try {
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(TERMINAL);
//            logger.info("Table dropped");
//        } catch (SQLException e) {
//            logger.severe(e.getMessage());
//            throw new DaoException(e);
//        }
//        return true;
        return dropTableService(s);
    }

    @Override
    public Optional<Terminal> findByValue(String cardNumber) {
        return Optional.empty();
    }
}
