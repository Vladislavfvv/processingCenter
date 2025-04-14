package service.hibernate;

import dao.DAOInterface;
import dao.hibernate.CurrencyHibernateDaoImpl;
import lombok.extern.slf4j.Slf4j;
import model.Currency;
import org.hibernate.Session;

import org.hibernate.Transaction;
import util.HibernateConfig;

import java.util.List;
import java.util.Optional;

@Slf4j
public class CurrencyHibernateService implements ServiceInterfaceHibernate<Long, Currency> {

    private final CurrencyHibernateDaoImpl currencyHibernateDao;

public CurrencyHibernateService(CurrencyHibernateDaoImpl currencyHibernateDao) {
    this.currencyHibernateDao = currencyHibernateDao;
}

    @Override
    public Currency create(Currency value) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Currency saved = currencyHibernateDao.insert(session, value);
            tx.commit();
            return saved;
        }
    }

    @Override
    public boolean update(Currency value) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = currencyHibernateDao.update(session, value);
            tx.commit();
            return result;
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = currencyHibernateDao.delete(session, id);
            tx.commit();
            return result;
        }
    }

    @Override
    public Optional<Currency> findById(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return currencyHibernateDao.findById(session, id);
        }
    }

    @Override
    public List<Currency> findAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return currencyHibernateDao.findAll(session);
        }
    }

    @Override
    public void createTable(String sql) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            currencyHibernateDao.createTableQuery(session, sql);
            tx.commit();
        }
    }

    @Override
    public boolean deleteAll(String tableName) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = currencyHibernateDao.deleteAll(session, tableName);
            tx.commit();
            return result;
        }
    }

    @Override
    public boolean dropTable(String tableName) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            boolean result = currencyHibernateDao.dropTable(session, tableName);
            tx.commit();
            return result;
        }
    }

//    @Override
//    public Currency insert(Currency currency) {
//        Transaction transaction = null;
//        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            Currency saved = currencyHibernateDao.insert(session, currency);
//            transaction.commit();
//            return saved;
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            log.error("Ошибка при сохранении Currency", e);
//            throw e;
//        }
//    }
//
//    @Override
//    public Currency create(Currency value) {
//        return null;
//    }
//
//    @Override
//    public boolean update(Currency currency) {
//        Transaction transaction = null;
//        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            boolean result = currencyHibernateDao.update(session, currency);
//            transaction.commit();
//            return result;
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            log.error("Ошибка при обновлении Currency", e);
//            return false;
//        }
//    }
//
//    @Override
//    public boolean delete(Long id) {
//        Transaction transaction = null;
//        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            boolean result = currencyHibernateDao.deleteById(session, id);
//            transaction.commit();
//            return result;
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            log.error("Ошибка при удалении Currency по id = {}", id, e);
//            return false;
//        }
//    }
//
//    @Override
//    public Optional<Currency> findById(Long id) {
//        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
//            return currencyHibernateDao.findById(session, id);
//        } catch (Exception e) {
//            log.error("Ошибка при поиске Currency по id = {}", id, e);
//            return Optional.empty();
//        }
//    }
//
//    @Override
//    public List<Currency> findAll() {
//        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
//            return currencyHibernateDao.findAll(session);
//        } catch (Exception e) {
//            log.error("Ошибка при получении списка всех Currency", e);
//            return List.of();
//        }
//    }
//
//    @Override
//    public void createTable(String tableName) {
//
//    }
//
//    @Override
//    public boolean createTableQuery(String sql) {
//        Transaction transaction = null;
//        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            boolean result = currencyHibernateDao.createTableQuery(session, sql);
//            transaction.commit();
//            return result;
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            log.error("Ошибка при выполнении createTableQuery", e);
//            return false;
//        }
//    }
//
//    @Override
//    public boolean deleteAll(String tableName) {
//        Transaction transaction = null;
//        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            boolean result = currencyHibernateDao.deleteAll(session, tableName);
//            transaction.commit();
//            return result;
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            log.error("Ошибка при удалении всех записей из таблицы {}", tableName, e);
//            return false;
//        }
//    }
//
//    @Override
//    public boolean dropTable(String tableName) {
//        Transaction transaction = null;
//        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            boolean result = currencyHibernateDao.dropTable(session, tableName);
//            transaction.commit();
//            return result;
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            log.error("Ошибка при удалении таблицы {}", tableName, e);
//            return false;
//        }
//    }
//
//    @Override
//    public Optional<Currency> findByValue(String value) {
//        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
//            return currencyHibernateDao.findByValue(session, value);
//        } catch (Exception e) {
//            log.error("Ошибка при поиске Currency по value = {}", value, e);
//            return Optional.empty();
//        }
//    }


//    @Override
//    public Currency insert(Currency value) {
//        return currencyHibernateDao.insert(value);
//    }

//    @Override
//    public boolean update(Currency value) {
//        return currencyHibernateDao.update(value);
//    }
//
//    @Override
//    public boolean delete(Long id) {
//        return currencyHibernateDao.delete(id);
//    }
//
//    @Override
//    public Optional<Currency> findById(Long id) {
//        return currencyHibernateDao.findById(id);
//    }
//
//    @Override
//    public List<Currency> findAll() {
//        return currencyHibernateDao.findAll();
//    }
//
//    @Override
//    public boolean createTableQuery(String sql) {
//        return currencyHibernateDao.createTableQuery(sql);
//    }
//
//    @Override
//    public boolean deleteAll(String s) {
//        return currencyHibernateDao.deleteAll(s);
//    }
//
//    @Override
//    public boolean dropTable(String s) {
//        return currencyHibernateDao.dropTable(s);
//    }
//
//    @Override
//    public Optional<Currency> findByValue(String value) {
//        return currencyHibernateDao.findByValue(value);
//    }
}
