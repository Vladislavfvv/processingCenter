package com.edme.pro.dao;


import com.edme.pro.dto.CurrencyDto;
import com.edme.pro.model.CardStatus;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import com.edme.pro.model.Currency;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Transactional
@Repository
public class CurrencySpringDaoImpl  implements DaoInterfaceSpring<Long, Currency> {
    @PersistenceContext
    private EntityManager em;

    void setEntityManager(EntityManager em) {
        this.em = em;
    }


    public Currency insert(Currency currency) {
       em.persist(currency);
       em.flush();
       log.info("Inserted currency: " + currency);
       return currency;
    }


    @Override
    public Currency update(Currency value) {
        return em.merge(value);
    }

    public boolean delete(Long id) {
        Currency currency = em.find(Currency.class, id);
        if (currency != null) {
            em.remove(currency);
            log.info("Deleted Currency: " + currency);
            return true;
        }
        log.info("Not deleted Currency: " + id);
        return false;
    }

    public List<Currency> findAll() {

        //return em.createQuery("FROM Currency", Currency.class).getResultList();
        return em.createQuery("SELECT c FROM Currency c", Currency.class).getResultList();
    }

    @Override
    public boolean createTable() {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS processingcenterschema.currency (\n" +
                           "                id                    bigserial PRIMARY KEY,\n" +
                           "                currency_digital_code varchar(3)   NOT NULL,\n" +
                           "                currency_letter_code  varchar(3)   NOT NULL,\n" +
                           "                currency_name         varchar(255) NOT NULL,\n" +
                           "                CONSTRAINT unique_currency_fields\n" +
                           "                UNIQUE (currency_digital_code, currency_letter_code, currency_name)\n" +
                           "        );";

        try {
            em.createNativeQuery(sqlCreate).executeUpdate();
            log.info("Table {} created", CurrencySpringDaoImpl.class.getName());
            return true;
        } catch (Exception e) {
           log.info("Table {} not created", CurrencySpringDaoImpl.class.getName());
            return false;
        }
    }

    @Override
    public boolean deleteAll() {
        try {
            Query query = em.createNativeQuery("DELETE FROM processingcenterschema.currency");
            log.info("Table {} cleared", Currency.class.getSimpleName());
            int result = query.executeUpdate();
            return result > 0;  // return true if records were deleted, otherwise false
        } catch (Exception e) {
            log.info("Table {} not cleared", Currency.class.getSimpleName());
            return false;
        }
    }

    @Override
    public boolean dropTable() {
        try {
           // em.createNativeQuery("DROP TABLE IF EXISTS Currency").executeUpdate();
            Query query =  em.createNativeQuery("DROP TABLE IF EXISTS processingcenterschema.currency CASCADE");
           log.info("Table {} dropped", CurrencySpringDaoImpl.class.getName());
            int result = query.executeUpdate();
            return result > 0;  // return true if records were deleted, otherwise false
        } catch (Exception e) {
           log.info("Table {} not dropped", CurrencySpringDaoImpl.class.getName());
            return false;
        }
    }

    //метод сравнения объектов
    protected List<Currency> findMatching(Currency currency) {
        return em.createQuery(
                        "SELECT c FROM Currency c WHERE c.currencyName = :currencyName AND c.currencyDigitalCode = :currencyDigitalCode AND c.currencyLetterCode = :currencyLetterCode", Currency.class)
                .setParameter("currencyName", currency.getCurrencyName())
                .setParameter("currencyDigitalCode", currency.getCurrencyDigitalCode())
                .setParameter("currencyLetterCode", currency.getCurrencyLetterCode())
                .getResultList();
    }

    @Override
    public Optional<Currency> findByValue(String value) {
        List<Currency> result = em.createQuery(
                        "FROM Currency c WHERE c.currencyName = :value", Currency.class)
                .setParameter("value", value)
                .getResultList();

        return result.stream().findFirst();
    }

    public Optional<Currency> findById(Long id) {
        return Optional.ofNullable(em.find(Currency.class, id));
    }

    @Override
    public boolean insertDefaultValues() {
        String sql = """
        INSERT INTO processingcenterschema.currency(currency_digital_code, currency_letter_code, currency_name)
                               VALUES ('643', 'RUB', 'Russian Ruble'),
                                      ('980', 'UAN', 'Hryvnia'),
                                      ('840', 'USD', 'US Dollar'),
                                      ('978', 'EUR', 'Euro'),
                                      ('392', 'JPY', 'Yen'),
                                      ('156', 'CNY', 'Yuan Renminbi'),
                                      ('826', 'GBP', 'Pound Sterling'),
                                       ('899', 'GDR', 'New Sterling'),
                                       ('911', 'BSS', 'New SpecCurrency')
        ON CONFLICT (currency_digital_code, currency_letter_code, currency_name) DO NOTHING;
        """;
        try {
            em.createNativeQuery(sql).executeUpdate();
            log.info("Default currencies inserted");
            return true;
        } catch (Exception e) {
            log.error("Failed to insert default currencies", e);
            return false;
        }
    }
}


