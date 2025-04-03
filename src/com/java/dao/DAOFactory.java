package com.java.dao;

import com.java.dao.jdbc.*;
import com.java.model.*;

import java.sql.Connection;

public class DAOFactory {
    // private static final Connection connection = ConnectionManager2.open();

//    // Метод для получения DAO для Card
//    public static DAOInterface<Card> getCardDAO() {
//        return new CardJDBCDaoImpl(connection);
//    }
//
//    // Метод для получения DAO для AcquiringBank
//    public static DAOInterface<AcquiringBank> getAcquiringBankDAO() {
//        return new AcquiringBankJDBCDaoImpl(connection);
//    }
//
//    // Метод для получения DAO для Account
//    public static DAOInterface<Account> getAccountDAO() {
//        return new AccountJDBCDaoImpl(connection);
//    }
//
//
//    public static void closeConnection() {
//        ConnectionManager2.close(connection);
//    }
//    // Здесь можно добавить другие DAO, например для User, Transaction и т.д.


    //DAOFactory всегда получает соединение извне и не хранит его внутри.
    // Метод для получения DAO для Card
    public static DAOInterface<Long, Card> getCardDAO(Connection connection) {
        return new CardJDBCDaoImpl(connection);
    }

    // Метод для получения DAO для AcquiringBank
    public static DAOInterface<Long, AcquiringBank> getAcquiringBankDAO(Connection connection) {
        return new AcquiringBankJDBCDaoImpl(connection);
    }

    // Метод для получения DAO для AcquiringBank
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


    // Метод для получения DAO для Account
//    public static DAOInterface<Account> getAccountDAO(Connection connection) {
//        return new AccountJDBCDaoImpl(connection);
//    }

}
