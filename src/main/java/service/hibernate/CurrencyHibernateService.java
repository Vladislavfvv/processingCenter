package service.hibernate;

import dao.DAOInterface;
import dao.hibernate.CurrencyHibernateDaoImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Currency;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateConfig;

import java.util.List;
import java.util.Optional;

@Slf4j
public class CurrencyHibernateService implements DAOInterface<Long, Currency> {

    private final CurrencyHibernateDaoImpl currencyHibernateDao;

public CurrencyHibernateService(CurrencyHibernateDaoImpl currencyHibernateDao) {
    this.currencyHibernateDao = currencyHibernateDao;
}

    @Override
    public Currency insert(Currency currency) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Currency saved = currencyHibernateDao.insert(session, currency);
            transaction.commit();
            return saved;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Ошибка при сохранении Currency", e);
            throw e;
        }
    }

    @Override
    public boolean update(Currency currency) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            boolean result = currencyHibernateDao.update(session, currency);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Ошибка при обновлении Currency", e);
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            boolean result = currencyHibernateDao.deleteById(session, id);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Ошибка при удалении Currency по id = {}", id, e);
            return false;
        }
    }

    @Override
    public Optional<Currency> findById(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return currencyHibernateDao.findById(session, id);
        } catch (Exception e) {
            log.error("Ошибка при поиске Currency по id = {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<Currency> findAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return currencyHibernateDao.findAll(session);
        } catch (Exception e) {
            log.error("Ошибка при получении списка всех Currency", e);
            return List.of();
        }
    }

    @Override
    public boolean createTableQuery(String sql) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            boolean result = currencyHibernateDao.createTableQuery(session, sql);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Ошибка при выполнении createTableQuery", e);
            return false;
        }
    }

    @Override
    public boolean deleteAll(String tableName) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            boolean result = currencyHibernateDao.deleteAll(session, tableName);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Ошибка при удалении всех записей из таблицы {}", tableName, e);
            return false;
        }
    }

    @Override
    public boolean dropTable(String tableName) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            boolean result = currencyHibernateDao.dropTable(session, tableName);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Ошибка при удалении таблицы {}", tableName, e);
            return false;
        }
    }

    @Override
    public Optional<Currency> findByValue(String value) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return currencyHibernateDao.findByValue(session, value);
        } catch (Exception e) {
            log.error("Ошибка при поиске Currency по value = {}", value, e);
            return Optional.empty();
        }
    }


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
