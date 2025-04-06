package com.java.dao.jdbc;

import java.sql.*;
import com.java.dao.DAOAbstract;
import com.java.dao.DAOFactory;
import com.java.dao.DAOInterface;
import com.java.exception.DaoException;
import com.java.model.*;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class TransactionJDBCDaoImpl extends DAOAbstract implements DAOInterface<Long, Transaction> {
    private static final Logger logger = Logger.getLogger(TransactionJDBCDaoImpl.class.getName());

    private final Connection connection;
    private final DAOInterface<Long, Account> accountDAOImpl;
    private final DAOInterface<Long, TransactionType> transactionTypeDAOImpl;
    private final DAOInterface<Long, Card> cardDAOImpl;
    private final DAOInterface<Long, Terminal> terminalDAOImpl;
    private final DAOInterface<Long, ResponseCode> responseCodeDAOImpl;

    public TransactionJDBCDaoImpl(Connection connection) {
        super(connection);
        this.connection = connection;
        this.accountDAOImpl = DAOFactory.getAccountDAO(connection);
        this.transactionTypeDAOImpl = DAOFactory.getTransactionTypeDAO(connection);
        this.cardDAOImpl = DAOFactory.getCardDAO(connection);
        this.terminalDAOImpl = DAOFactory.getTerminalDAO(connection);
        this.responseCodeDAOImpl = DAOFactory.getResponseCodeDAO(connection);
    }


    private static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE IF NOT EXISTS processingCenterSchema.transaction\n" +
            "(\n" +
            "    id                         bigserial primary key,\n" +
            "    transaction_date           date,\n" +
            "    sum                        decimal,\n" +
            "    transaction_name           varchar(255),\n" +
            "    account_id                 bigint REFERENCES processingCenterSchema.account ON DELETE CASCADE\n" +
            "                                                         ON UPDATE CASCADE,\n" +
            "    transaction_type_id        bigint REFERENCES processingCenterSchema.transaction_type (id) ON DELETE CASCADE\n" +
            "                                                         ON UPDATE CASCADE,\n" +
            "    card_id                    bigint REFERENCES processingCenterSchema.card (id) ON DELETE CASCADE\n" +
            "                                                         ON UPDATE CASCADE,\n" +
            "    terminal_id                bigint REFERENCES processingCenterSchema.terminal ON DELETE CASCADE\n" +
            "                                                         ON UPDATE CASCADE,\n" +
            "    response_code_id           bigint REFERENCES processingCenterSchema.response_code ON DELETE CASCADE\n" +
            "                                                         ON UPDATE CASCADE,\n" +
            "    authorization_code         varchar(6),\n" +
            "    received_from_issuing_bank timestamp,\n" +
            "    sent_to_issuing_bank       timestamp\n" +
            "    );";

    private static final String FIND_ALL_TRANSACTIONS = "SELECT id, transaction_date, sum, transaction_name, account_id, transaction_type_id, card_id, terminal_id, response_code_id, authorization_code, received_from_issuing_bank, sent_to_issuing_bank" +
            "FROM processingCenterSchema.transaction";

    private static final String FIND_TRANSACTION_BY_ID = "SELECT id, transaction_date, sum, transaction_name, account_id, transaction_type_id, card_id, terminal_id, response_code_id, authorization_code, received_from_issuing_bank, sent_to_issuing_bank" +
            " FROM processingCenterSchema.transaction WHERE id = ?";

    private static final String DROP_TABLE_TRANSACTION = "DROP TABLE IF EXISTS processingCenterSchema.transaction CASCADE";
    private static final String DELETE_ALL_TRANSACTION = "DELETE FROM processingCenterSchema.transaction";
    private static final String DELETE_TRANSACTION_BY_ID = "DELETE FROM processingCenterSchema.transaction where id = ?";
    private static final String UPDATE_TRANSACTION = "UPDATE processingCenterSchema.transaction " +
            "SET transaction_date = ?, sum = ?, transaction_name = ?, account_id = ?, transaction_type_id = ?, card_id = ?, terminal_id = ?, response_code_id = ?, authorization_code = ?, received_from_issuing_bank = ?, sent_to_issuing_bank = ?  WHERE id = ?";

    private static final String INSERT_TRANSACTION = "INSERT INTO processingCenterSchema.transaction (transaction_date, sum, transaction_name, account_id, transaction_type_id, card_id, terminal_id, response_code_id, authorization_code, received_from_issuing_bank, sent_to_issuing_bank) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";




    @Override
    public Transaction insert(Transaction value) {
        Connection connection = this.connection;
        try {
            connection.setAutoCommit(false); // начинаем транзакцию

            // Проверка на null
            if (value == null || value.getTransactionTypeId() == null || value.getCardId() == null || value.getTerminalId() == null || value.getResponseCodeId() == null) {
                logger.warning("Transaction или его поля (transaction_type_id, card_id, terminal_id, response_code_id) равны null.");
                connection.rollback();
                return null;
            }

            // Проверка на существование аккаунта
            Optional<Account> optionalAccount = accountDAOImpl.findById(value.getAccountId().getId());
            if (optionalAccount.isEmpty()) {
                logger.warning("CqrdStatus с id = " + value.getAccountId().getId() + " не найден.");
                connection.rollback();
                return null;
            }

            // Проверка на существование transaction_type
            Optional<TransactionType> optionalTransactionType = transactionTypeDAOImpl.findById(value.getTransactionTypeId().getId());
            if (optionalTransactionType.isEmpty()) {
                logger.warning("TransactionType с id = " + value.getTransactionTypeId().getId() + " не найден.");
                connection.rollback();
                return null;
            }

            try (PreparedStatement ps = connection.prepareStatement(INSERT_TRANSACTION)) {
                ps.setDate(1, value.getTransactionDate());
                ps.setBigDecimal(2, value.getSum());
                ps.setString(3, value.getTransactionName());
                ps.setLong(4, value.getAccountId().getId());
                ps.setLong(5, value.getTransactionTypeId().getId());
                ps.setLong(6, value.getCardId().getId());
                ps.setLong(7, value.getTerminalId().getId());
                ps.setLong(8, value.getResponseCodeId().getId());
                ps.setString(9, value.getAuthorizationCode());
                ps.setTimestamp(10, value.getReceivedFromIssuingBank());
                ps.setTimestamp(11, value.getSentToIssuingBank());

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    value.setId(rs.getLong("id"));
                    connection.commit();
                    logger.info("Transaction успешно сохранен с id = " + value.getId());
                    return value;
                } else {
                    connection.rollback();
                    logger.warning("Transaction не был сохранен (отсутствует сгенерированный id).");
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
    public boolean update(Transaction value) {
        Connection connection = this.connection;
        try {
            connection.setAutoCommit(false);

            // Проверка на null
            if (value == null || value.getTransactionTypeId() == null || value.getCardId() == null || value.getTerminalId() == null || value.getResponseCodeId() == null) {
                logger.warning("Transaction или его поля (transaction_type_id, card_id, terminal_id, response_code_id) равны null.");
                connection.rollback();
                return false;
            }

            // Проверка на существование аккаунта
            Optional<Account> optionalAccount = accountDAOImpl.findById(value.getAccountId().getId());
            if (optionalAccount.isEmpty()) {
                logger.warning("CqrdStatus с id = " + value.getAccountId().getId() + " не найден.");
                connection.rollback();
                return false;
            }

            // Проверка на существование transaction_type
            Optional<TransactionType> optionalTransactionType = transactionTypeDAOImpl.findById(value.getTransactionTypeId().getId());
            if (optionalTransactionType.isEmpty()) {
                logger.warning("TransactionType с id = " + value.getTransactionTypeId().getId() + " не найден.");
                connection.rollback();
                return false;
            }

            try (PreparedStatement ps = connection.prepareStatement(UPDATE_TRANSACTION)) {
                ps.setDate(1, value.getTransactionDate());
                ps.setBigDecimal(2, value.getSum());
                ps.setString(3, value.getTransactionName());
                ps.setLong(4, value.getAccountId().getId());
                ps.setLong(5, value.getTransactionTypeId().getId());
                ps.setLong(6, value.getCardId().getId());
                ps.setLong(7, value.getTerminalId().getId());
                ps.setLong(8, value.getResponseCodeId().getId());
                ps.setString(9, value.getAuthorizationCode());
                ps.setTimestamp(10, value.getReceivedFromIssuingBank());
                ps.setTimestamp(11, value.getSentToIssuingBank());


                int updated = ps.executeUpdate();

                if (updated == 1) {
                    connection.commit();
                    logger.info("Transaction с id = " + value.getId() + " обновлена.");
                    return true;
                } else {
                    connection.rollback();
                    logger.warning("Transaction с id = " + value.getId() + " не найдена для обновления.");
                    return false;
                }

            } catch (SQLException e) {
                connection.rollback();
                logger.severe("Ошибка при обновлении Transaction: " + e.getMessage());
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
    public boolean delete(Long id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_TRANSACTION_BY_ID)) {
            ps.setLong(1, id);
            int deleted = ps.executeUpdate();
            if (deleted == 1) {
                logger.info("Удалёна Transaction с id = " + id);
                return true;
            } else {
                logger.warning("Transaction с id = " + id + " не найдена для удаления.");
                return false;
            }
        } catch (SQLException e) {
            logger.severe("Ошибка при удалении Transaction с id = " + id + ": " + e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Transaction> findById(Long key) {
        try (PreparedStatement ps = connection.prepareStatement(FIND_TRANSACTION_BY_ID)) {
            ps.setLong(1, key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getLong("id"));
                transaction.setTransactionDate(rs.getDate("transaction_date"));
                transaction.setSum(rs.getBigDecimal("sum"));
                transaction.setTransactionName(rs.getString("transaction_name"));
                transaction.setAuthorizationCode(rs.getString("authorization_code"));
                transaction.setReceivedFromIssuingBank(rs.getTimestamp("received_from_issuing_bank"));
                transaction.setSentToIssuingBank(rs.getTimestamp("sent_to_issuing_bank"));


                long accountId = rs.getLong("account_id");
                long transactionTypeId = rs.getLong("transaction_type_id");
                long cardId = rs.getLong("card_id");
                long terminalId = rs.getLong("terminal_id");
                long responseCodeId = rs.getLong("response_code_id");

                accountDAOImpl.findById(accountId).ifPresent(account -> transaction.setAccountId(account));
                transactionTypeDAOImpl.findById(transactionTypeId).ifPresent(transactionType -> transaction.setTransactionTypeId(transactionType));
                cardDAOImpl.findById(cardId).ifPresent(card -> transaction.setCardId(card));
                terminalDAOImpl.findById(terminalId).ifPresent(terminal -> transaction.setTerminalId(terminal));
                responseCodeDAOImpl.findById(responseCodeId).ifPresent(responseCode -> transaction.setResponseCodeId(responseCode));


                logger.info("Найдена Transaction с id = " + key);
                return Optional.of(transaction);
            } else {
                logger.warning("Transaction с id = " + key + " не найдена.");
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.severe("Ошибка при поиске Transaction с id = " + key + ": " + e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactionsList = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL_TRANSACTIONS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getLong("id"));
                transaction.setTransactionDate(rs.getDate("transaction_date"));
                transaction.setSum(rs.getBigDecimal("sum"));
                transaction.setTransactionName(rs.getString("transaction_name"));
                transaction.setAuthorizationCode(rs.getString("authorization_code"));
                transaction.setReceivedFromIssuingBank(rs.getTimestamp("received_from_issuing_bank"));
                transaction.setSentToIssuingBank(rs.getTimestamp("sent_to_issuing_bank"));


                long accountId = rs.getLong("account_id");
                long transactionTypeId = rs.getLong("transaction_type_id");
                long cardId = rs.getLong("card_id");
                long terminalId = rs.getLong("terminal_id");
                long responseCodeId = rs.getLong("response_code_id");

                accountDAOImpl.findById(accountId).ifPresent(account -> transaction.setAccountId(account));
                transactionTypeDAOImpl.findById(transactionTypeId).ifPresent(transactionType -> transaction.setTransactionTypeId(transactionType));
                cardDAOImpl.findById(cardId).ifPresent(card -> transaction.setCardId(card));
                terminalDAOImpl.findById(terminalId).ifPresent(terminal -> transaction.setTerminalId(terminal));
                responseCodeDAOImpl.findById(responseCodeId).ifPresent(responseCode -> transaction.setResponseCodeId(responseCode));


                transactionsList.add(transaction);
            }

            logger.info("Найдено crds: " + transactionsList.size());
            return transactionsList;

        } catch (SQLException e) {
            logger.severe("Ошибка при получении списка транзакций: " + e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean createTableQuery(String sql) {
        return createTableService(CREATE_TABLE_TRANSACTION);
    }

    @Override
    public boolean deleteAll(String s) {
        return deleteAllService("processingcenterschema.transaction");
    }

    @Override
    public boolean dropTable(String s) {
        return dropTableService("processingcenterschema.transaction");
    }
}
