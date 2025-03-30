package com.java.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public final class ConnectionManager2 {
    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static final String DRIVER_KEY = "db.driver";
    private static final Queue<Connection> connectionPool = new LinkedList<>();
    //private static final int MAX_POOL_SIZE = Integer.parseInt("pool.size"); // Ограничение на количество соединений в пуле
    private static final int MAX_POOL_SIZE; // Ограничение на количество соединений в пуле

    static {
        try {
            MAX_POOL_SIZE = Integer.parseInt(PropertiesUtil2.get("pool.size"));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Ошибка парсинга pool.size. Убедитесь, что значение является числом.", e);
        }
        loadDriver();
        initializePool();
    }

    private ConnectionManager2() {
    }

//    public static Connection open() {
//        try {
//            return DriverManager.getConnection(
//                    PropertiesUtil2.get(URL_KEY),
//                    PropertiesUtil2.get(USER_KEY),
//                    PropertiesUtil2.get(PASSWORD_KEY));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private static Connection createConnection() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil2.get(URL_KEY),
                    PropertiesUtil2.get(USER_KEY),
                    PropertiesUtil2.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании соединения", e);
        }
    }

    // Инициализация пула соединений
    private static void initializePool() {
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            connectionPool.add(createConnection());
        }
    }

    // Открытие соединения из пула
    public static Connection open() {
        if (connectionPool.isEmpty()) {
            throw new RuntimeException("Все соединения заняты. Попробуйте позже.");
        }
        return connectionPool.poll();
    }

    // Закрытие соединения и возвращение в пул
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                connectionPool.offer(connection); // Возвращаем соединение в пул
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при закрытии соединения", e);
            }
        }
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
         //   Class.forName(PropertiesUtil.get(DRIVER_KEY));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
