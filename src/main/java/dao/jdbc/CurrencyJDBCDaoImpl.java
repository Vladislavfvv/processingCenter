package dao.jdbc;

import dao.DAOAbstract;
import dao.DAOInterface;

import java.sql.*;

import exception.DaoException;
import model.Currency;
import util.ConnectionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class CurrencyJDBCDaoImpl extends DAOAbstract implements DAOInterface<Long, Currency> {
    private static final Logger logger = Logger.getLogger(CurrencyJDBCDaoImpl.class.getName());

    private static final String CREATE_TABLE_CURRENCY = "CREATE TABLE IF NOT EXISTS processingCenterSchema.currency\n" +
            "(\n" +
            "    id                    bigserial primary key,\n" +
            "    currency_digital_code varchar(3)   not null,\n" +
            "    currency_letter_code  varchar(3)   not null,\n" +
            "    currency_name         varchar(255) not null\n" +
            "    );";
    private static final String DELETE_CURRENCY_TABLE = "DELETE FROM currency WHERE id = ?";
    private static final String GET_ALL_CURRENCIES = "SELECT id, currency_digital_code, currency_letter_code, currency_name FROM currency";
    private static final String UPDATE_CURRENCY = "UPDATE currency SET currency_digital_code = ?, currency_letter_code = ?, currency_name = ? WHERE id = ?";
    private static final String GET_CURRENCY_BY_ID = "SELECT id, currency_digital_code, currency_letter_code, currency_name FROM currency WHERE id = ?";
    private static final String DELETE_CURRENCY_BY_ID = "DELETE FROM currency WHERE id = ?";
    private static final String INSERT_CURRENCY = "INSERT INTO currency(currency_digital_code, currency_letter_code, currency_name) VALUES (?, ?, ?)";

    public CurrencyJDBCDaoImpl(Connection connection) {
        super(connection);
    }

    private Currency buildCurrency(ResultSet resultSet) throws SQLException {
        return new Currency(resultSet.getLong("id"),
                resultSet.getString("currency_digital_code"),
                resultSet.getString("currency_letter_code"),
                resultSet.getString("currency_name")
        );
    }




//    @Override
//    public Currency insert(Currency currency) {
//        try {
//            if (!DAOAbstract.isTableExists(connection, "currency")) {
//                logger.warning("Таблица currency не существует. Создаю...");
//                createTableQuery(CREATE_TABLE_CURRENCY);
//            }
//
//            // Проверяем, существует ли уже currency с таким же currency
//            String checkExistenceQuery = "SELECT COUNT(*) FROM currency WHERE currency_digital_code = ? AND currency_letter_code = ? AND currency_name = ?";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(checkExistenceQuery)) {
//                preparedStatement.setString(1, currency.getCurrencyDigitalCode());
//                preparedStatement.setString(2, currency.getCurrencyLetterCode());
//                preparedStatement.setString(3, currency.getCurrencyName());
//                ResultSet resultSet = preparedStatement.executeQuery();
//                System.out.println(preparedStatement);
//                if (resultSet.next() && resultSet.getInt(1) > 0) {
//                    logger.info("Статус с currency: " + currency.getCurrencyName() + " уже существует.");
//                    // Если статус уже существует, возвращаем его
//                    return currency;
//                }
//            }
//
//
//            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CURRENCY, Statement.RETURN_GENERATED_KEYS);////второй параметр для получения идентификатора созданной сущности
//            preparedStatement.setString(1, currency.getCurrencyDigitalCode());
//            preparedStatement.setString(2, currency.getCurrencyLetterCode());
//            preparedStatement.setString(3, currency.getCurrencyName());
//            preparedStatement.executeUpdate();
//
//            ResultSet resultSet = preparedStatement.getGeneratedKeys();
//            if (resultSet.next()) {
//                currency.setCurrencyDigitalCode(resultSet.getString(1));
//                currency.setCurrencyLetterCode(resultSet.getString(2));
//                currency.setCurrencyName(resultSet.getString(3));
//                currency.setId(resultSet.getLong(1));
//            }
//            logger.info("currency with CurrencyName: " + currency.getCurrencyName() + " was added.");
//            return currency;
//        } catch (SQLException e) {
//            logger.severe(e.getMessage());
//            throw new DaoException(e);
//        }
//    }

    @Override
    public Currency insert(Currency currency) {
        try {
            if (!DAOAbstract.isTableExists(connection, "currency")) {
                logger.warning("Таблица currency не существует. Создаю...");
                createTableQuery(CREATE_TABLE_CURRENCY);
            }

            String checkExistenceQuery = "SELECT COUNT(*) FROM currency WHERE currency_digital_code = ? AND currency_letter_code = ? AND currency_name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(checkExistenceQuery)) {
                preparedStatement.setString(1, currency.getCurrencyDigitalCode());
                preparedStatement.setString(2, currency.getCurrencyLetterCode());
                preparedStatement.setString(3, currency.getCurrencyName());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    logger.info("Валюта " + currency.getCurrencyName() + " уже существует.");
                    return currency;
                }
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CURRENCY, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, currency.getCurrencyDigitalCode());
                preparedStatement.setString(2, currency.getCurrencyLetterCode());
                preparedStatement.setString(3, currency.getCurrencyName());
                preparedStatement.executeUpdate();

                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        currency.setId(resultSet.getLong(1)); // Только ID
                    }
                }
            }

            logger.info("Валюта " + currency.getCurrencyName() + " добавлена.");
            return currency;
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Currency currency) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CURRENCY)) {
            preparedStatement.setString(1, currency.getCurrencyDigitalCode());
            preparedStatement.setString(2, currency.getCurrencyLetterCode());
            preparedStatement.setString(3, currency.getCurrencyName());
            preparedStatement.setLong(4, currency.getId());
            logger.info("cardStatus with cardStatusName: " + currency.getId() + " was updated.");
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Ошибка при обновлении currency", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CURRENCY_BY_ID)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при удалении", e);
        }
    }

    @Override
    public Optional<Currency> findById(Long id) {
        try (Connection connection = ConnectionManager.open()) {
           return findById(id, connection);
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при поиске currency ", e);
        }
    }

    public Optional<Currency> findById(long id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_CURRENCY_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Currency currency = null;
            if (resultSet.next()) {
                currency = buildCurrency(resultSet);

                logger.info("currency with ID: " + currency.getId() + " was found.");
                return Optional.of(currency);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при поиске currency ", e);
        }
    }


    @Override
    public List<Currency> findAll() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CURRENCIES);) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Currency> currencies = new ArrayList<>();

            while (resultSet.next()) {
                currencies.add(buildCurrency(resultSet));
            }
            return currencies;
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



    public Optional<Currency> getCurrencyByCode(String currencyCode) {
        String query = "SELECT * FROM currency WHERE currency_digital_code = ?";  // Запрос для поиска валюты по коду
        try (Connection connection = ConnectionManager.open();  // Открываем соединение
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, currencyCode);  // Устанавливаем параметр с кодом валюты

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Если результат найден, создаем объект Currency
                Currency currency = buildCurrency(resultSet);

                logger.info("Валюта с кодом " + currencyCode + " была найдена.");
                return Optional.of(currency);  // Возвращаем найденную валюту
            } else {
                return Optional.empty();  // Если валюта не найдена, возвращаем пустой Optional
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при поиске валюты по коду: " + currencyCode, e);
        }
    }

    @Override
    public boolean createTableQuery(String sql) {
        return createTableService(CREATE_TABLE_CURRENCY);
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
    public Optional<Currency> findByValue(String cardNumber) {
        return Optional.empty();
    }


}
