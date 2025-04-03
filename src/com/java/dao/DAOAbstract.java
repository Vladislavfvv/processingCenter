package com.java.dao;

import com.java.exception.DaoException;
import com.java.util.ConnectionManager2;

import java.sql.*;
import java.util.logging.Logger;

public class DAOAbstract {
    private static final Logger logger = Logger.getLogger(DAOAbstract.class.getName());
    protected static Connection connection = null;

    public DAOAbstract(Connection connection) {
        DAOAbstract.connection = ConnectionManager2.open();
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
    public static boolean deleteAllService(String tableName) {
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
    public static boolean dropTableService(String tableName) {
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

