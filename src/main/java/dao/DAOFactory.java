package dao;

import dao.jdbc.*;
import model.*;
import model.Currency;
import util.ConnectionManager;

import java.sql.Connection;

//DAOFactory всегда получает соединение извне и не хранит его внутри.

public class DAOFactory {
     private static final Connection connection = ConnectionManager.open();


    public static DAOInterface<Long, Card> getCardDAO(Connection connection) {
        return new CardJDBCDaoImpl(connection);
    }


    public static DAOInterface<Long, AcquiringBank> getAcquiringBankDAO(Connection connection) {
        return new AcquiringBankJDBCDaoImpl(connection);
    }

    public static DAOInterface<Long, SalesPoint> getSalesPointDAO(Connection connection) {
        return new SalesPointJDBCDaoImpl(connection);
    }

    public static DAOInterface<Long, CardStatus> getCardStatusDAO(Connection connection) {
        return new CardStatusJDBCDaoImpl(connection);
    }

    public static DAOInterface<Long, ResponseCode> getResponseCodeDAO(Connection connection) {
        return new ResponseCodeJDBCImpl(connection);
    }

    public static DAOInterface<Long, PaymentSystem> getPaymentSystemDAO(Connection connection) {
        return new PaymentSystemJDBCDaoImpl(connection);
    }

    public static DAOInterface<Long, Currency> getCurrencyDAO(Connection connection) {
        return new CurrencyJDBCDaoImpl(connection);
    }

    public static DAOInterface<Long, MerchantCategoryCode> getMerchantCategoryCodeDAO(Connection connection) {
        return new MerchantCategoryCodeJDBCImpl(connection);
    }

    public static DAOInterface<Long, Terminal> getTerminalDAO(Connection connection) {
        return new TerminalJDBCDaoImpl(connection);
    }

    public static DAOInterface<Long, TransactionType> getTransactionTypeDAO(Connection connection) {
        return new TransactionTypeJDBCDaoImpl(connection);
    }

    public static DAOInterface<Long, IssuingBank> getIssuingBankDAO(Connection connection) {
        return new IssuingBankJDBCDaoImpl(connection);
    }


    public static DAOInterface<Long, Account> getAccountDAO(Connection connection) {
        return new AccountJDBCDaoImpl(connection);
    }

    public static DAOInterface<Long, Transaction> getTransactionDAO(Connection connection) {
        return new TransactionJDBCDaoImpl(connection);
    }

        public static void closeConnection() {
        ConnectionManager.close(connection);
    }
}
