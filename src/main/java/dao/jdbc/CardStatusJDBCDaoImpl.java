package dao.jdbc;

import dao.DAOAbstract;
import dao.DAOInterface;
import exception.DaoException;
import model.CardStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class CardStatusJDBCDaoImpl extends DAOAbstract implements DAOInterface<Long, CardStatus> {
    private static final Logger logger = Logger.getLogger(CardStatusJDBCDaoImpl.class.getName());


    public CardStatusJDBCDaoImpl(Connection connection) {
        super(connection);

    }

    private static final String CREATE_TABLE_CARD_STATUS = "CREATE TABLE IF NOT EXISTS processingCenterSchema.card_status (id  bigserial primary key, card_status_name varchar(255) UNIQUE not null);";
    private static final String GET_ALL_CARD_STATUS = "SELECT id, card_status_name FROM card_status";
    private static final String UPDATE_CARD_STATUS = "UPDATE card_status SET card_status_name = ? WHERE id = ?";
    private static final String GET_CARD_STATUS_BY_ID = "SELECT id, card_status_name FROM card_status WHERE id = ?";
    private static final String DELETE_CARD_STATUS_BY_ID = "DELETE FROM card_status WHERE id = ?";
    private static final String INSERT_SQL = "INSERT INTO card_status (card_status_name) VALUES (?)";
//    private static final String INSERT_SQL = "INSERT INTO processingCenterSchema.card_status (card_status_name)\n" +
//            "    VALUES (?)\n" +
//            "    ON CONFLICT (card_status_name)\n" +
//            "    DO UPDATE SET card_status_name = EXCLUDED.card_status_name;";

    private CardStatus buildCardStatus(ResultSet resultSet) throws SQLException {
        return new CardStatus(resultSet.getLong("id"),
                resultSet.getString("card_status_name"));
    }

    @Override
    public CardStatus insert(CardStatus cardStatus) {
        try {
            if (!DAOAbstract.isTableExists(connection, "card_status")) {
                logger.warning("Таблица card_status не существует. Создаю...");
                createTableQuery(CREATE_TABLE_CARD_STATUS);
            }

            // Проверяем, существует ли уже статус с таким же cardStatusName
            String checkExistenceQuery = "SELECT COUNT(*) FROM card_status WHERE card_status_name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(checkExistenceQuery)) {
                preparedStatement.setString(1, cardStatus.getCardStatusName());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    logger.info("CardStatusName: " + cardStatus.getCardStatusName() + " уже существует.");
                    // Если статус уже существует, возвращаем его
                    return cardStatus;
                }
            }

            // Вставка нового статуса, если такого еще нет
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, cardStatus.getCardStatusName());
                preparedStatement.executeUpdate();

                // Получение сгенерированного ID для нового статуса
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    cardStatus.setId(resultSet.getLong(1));
                }
                logger.info("cardStatus с cardStatusName: " + cardStatus.getCardStatusName() + " добавлен.");
                return cardStatus;
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(CardStatus cardStatus) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CARD_STATUS)) {
            preparedStatement.setString(1, cardStatus.getCardStatusName());
            preparedStatement.setLong(2, cardStatus.getId());
            logger.info("cardStatus с cardStatusName: " + cardStatus.getCardStatusName() + " обновлен.");
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Ошибка при обновлении cardStatus", e);
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
    public Optional<CardStatus> findById(Long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_CARD_STATUS_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            CardStatus cardStatus = null;
            if (resultSet.next()) {
                cardStatus = new CardStatus(resultSet.getLong("id"),
                        resultSet.getString("card_status_name"));

                logger.info("cardStatus с cardStatusName: " + cardStatus.getCardStatusName() + " найден.");
                return Optional.ofNullable(cardStatus);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при поиске cardStatusName value", e);
        }
    }

    @Override
    public List<CardStatus> findAll() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CARD_STATUS);) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CardStatus> cardStatusList = new ArrayList<>();

            while (resultSet.next()) {
                cardStatusList.add(buildCardStatus(resultSet));
            }
            return cardStatusList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public boolean createTableQuery(String sql) {
        return createTableService(CREATE_TABLE_CARD_STATUS);
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
    public Optional<CardStatus> findByValue(String cardNumber) {
        return Optional.empty();
    }

//    private boolean isTableExists(Connection connection, String tableName) {
//        String sql = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = 'processingcenterschema' AND table_name = ?)";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, tableName);
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    return resultSet.getBoolean(1);
//                }
//            }
//        } catch (SQLException e) {
//            logger.severe("Ошибка при проверке таблицы: " + e.getMessage());
//            throw new DaoException(e);
//        }
//        return false;
//    }


}
