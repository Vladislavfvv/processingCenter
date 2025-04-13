package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.InputStream;
import java.util.Map;

public class YamlSqlLoader {
    private static final Map<String, String> queries;

    static {
        try (InputStream input = YamlSqlLoader.class.getClassLoader().getResourceAsStream("sql/sql-queries.yaml")) {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            Map<String, Map<String, String>> data = mapper.readValue(input, Map.class);
            queries = data.get("queries");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки SQL из YAML", e);
        }
    }

    public static String getQuery(String key) {
        return queries.get(key);
    }

    public static String getQuery(String key, Map<String, String> params) {
        String query = queries.get(key);
        for (var entry : params.entrySet()) {
            query = query.replace(":" + entry.getKey(), entry.getValue());
        }
        return query;
    }
}
