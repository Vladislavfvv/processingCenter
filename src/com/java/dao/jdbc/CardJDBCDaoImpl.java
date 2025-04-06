package com.java.dao.jdbc;

import com.java.dao.DAOAbstract;
import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.exception.DaoException;
import com.java.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;



public class CardJDBCDaoImpl extends DAOAbstract implements DAOInterface<Long, Card> {
    private static final Logger logger = Logger.getLogger(CardJDBCDaoImpl.class.getName());

    private final DAOInterface<Long, CardStatus> cardStatusDAOImpl;
    private final DAOInterface<Long, PaymentSystem> paymentSystemDAOImpl;
    private final DAOInterface<Long, Account> accountDAOImpl;
    private final Connection connection;


    public CardJDBCDaoImpl(Connection connection) {
        super(connection);
        this.connection = connection;
        this.cardStatusDAOImpl = DAOFactory.getCardStatusDAO(connection);
        this.paymentSystemDAOImpl = DAOFactory.getPaymentSystemDAO(connection);
        this.accountDAOImpl = DAOFactory.getAccountDAO(connection);
    }


    private static final String CREATE_TABLE_CARD = "CREATE TABLE IF NOT EXISTS processingCenterSchema.card\n" +
            "(\n" +
            "    id                         bigserial primary key,\n" +
            "    card_number                varchar(50),\n" +
            "    expiration_date            date,\n" +
            "    holder_name                varchar(50),\n" +
            "    card_status_id             bigint REFERENCES processingCenterSchema.card_status (id) ON DELETE CASCADE\n" +
            "                                                                  ON UPDATE CASCADE,\n" +
            "    payment_system_id          bigint REFERENCES processingCenterSchema.payment_system (id) ON DELETE CASCADE\n" +
            "                                                                  ON UPDATE CASCADE,\n" +
            "    account_id                 bigint REFERENCES processingCenterSchema.account ON DELETE CASCADE\n" +
            "                                                                  ON UPDATE CASCADE,\n" +
            "    received_from_issuing_bank timestamp,\n" +
            "    sent_to_issuing_bank       timestamp\n" +
            "    );";

    private static final String FIND_ALL_CARD = "SELECT id, card_number, expiration_date, holder_name, card_status_id, payment_system_id, account_id, received_from_issuing_bank, sent_to_issuing_bank" +
            "FROM processingCenterSchema.card";

    private static final String FIND_CARD_BY_ID = "SELECT id, card_number, expiration_date, holder_name, card_status_id, payment_system_id, account_id, received_from_issuing_bank, sent_to_issuing_bank" +
            " FROM processingCenterSchema.card WHERE id = ?";

    private static final String DROP_TABLE_CARD = "DROP TABLE IF EXISTS processingCenterSchema.card CASCADE";
    private static final String DELETE_ALL_CARD = "DELETE FROM processingCenterSchema.card";
    private static final String DELETE_CARD_BY_ID = "DELETE FROM processingCenterSchema.card where id = ?";
    private static final String UPDATE_CARD = "UPDATE processingCenterSchema.card " +
            "SET card_number = ?, expiration_date = ?, holder_name = ?, card_status_id = ?, payment_system_id = ?, account_id = ?, received_from_issuing_bank = ?, sent_to_issuing_bank = ?  WHERE id = ?";

    private static final String INSERT_CARD = "INSERT INTO processingCenterSchema.card (card_number, expiration_date, holder_name, card_status_id, payment_system_id, account_id, received_from_issuing_bank, sent_to_issuing_bank) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";

    @Override
    public Card insert(Card value) {
        Connection connection = this.connection;
        try {
            connection.setAutoCommit(false); // начинаем транзакцию

            // Проверка на null
            if (value == null || value.getCardStatusId() == null || value.getPaymentSystemId() == null || value.getAccountId() == null) {
                logger.warning("CARD или его поля (cardStatus, paymentSystem, account) равны null.");
                connection.rollback();
                return null;
            }

            // Проверка на существование кард-статуса
            Optional<CardStatus> optionalCurrency = cardStatusDAOImpl.findById(value.getCardStatusId().getId());
            if (optionalCurrency.isEmpty()) {
                logger.warning("CqrdStatus с id = " + value.getCardStatusId().getId() + " не найден.");
                connection.rollback();
                return null;
            }

            // Проверка на существование банка
            Optional<PaymentSystem> optionalIssuingBank = paymentSystemDAOImpl.findById(value.getPaymentSystemId().getId());
            if (optionalIssuingBank.isEmpty()) {
                logger.warning("IssuingBank с id = " + value.getPaymentSystemId().getId() + " не найден.");
                connection.rollback();
                return null;
            }

            try (PreparedStatement ps = connection.prepareStatement(INSERT_CARD)) {
                ps.setString(1, value.getCardNumber());
                ps.setDate(2, value.getExpirationDate());
                ps.setString(3, value.getHolderName());
                ps.setLong(4, value.getCardStatusId().getId());
                ps.setLong(5, value.getPaymentSystemId().getId());
                ps.setLong(6, value.getAccountId().getId());
                ps.setTimestamp(7, value.getReceivedFromIssuingBank());
                ps.setTimestamp(8, value.getSentToIssuingBank());

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    value.setId(rs.getLong("id"));
                    connection.commit();
                    logger.info("Card успешно сохранен с id = " + value.getId());
                    return value;
                } else {
                    connection.rollback();
                    logger.warning("Card не был сохранен (отсутствует сгенерированный id).");
                    return null;
                }

            } catch (SQLException e) {
                connection.rollback();
                logger.severe("Ошибка при вставке Card: " + e.getMessage());
                throw new DaoException(e);
            }

        } catch (SQLException e) {
            logger.severe("Ошибка управления транзакцией: " + e.getMessage());
            throw new DaoException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.warning("Не удалось включить автокоммит: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean update(Card value) {
        Connection connection = this.connection;
        try {
            connection.setAutoCommit(false);

            // Проверка на null
            if (value == null || value.getCardStatusId() == null || value.getPaymentSystemId() == null || value.getAccountId() == null) {
                logger.warning("Card или его поля (card_status_id, payment_system_id, account_id) равны null. А так быть не должно");
                connection.rollback();
                return false;
            }

            // Проверка существования кард-статуса
            if (cardStatusDAOImpl.findById(value.getCardStatusId().getId()).isEmpty()) {
                logger.warning("Кард-статус с id = " + value.getCardStatusId().getId() + " не найден.");
                connection.rollback();
                return false;
            }

            // Проверка существования банка
            if (paymentSystemDAOImpl.findById(value.getPaymentSystemId().getId()).isEmpty()) {
                logger.warning("PaymentSystem с id = " + value.getPaymentSystemId().getId() + " не найден.");
                connection.rollback();
                return false;
            }

            try (PreparedStatement ps = connection.prepareStatement(UPDATE_CARD)) {
                ps.setString(1, value.getCardNumber());
                ps.setDate(2, value.getExpirationDate());
                ps.setString(3, value.getHolderName());
                ps.setLong(4, value.getCardStatusId().getId());
                ps.setLong(5, value.getPaymentSystemId().getId());
                ps.setLong(6, value.getAccountId().getId());
                ps.setTimestamp(7, value.getReceivedFromIssuingBank());
                ps.setTimestamp(8, value.getSentToIssuingBank());


                int updated = ps.executeUpdate();
                if (updated == 1) {
                    connection.commit();
                    logger.info("Account с id = " + value.getId() + " обновлен.");
                    return true;
                } else {
                    connection.rollback();
                    logger.warning("Account с id = " + value.getId() + " не найден для обновления.");
                    return false;
                }

            } catch (SQLException e) {
                connection.rollback();
                logger.severe("Ошибка при обновлении Account: " + e.getMessage());
                throw new DaoException(e);
            }

        } catch (SQLException e) {
            logger.severe("Ошибка управления транзакцией: " + e.getMessage());
            throw new DaoException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.warning("Не удалось включить автокоммит: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean delete(Long key) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_CARD_BY_ID)) {
            ps.setLong(1, key);
            int deleted = ps.executeUpdate();
            if (deleted == 1) {
                logger.info("Удалёна CARD с id = " + key);
                return true;
            } else {
                logger.warning("CARD с id = " + key + " не найдена для удаления.");
                return false;
            }
        } catch (SQLException e) {
            logger.severe("Ошибка при удалении CARD с id = " + key + ": " + e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Card> findById(Long key) {
        try (PreparedStatement ps = connection.prepareStatement(FIND_CARD_BY_ID)) {
            ps.setLong(1, key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Card card = new Card();
                card.setId(rs.getLong("id"));
                card.setCardNumber(rs.getString("card_number"));
                card.setExpirationDate(rs.getDate("expiration_date"));
                card.setReceivedFromIssuingBank(rs.getTimestamp("received_from_issuing_bank"));
                card.setSentToIssuingBank(rs.getTimestamp("sent_to_issuing_bank"));


                long cardStatusId = rs.getLong("card_status_id");
                long paymentSystemId = rs.getLong("payment_system_id");
                long accountId = rs.getLong("account_id");

                cardStatusDAOImpl.findById(cardStatusId).ifPresent(cardStatus -> card.setCardStatusId(cardStatus));
                paymentSystemDAOImpl.findById(paymentSystemId).ifPresent(paymentSystem -> card.setPaymentSystemId(paymentSystem));
                accountDAOImpl.findById(accountId).ifPresent(account -> card.setAccountId(account));

                logger.info("Найдена Card с id = " + key);
                return Optional.of(card);
            } else {
                logger.warning("Card с id = " + key + " не найдена.");
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.severe("Ошибка при поиске Card с id = " + key + ": " + e.getMessage());
            throw new DaoException(e);
        }
    }


    @Override
    public List<Card> findAll() {
        List<Card> cardsList = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL_CARD);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Card card = new Card();
                card.setId(rs.getLong("id"));
                card.setCardNumber(rs.getString("card_number"));
                card.setExpirationDate(rs.getDate("expiration_date"));
                card.setReceivedFromIssuingBank(rs.getTimestamp("received_from_issuing_bank"));
                card.setSentToIssuingBank(rs.getTimestamp("sent_to_issuing_bank"));


                long cardStatusId = rs.getLong("card_status_id");
                long paymentSystemId = rs.getLong("payment_system_id");
                long accountId = rs.getLong("account_id");

                cardStatusDAOImpl.findById(cardStatusId).ifPresent(cardStatus -> card.setCardStatusId(cardStatus));
                paymentSystemDAOImpl.findById(paymentSystemId).ifPresent(paymentSystem -> card.setPaymentSystemId(paymentSystem));
                accountDAOImpl.findById(accountId).ifPresent(account -> card.setAccountId(account));

                cardsList.add(card);
            }

            logger.info("Найдено crds: " + cardsList.size());
            return cardsList;

        } catch (SQLException e) {
            logger.severe("Ошибка при получении списка карт: " + e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public void createTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(CREATE_TABLE_CARD);
            logger.info("Table created");
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean deleteAll(String s) {
        return deleteAllService("processingcenterschema.card");
    }

    @Override
    public boolean dropTable(String s) {
        return dropTableService("processingcenterschema.account");
    }


}
