package dao.jdbc;

import dao.DAOAbstract;
import dao.DAOFactory;
import dao.DAOInterface;
import exception.DaoException;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class AccountJDBCDaoImpl extends DAOAbstract implements DAOInterface<Long, Account> {

    private static final Logger logger = Logger.getLogger(AccountJDBCDaoImpl.class.getName());

    private final DAOInterface<Long, Currency> currencyJDBCDaoImpl;
    private final DAOInterface<Long, IssuingBank> issuingBankJDBCDaoImpl;

    public AccountJDBCDaoImpl(Connection connection) {
        super(connection);
        this.currencyJDBCDaoImpl = DAOFactory.getCurrencyDAO(connection);
        this.issuingBankJDBCDaoImpl = DAOFactory.getIssuingBankDAO(connection);
    }


    private static final String FIND_ACCOUNT_BY_ID = "SELECT a.id, a.account_number, a.balance, " +
            "c.id AS currency_id, c.currency_digital_code, c.currency_letter_code,  c.currency_name, " +
            "ib.id as issuing_bank_id, ib.bic, ib.bin, ib.abbreviated_name " +
            "FROM processingCenterSchema.account a " +
            "JOIN processingCenterSchema.currency c ON a.currency_id = c.id " +
            "JOIN processingCenterSchema.issuing_bank ib ON a.issuing_bank_id = ib.id " +
            "WHERE a.id = ? ";

    private static final String FIND_ALL_ACCOUNTS = "SELECT a.id, a.account_number, a.balance, " +
            "c.id AS currency_id, c.currency_digital_code, c.currency_letter_code,  c.currency_name, " +
            "ib.id as issuing_bank_id, ib.bic, ib.bin, ib.abbreviated_name " +
            "FROM processingCenterSchema.account a " +
            "JOIN processingCenterSchema.currency c ON a.currency_id = c.id " +
            "JOIN processingCenterSchema.issuing_bank ib ON a.issuing_bank_id = ib.id ";

    private static final String FIND_ALL_ACCOUNTS2 = "SELECT id, account_number, balance, currency_id, issuing_bank_id " +
            "FROM processingCenterSchema.account";

    private static final String FIND_ACCOUNT_BY_ID2 = "SELECT id, account_number, balance, currency_id, issuing_bank_id " +
            "FROM processingCenterSchema.account WHERE id = ?";

    private static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE IF NOT EXISTS processingCenterSchema.account\n" +
            "            (\n" +
            "                    id              bigserial UNIQUE primary key,\n" +
            "                    account_number  varchar(50) not null,\n" +
            "    balance         decimal,\n" +
            "    currency_id     bigint REFERENCES processingCenterSchema.currency (id) ON DELETE CASCADE\n" +
            "    ON UPDATE CASCADE,\n" +
            "    issuing_bank_id bigint REFERENCES processingCenterSchema.issuing_bank (id) ON DELETE CASCADE\n" +
            "    ON UPDATE CASCADE\n" +
            "    );";

    private static final String DROP_TABLE_ACCOUNT = "DROP TABLE IF EXISTS processingCenterSchema.account CASCADE";
    private static final String DELETE_ALL_ACCOUNTS = "DELETE FROM processingCenterSchema.account";
    private static final String DELETE_ACCOUNT_BY_ID = "DELETE FROM processingCenterSchema.account where id = ?";
    private static final String UPDATE_ACCOUNT = "UPDATE processingCenterSchema.account SET account_number = ?, balance = ?, currency_id = ?, issuing_bank_id = ?  WHERE id = ?";

    private static final String INSERT_ACCOUNT = "INSERT INTO processingCenterSchema.account (account_number, balance, currency_id, issuing_bank_id) " +
            "VALUES (?, ?, ?, ?) RETURNING id";

    private Account buildAccount(ResultSet resultSet) throws SQLException {
        // Получаем Account из DAO
        Optional<Currency> optionalCurrency = currencyJDBCDaoImpl.findById(resultSet.getLong("currency_id"));
        Currency currency = optionalCurrency.orElse(null);

        Optional<IssuingBank> optionalIssuingBank = issuingBankJDBCDaoImpl.findById(resultSet.getLong("issuing_bank_id"));
        IssuingBank issuingBank = optionalIssuingBank.orElse(null);

        Account account = new Account(
                resultSet.getLong("id"),
                resultSet.getString("account_number"),
                resultSet.getBigDecimal("balance"),
                currency,
                issuingBank
        );
        return account;
    }

    @Override
    public Account insert(Account account) {
        try {
            connection.setAutoCommit(false); // начинаем транзакцию

//            // Проверка на null
//            if (value == null || value.getCurrencyId() == null || value.getIssuingBankId() == null) {
//                logger.warning("Account или его поля (currency/issuingBank) равны null.");
//                connection.rollback();
//                return null;
//            }

//            // Проверка на существование валюты
//            Optional<Currency> optionalCurrency = currencyJDBCDaoImpl.findById(value.getCurrencyId().getId());
//            if (optionalCurrency.isEmpty()) {
//                logger.warning("Валюта с id = " + value.getCurrencyId().getId() + " не найдена.");
//                connection.rollback();
//                return null;
//            }
//
//            // Проверка на существование банка
//            Optional<IssuingBank> optionalIssuingBank = issuingBankJDBCDaoImpl.findById(value.getIssuingBankId().getId());
//            if (optionalIssuingBank.isEmpty()) {
//                logger.warning("IssuingBank с id = " + value.getIssuingBankId().getId() + " не найден.");
//                connection.rollback();
//                return null;
//            }

            String checkExistenceQuery = "SELECT COUNT(*) FROM account WHERE account_number = ? AND balance = ? AND currency_id = ? AND issuing_bank_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(checkExistenceQuery)) {
                preparedStatement.setString(1, account.getAccountNumber());
                preparedStatement.setBigDecimal(2, account.getBalance());
                preparedStatement.setLong(3, account.getCurrencyId().getId());
                preparedStatement.setLong(4, account.getIssuingBankId().getId());

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    logger.info("account с account: " + account.getAccountNumber() + " уже существует.");
                    // Если акаунт уже существует, возвращаем её
                    return account;
                }
            }

            try (PreparedStatement ps = connection.prepareStatement(INSERT_ACCOUNT)) {
                ps.setString(1, account.getAccountNumber());
                ps.setBigDecimal(2, account.getBalance());
                ps.setLong(3, account.getCurrencyId().getId());
                ps.setLong(4, account.getIssuingBankId().getId());

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    account.setId(rs.getLong("id"));
                    connection.commit();
                    logger.info("Account успешно вставлен с id = " + account.getId());
                    return account;
                } else {
                    connection.rollback();
                    logger.warning("Account не был вставлен (отсутствует сгенерированный id).");
                    return null;
                }

            } catch (SQLException e) {
                connection.rollback();
                logger.severe("Ошибка при вставке Account: " + e.getMessage());
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
    public boolean update(Account value) {
        try {
            connection.setAutoCommit(false);

//            // Проверка на null
//            if (value == null || value.getCurrencyId() == null || value.getIssuingBankId() == null) {
//                logger.warning("Account или его поля (currency/issuingBank) равны null.");
//                connection.rollback();
//                return false;
//            }
//
//            // Проверка существования валюты
//            if (currencyJDBCDaoImpl.findById(value.getCurrencyId().getId()).isEmpty()) {
//                logger.warning("Валюта с id = " + value.getCurrencyId().getId() + " не найдена.");
//                connection.rollback();
//                return false;
//            }
//
//            // Проверка существования банка
//            if (issuingBankJDBCDaoImpl.findById(value.getIssuingBankId().getId()).isEmpty()) {
//                logger.warning("IssuingBank с id = " + value.getIssuingBankId().getId() + " не найден.");
//                connection.rollback();
//                return false;
//            }

            String checkExistenceQuery = "SELECT COUNT(*) FROM account WHERE account_number = ? AND balance = ? AND currency_id = ? AND issuing_bank_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(checkExistenceQuery)) {
                preparedStatement.setString(1, value.getAccountNumber());
                preparedStatement.setBigDecimal(2, value.getBalance());
                preparedStatement.setLong(3, value.getCurrencyId().getId());
                preparedStatement.setLong(4, value.getIssuingBankId().getId());

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    logger.info("account с account: " + value.getAccountNumber() + " уже существует.");
                    // Если акаунт уже существует, возвращаем её
                    return false;
                }
            }

            try (PreparedStatement ps = connection.prepareStatement(UPDATE_ACCOUNT)) {
                ps.setString(1, value.getAccountNumber());
                ps.setBigDecimal(2, value.getBalance());
                ps.setLong(3, value.getCurrencyId().getId());
                ps.setLong(4, value.getIssuingBankId().getId());
                ps.setLong(5, value.getId());

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
        try (PreparedStatement ps = connection.prepareStatement(DELETE_ACCOUNT_BY_ID)) {
            ps.setLong(1, key);
            int deleted = ps.executeUpdate();
            if (deleted == 1) {
                logger.info("Удалён Account с id = " + key);
                return true;
            } else {
                logger.warning("Account с id = " + key + " не найден для удаления.");
                return false;
            }
        } catch (SQLException e) {
            logger.severe("Ошибка при удалении Account с id = " + key + ": " + e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Account> findById(Long key) {
        try (PreparedStatement ps = connection.prepareStatement(FIND_ACCOUNT_BY_ID2)) {
            ps.setLong(1, key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setId(rs.getLong("id"));
                account.setAccountNumber(rs.getString("account_number"));
                account.setBalance(rs.getBigDecimal("balance"));

                long currencyId = rs.getLong("currency_id");
                long bankId = rs.getLong("issuing_bank_id");

                currencyJDBCDaoImpl.findById(currencyId).ifPresent(currency -> account.setCurrencyId(currency));
                issuingBankJDBCDaoImpl.findById(bankId).ifPresent(bank -> account.setIssuingBankId(bank));

                logger.info("Найден Account с id = " + key);
                return Optional.of(account);
            } else {
                logger.warning("Account с id = " + key + " не найден.");
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.severe("Ошибка при поиске Account с id = " + key + ": " + e.getMessage());
            throw new DaoException(e);
        }
    }


    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL_ACCOUNTS2);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getLong("id"));
                account.setAccountNumber(rs.getString("account_number"));
                account.setBalance(rs.getBigDecimal("balance"));

                // Получаем объекты currency и issuingBank через DAO
                long currencyId = rs.getLong("currency_id");
                long bankId = rs.getLong("issuing_bank_id");

                currencyJDBCDaoImpl.findById(currencyId).ifPresent(currency -> account.setCurrencyId(currency));
                issuingBankJDBCDaoImpl.findById(bankId).ifPresent(bank -> account.setIssuingBankId(bank));

                accounts.add(account);
            }

            logger.info("Найдено аккаунтов: " + accounts.size());
            return accounts;

        } catch (SQLException e) {
            logger.severe("Ошибка при получении списка аккаунтов: " + e.getMessage());
            throw new DaoException(e);
        }
    }


    @Override
    public boolean createTableQuery(String sql) {
        return createTableService(CREATE_TABLE_ACCOUNT);
    }


    @Override
    public boolean deleteAll(String s) {
        return deleteAllService(s);
    }

    @Override
    public boolean dropTable(String tableName) {
        return dropTableService(tableName);
    }

    @Override
    public Optional<Account> findByValue(String cardNumber) {
        return Optional.empty();
    }

}
