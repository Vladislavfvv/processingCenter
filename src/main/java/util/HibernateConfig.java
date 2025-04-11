package util;

import model.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


import java.util.Properties;

public class HibernateConfig {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();

            // Настройки подключения
            Properties settings = new Properties();
            settings.put("hibernate.connection.driver_class", "org.postgresql.Driver");
            settings.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/processingcenter");
            settings.put("hibernate.connection.username", "lesson");
            settings.put("hibernate.connection.password", "lesson");
            settings.put("hibernate.default_schema", "public");

            // Другие настройки
            settings.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            settings.put("hibernate.show_sql", "true");
            settings.put("hibernate.format_sql", "true");
            settings.put("hibernate.hbm2ddl.auto", "update"); // или create / validate / none

            configuration.setProperties(settings);

            // Регистрируем Entity
            configuration.addAnnotatedClass(Account.class);
            configuration.addAnnotatedClass(AcquiringBank.class);
            configuration.addAnnotatedClass(Card.class);
            configuration.addAnnotatedClass(CardStatus.class);
            configuration.addAnnotatedClass(Currency.class);
            configuration.addAnnotatedClass(IssuingBank.class);
            configuration.addAnnotatedClass(MerchantCategoryCode.class);
            configuration.addAnnotatedClass(PaymentSystem.class);
            configuration.addAnnotatedClass(ResponseCode.class);
            configuration.addAnnotatedClass(SalesPoint.class);
            configuration.addAnnotatedClass(Terminal.class);
            configuration.addAnnotatedClass(Transaction.class);
            configuration.addAnnotatedClass(TransactionType.class);
            configuration.addAnnotatedClass(User.class);

            StandardServiceRegistryBuilder builder =
                    new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());

            sessionFactory = configuration.buildSessionFactory(builder.build());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка конфигурации Hibernate: " + e.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
