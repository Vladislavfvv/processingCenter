package com.edme.pro.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import com.edme.pro.model.PaymentSystem;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Repository
public class PaymentSystemSpringDaoImpl implements DaoInterfaceSpring<Long, PaymentSystem> {


    @PersistenceContext
    private EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public PaymentSystem insert(PaymentSystem value) {
        em.persist(value);
        em.flush();
        log.info("Inserted " + value);
        return value;
    }

    @Override
    public PaymentSystem update(PaymentSystem value) {
        return em.merge(value);
    }

    @Override
    public boolean delete(Long id) {
        PaymentSystem paymentSystem = em.find(PaymentSystem.class, id);
        if (paymentSystem != null) {
            em.remove(paymentSystem);
            log.info("Deleted " + paymentSystem);
            return true;
        }
        log.info("Not deleted " + id);
        return false;
    }

    @Override
    public Optional<PaymentSystem> findById(Long id) {

        return Optional.ofNullable(em.find(PaymentSystem.class, id));
    }

    @Override
    public List<PaymentSystem> findAll() {
        return em.createQuery("select p from PaymentSystem p", PaymentSystem.class).getResultList();
    }

    @Override
    public boolean createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS processingcenterschema.payment_system
                   (
                       id                  bigserial primary key,
                       payment_system_name varchar(50) UNIQUE not null
                       );
                """;
        try {
            em.createNativeQuery(sql).executeUpdate();
            log.info("Table created");
            return true;
        } catch (Exception e) {
           log.info("Table not created");
            return false;
        }
    }

    @Override
    public boolean deleteAll() {
        try {
            em.createQuery("DELETE FROM PaymentSystem").executeUpdate();
            log.info("Tables PaymentSystem cleared");
            return true;
        } catch (Exception e) {
            log.error("Tables PaymentSystem not cleared");
            return false;
        }
    }

    @Override
    public boolean dropTable() {
        try {
            em.createNativeQuery("DROP TABLE IF EXISTS processingcenterschema.payment_system CASCADE").executeUpdate();
            log.info("Tables dropped");
            return true;
        } catch (Exception e) {
            log.info("Tables not dropped");
            return false;
        }
    }

    @Override
    public Optional<PaymentSystem> findByValue(String name) {
        List<PaymentSystem> paymentSystem = em.
                createQuery("FROM PaymentSystem pm WHERE pm.paymentSystemName = :name", PaymentSystem.class)
                .setParameter("name", name)
                .getResultList();
        return paymentSystem.stream().findFirst();
    }

    @Override
    public boolean insertDefaultValues() {
        String sql = """
        INSERT INTO processingcenterschema.payment_system (payment_system_name)
        VALUES 
            ('VISA International Service Association'),
            ('Mastercard'),
            ('JCB'),
            ('American Express'),
            ('Diners Club International'),
            ('China UnionPay ')
        ON CONFLICT (payment_system_name) DO NOTHING;
        """;
        try {
            em.createNativeQuery(sql).executeUpdate();
            log.info("Default payment systems inserted");
            return true;
        } catch (Exception e) {
            log.error("Failed to insert default payment systems", e);
            return false;
        }
    }

}
