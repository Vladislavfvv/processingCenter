package util.jdbc;

import dao.DaoInterfaceSpring;
import dao.spring.CurrencySpringDaoImpl;
import model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//import javax.persistence.EntityManagerFactory;//для старых версии Hibernate 5 / Spring 5
import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {"dao", "service.spring"})
@PropertySource("classpath:/applicationSpring.properties")
@EnableTransactionManagement
public class AppConfig {

    @Autowired
    private Environment env;

    // Бин для DataSource
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));

        createSchemaIfNotExists(dataSource);
        return dataSource;
    }

    // Метод для создания схемы если её нет
    private void createSchemaIfNotExists(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String schemaName = env.getProperty("spring.jpa.properties.hibernate.default_schema");
            String sql = "CREATE SCHEMA IF NOT EXISTS " + schemaName;
            statement.execute(sql);
      //      System.out.println(" Схема '" + schemaName + "' создана или уже существует.");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании схемы: " + e.getMessage(), e);
        }
    }


    // Бин для EntityManagerFactory
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("model");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.properties.hibernate.hbm2ddl.auto"));
        props.setProperty("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        props.setProperty("hibernate.format_sql", "true");
        props.setProperty("hibernate.use_sql_comments", "true");
        props.setProperty("hibernate.show_sql", env.getProperty("spring.jpa.properties.hibernate.show_sql"));
        props.setProperty("hibernate.default_schema", env.getProperty("spring.jpa.properties.hibernate.default_schema"));

        em.setJpaProperties(props);
        return em;
    }

    // Бин для Dao интерфейса
    @Bean
    public DaoInterfaceSpring<Long, Currency> currencyDao() {
        return new CurrencySpringDaoImpl(); // Конкретная реализация
    }

    // Бин для транзакционного менеджера
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }










//    @Autowired
//    private Environment env;
//
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
////        dataSource.setDriverClassName("org.postgresql.Driver");
////        dataSource.setUrl("jdbc:postgresql://localhost:5435/processingcenter");
////        dataSource.setUsername("lesson");
////        dataSource.setPassword("lesson");
////        return dataSource;
//        dataSource.setDriverClassName(env.getProperty("db.driver"));
//        dataSource.setUrl(env.getProperty("db.url"));
//        dataSource.setUsername(env.getProperty("db.user"));
//        dataSource.setPassword(env.getProperty("db.password"));
//        return dataSource;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource);
//        em.setPackagesToScan("model");
//
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//
//        Properties props = new Properties();
//        props.setProperty("hibernate.hbm2ddl.auto", "update"); // create | update | validate | none
//        props.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        props.setProperty("hibernate.format_sql", "true");
//        props.setProperty("hibernate.use_sql_comments", "true");
//        // props.setProperty("hibernate.show_sql", "true");
//
//        em.setJpaProperties(props);
//        return em;
//    }
//
//    @Bean
//    public DaoInterfaceSpring<Long, Currency> currencyDao() {
//        return new CurrencySpringDaoImpl(); // твоя конкретная реализация
//    }
//
//    @Bean
//    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
//        return new JpaTransactionManager(emf);
//    }
//
}
