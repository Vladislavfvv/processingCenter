package dao.spring;


import dao.DaoInterfaceSpring;
import lombok.extern.slf4j.Slf4j;
import model.Currency;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Repository
public class CurrencySpringDaoImpl implements DaoInterfaceSpring<Long, Currency> {
    @PersistenceContext
    private EntityManager em;


    public Currency insert(Currency currency) {
       em.persist(currency);
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
            return true;
        }
        return false;
    }

    public List<Currency> findAll() {
        return em.createQuery("FROM Currency", Currency.class).getResultList();
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
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteAll() {
        try {
            em.createQuery("DELETE FROM Currency").executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean dropTable() {
        try {
            em.createNativeQuery("DROP TABLE IF EXISTS Currency").executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
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

    // public Optional<Currency> findByValue(String name) {
    //        return currencies.stream()
    //                .filter(c -> c.getCurrencyName().equals(name))
    //                .findFirst();
    //    }

    public Optional<Currency> findById(Long id) {
        return Optional.ofNullable(em.find(Currency.class, id));
    }


    }


