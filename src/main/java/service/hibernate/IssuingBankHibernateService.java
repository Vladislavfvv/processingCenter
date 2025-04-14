package service.hibernate;

import dao.hibernate.IssuingBankHibernateDaoImpl;
import model.IssuingBank;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateConfig;

import java.util.List;
import java.util.Optional;

public class IssuingBankHibernateService implements ServiceInterfaceHibernate<Long, IssuingBank> {

    private final IssuingBankHibernateDaoImpl issuingBankHibernateDao;

    public IssuingBankHibernateService(IssuingBankHibernateDaoImpl issuingBankHibernateDao) {
        this.issuingBankHibernateDao = issuingBankHibernateDao;
    }

    @Override
    public IssuingBank create(IssuingBank value) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            IssuingBank saved = issuingBankHibernateDao.insert(session, value);
            tx.commit();
            return saved;
        }
    }

    @Override
    public boolean update(IssuingBank value) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = issuingBankHibernateDao.update(session, value);
            tx.commit();
            return result;
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = issuingBankHibernateDao.delete(session, id);
            tx.commit();
            return result;
        }
    }

    @Override
    public Optional<IssuingBank> findById(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return issuingBankHibernateDao.findById(session, id);
        }
    }

    @Override
    public List<IssuingBank> findAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return issuingBankHibernateDao.findAll(session);
        }
    }

    @Override
    public void createTable(String sql) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            issuingBankHibernateDao.createTableQuery(session, sql);
            tx.commit();
        }
    }

    @Override
    public boolean deleteAll(String tableName) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = issuingBankHibernateDao.deleteAll(session, tableName);
            tx.commit();
            return result;
        }
    }

    @Override
    public boolean dropTable(String tableName) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = issuingBankHibernateDao.dropTable(session, tableName);
            tx.commit();
            return result;
        }
    }

}
