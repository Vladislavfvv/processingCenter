package service.hibernate;

import dao.hibernate.AccountHibernateDaoImpl;
import model.Account;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateConfig;

import java.util.List;
import java.util.Optional;

public class AccountHibernateService implements ServiceInterfaceHibernate<Long, Account> {

    private final AccountHibernateDaoImpl accountHibernateDao;

    public AccountHibernateService(AccountHibernateDaoImpl accountHibernateDao) {
        this.accountHibernateDao = accountHibernateDao;
    }

    @Override
    public Account create(Account value) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Account saved = accountHibernateDao.insert(session, value);
            tx.commit();
            return saved;
        }
    }

    @Override
    public boolean update(Account value) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = accountHibernateDao.update(session, value);
            tx.commit();
            return result;
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = accountHibernateDao.delete(session, id);
            tx.commit();
            return result;
        }
    }

    @Override
    public Optional<Account> findById(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return accountHibernateDao.findById(session, id);
        }
    }

    @Override
    public List<Account> findAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return accountHibernateDao.findAll(session);
        }
    }

    @Override
    public void createTable(String sql) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            accountHibernateDao.createTableQuery(session, sql);
            tx.commit();
        }
    }


    @Override
    public boolean deleteAll(String tableName) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = accountHibernateDao.deleteAll(session, tableName);
            tx.commit();
            return result;
        }
    }

    @Override
    public boolean dropTable(String tableName) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = accountHibernateDao.dropTable(session, tableName);
            tx.commit();
            return result;
        }
    }

}
