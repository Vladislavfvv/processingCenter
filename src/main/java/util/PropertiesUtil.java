package util;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        System.out.println("Загрузка application.properties...");
       // InputStream inputStream = PropertiesUtil2.class.getClassLoader().getResourceAsStream("db.properties");
        //System.out.println("inputStream = " + inputStream);
        loadProperties();
    }

    private PropertiesUtil() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key); //для возврата по ключу проперти
    }

    private static void loadProperties() {
        try(var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties"))
        {
            PROPERTIES.load(inputStream);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
