package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public final class ConnectionManager {
    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static final String DRIVER_KEY = "db.driver";
    private static final Queue<Connection> connectionPool = new LinkedList<>();
    private static final int MAX_POOL_SIZE = Integer.parseInt("pool.size"); // Ограничение на количество соединений в пуле


    static {
        loadDriver();
       // initializePool();
    }

    private ConnectionManager() {
    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USER_KEY),
                    PropertiesUtil.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadDriver() {
        try {
//            Class.forName("org.postgresql.Driver");
            Class.forName(PropertiesUtil.get(DRIVER_KEY));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
