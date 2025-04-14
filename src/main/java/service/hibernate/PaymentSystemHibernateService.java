package service.hibernate;

import dao.hibernate.PaymentSystemHibernateDaoImpl;
import model.PaymentSystem;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.ServiceInterface;
import util.HibernateConfig;

import java.util.List;
import java.util.Optional;

public class PaymentSystemHibernateService implements ServiceInterfaceHibernate<Long, PaymentSystem> {

    private final PaymentSystemHibernateDaoImpl paymentSystemHibernateDaoImpl;

    public PaymentSystemHibernateService(PaymentSystemHibernateDaoImpl paymentSystemHibernateDaoImpl) {
        this.paymentSystemHibernateDaoImpl = paymentSystemHibernateDaoImpl;
    }

    @Override
    public PaymentSystem create(PaymentSystem value) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            PaymentSystem saved = paymentSystemHibernateDaoImpl.insert(session, value);
            tx.commit();
            return saved;
        }
    }

    @Override
    public boolean update(PaymentSystem value) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = paymentSystemHibernateDaoImpl.update(session, value);
            tx.commit();
            return result;
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = paymentSystemHibernateDaoImpl.delete(session, id);
            tx.commit();
            return result;
        }
    }

    @Override
    public Optional<PaymentSystem> findById(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return paymentSystemHibernateDaoImpl.findById(session, id);
        }
    }

    @Override
    public List<PaymentSystem> findAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return paymentSystemHibernateDaoImpl.findAll(session);
        }
    }

    @Override
    public void createTable(String sql) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            paymentSystemHibernateDaoImpl.createTableQuery(session, sql);
            tx.commit();
        }
    }

    @Override
    public boolean deleteAll(String tableName) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = paymentSystemHibernateDaoImpl.deleteAll(session, tableName);
            tx.commit();
            return result;
        }
    }

    @Override
    public boolean dropTable(String tableName) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = paymentSystemHibernateDaoImpl.dropTable(session, tableName);
            tx.commit();
            return result;
        }
    }
}
