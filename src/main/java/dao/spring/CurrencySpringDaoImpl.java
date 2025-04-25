package dao.spring;


import dao.DaoInterfaceSpring;
import lombok.extern.slf4j.Slf4j;
import model.Currency;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
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
        String sqlCreate = "CREATE TABLE IF NOT EXISTS processingCenterSchema.currency\n" +
                "(\n" +
                "    id                    bigserial primary key,\n" +
                "    currency_digital_code varchar(3)   not null,\n" +
                "    currency_letter_code  varchar(3)   not null,\n" +
                "    currency_name         varchar(255) not null\n" +
                "    );";
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
            em.createQuery("DELETE FROM Currency").executeUpdate();
            log.info("Table {} cleared", Currency.class.getSimpleName());
            return true;
        } catch (Exception e) {
           log.info("Table {} not cleared", Currency.class.getSimpleName());
            return false;
        }
    }

    @Override
    public boolean dropTable() {
        try {
           // em.createNativeQuery("DROP TABLE IF EXISTS Currency").executeUpdate();
            em.createNativeQuery("DROP TABLE IF EXISTS processingcenterschema.currency CASCADE ").executeUpdate();
           log.info("Table {} dropped", CurrencySpringDaoImpl.class.getName());
            return true;
        } catch (Exception e) {
           log.info("Table {} not dropped", CurrencySpringDaoImpl.class.getName());
            return false;
        }
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



    // public Optional<Currency> findByValue(String name) {
    //        return currencies.stream()
    //                .filter(c -> c.getCurrencyName().equals(name))
    //                .findFirst();
    //    }

}


