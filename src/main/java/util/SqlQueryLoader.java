package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SqlQueryLoader {
    private static final Properties queries = new Properties();

    static {
        try (InputStream input = SqlQueryLoader.class.getClassLoader().getResourceAsStream("sql/sql-queries.properties")) {
            if (input == null) {
                throw new RuntimeException("Файл sql-queries.properties не найден в resources");
            }
            queries.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки SQL запросов", e);
        }
    }

    public static String getQuery(String key) {
        return queries.getProperty(key);
    }
}
