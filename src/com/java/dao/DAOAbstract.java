package com.java.dao;

import com.java.dao.jdbc.AcquiringBankJDBCDaoImpl;
import com.java.exception.DaoException;
import com.java.util.ConnectionManager2;

import java.sql.*;
import java.util.logging.Logger;

public abstract class DAOAbstract {
    private static final Logger logger = Logger.getLogger(DAOAbstract.class.getName());
    protected final Connection connection;

    public DAOAbstract(Connection connection) {
        this.connection = connection;
    }


    /**
     * Проверяет, существует ли таблица в базе данных
     */
    public static boolean isTableExists(Connection connection, String tableName) {
        String sql = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_schema = 'processingcenterschema' AND table_name = ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, tableName);
            try (var resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() && resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            logger.severe("Ошибка при проверке таблицы: " + e.getMessage());
            throw new DaoException(e);
        }
    }

    /**
     * Удаляет все записи из таблицы
     */
    public boolean deleteAll(String tableName) {
        String sql = "DELETE FROM " + tableName + ";";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            logger.severe("Ошибка при удалении всех записей из таблицы " + tableName + ": " + e.getMessage());
            throw new DaoException(e);
        }
    }

    /**
     * Удаляет таблицу
     */
    public boolean dropTable(String tableName) {
        String sql = "DROP TABLE IF EXISTS " + tableName + " CASCADE;";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            logger.severe("Ошибка при удалении таблицы " + tableName + ": " + e.getMessage());
            throw new DaoException(e);
        }
    }


}

