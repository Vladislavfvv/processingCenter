package dao.hibernate;

import dao.DAOInterface;
import exception.DaoException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Currency;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateConfig;

import java.util.List;
import java.util.Optional;

@Slf4j

public class CurrencyHibernateDaoImpl extends AbstractHibernateDao<Long, Currency> {

    public CurrencyHibernateDaoImpl() {
        super(Currency.class);
    }

    @Override
    public Currency insert(Session session, Currency entity) {
        session.save(entity);
        return entity;
    }

    @Override
    public boolean update(Session session, Currency entity) {
        session.update(entity);
        return true;
    }

    @Override
    public boolean delete(Session session, Long id) {
        Currency entity = session.get(Currency.class, id);
        if (entity != null) {
            session.delete(entity);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Currency> findById(Session session, Long id) {
        return Optional.ofNullable(session.get(Currency.class, id));
    }

    @Override
    public List<Currency> findAll(Session session) {
        Query<Currency> query = session.createQuery("FROM Currency", Currency.class);
        return query.getResultList();
    }

    @Override
    public boolean createTableQuery(Session session, String sql) {
        session.createNativeQuery(sql).executeUpdate();
        return true;
    }

    @Override
    public boolean deleteAll(Session session, String tableName) {
        String hql = "DELETE FROM " + tableName;
        session.createQuery(hql).executeUpdate();
        return true;
    }

    @Override
    public boolean dropTable(Session session, String tableName) {
        session.createNativeQuery("DROP TABLE IF EXISTS " + tableName).executeUpdate();
        return true;
    }

    @Override
    public Optional<Currency> findByValue(Session session, String value) {
        String hql = "FROM Currency WHERE currencyName = :value";
        Query<Currency> query = session.createQuery(hql, Currency.class);
        query.setParameter("value", value);
        return query.uniqueResultOptional();
    }

    //    protected CurrencyHibernateDaoImpl(Class<Currency> entityClass) {
//        super(entityClass);
//    }
////public class CurrencyHibernateDaoImpl implements DAOInterface<Long, Currency> {
//
//    public Currency insert(Session session, Currency currency) {
//        session.save(currency);
//        return currency;
//    }
//
//    public boolean update(Session session, Currency currency) {
//        try {
//            session.update(currency);
//            return true;
//        } catch (Exception e) {
//            log.error("Ошибка при обновлении Currency", e);
//            return false;
//        }
//    }
//
//    public boolean deleteById(Session session, Long id) {
//        try {
//            Currency currency = session.get(Currency.class, id);
//            if (currency != null) {
//                session.delete(currency);
//                return true;
//            } else {
//                log.warn("Currency с id = {} не найден для удаления", id);
//                return false;
//            }
//        } catch (Exception e) {
//            log.error("Ошибка при удалении Currency по id", e);
//            return false;
//        }
//    }
//
//    public Optional<Currency> findById(Session session, Long id) {
//        try {
//            Currency currency = session.get(Currency.class, id);
//            return Optional.ofNullable(currency);
//        } catch (Exception e) {
//            log.error("Ошибка при поиске Currency по id", e);
//            return Optional.empty();
//        }
//    }
//
//    public List<Currency> findAll(Session session) {
//        try {
//            Query<Currency> query = session.createQuery("FROM Currency", Currency.class);
//            return query.getResultList();
//        } catch (Exception e) {
//            log.error("Ошибка при получении списка Currency", e);
//            return List.of();
//        }
//    }
//
//    public boolean createTableQuery(Session session, String sql) {
//        try {
//            session.createSQLQuery(sql).executeUpdate();
//            return true;
//        } catch (Exception e) {
//            log.error("Ошибка при выполнении SQL запроса на создание таблицы", e);
//            return false;
//        }
//    }
//
//    public boolean deleteAll(Session session, String tableName) {
//        try {
//            String hql = "DELETE FROM " + tableName;
//            session.createQuery(hql).executeUpdate();
//            return true;
//        } catch (Exception e) {
//            log.error("Ошибка при удалении всех записей из таблицы {}", tableName, e);
//            return false;
//        }
//    }
//
//    public boolean dropTable(Session session, String tableName) {
//        try {
//            String sql = "DROP TABLE IF EXISTS " + tableName;
//            session.createSQLQuery(sql).executeUpdate();
//            return true;
//        } catch (Exception e) {
//            log.error("Ошибка при удалении таблицы {}", tableName, e);
//            return false;
//        }
//    }
//
//    public Optional<Currency> findByValue(Session session, String value) {
//        try {
//            Query<Currency> query = session.createQuery(
//                    "FROM Currency c WHERE c.currencyName = :value", Currency.class);
//            query.setParameter("value", value);
//            return query.uniqueResultOptional();
//        } catch (Exception e) {
//            log.error("Ошибка при поиске Currency по value", e);
//            return Optional.empty();
//        }
//    }






//    public CurrencyHibernateDaoImpl() {
//        super(Currency.class);
//    }
//
//    @Override
//    public Currency insert(Session session, Currency value) {
//        session.save(value);
//        return value;
//    }
//
//    @Override
//    public boolean update(Session session, Currency value) {
//        session.update(value);
//        return true;
//    }
//
//    @Override
//    public Optional<Currency> findByValue(Session session, String name) {
//        String hql = "FROM Currency c WHERE c.currencyName = :name";
//        Currency result = session.createQuery(hql, Currency.class)
//                .setParameter("name", name)
//                .uniqueResult();
//        return Optional.ofNullable(result);
//    }



//
//    private static final SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
//    @Getter
//    private static CurrencyHibernateDaoImpl INSTANCE = new CurrencyHibernateDaoImpl();
//
//
//    @Override
//    public Currency insert(Currency currency) {
//        Transaction transaction = null;
//        try (Session session = sessionFactory.openSession()) {
//            transaction = session.beginTransaction();
//            session.save(currency);
//            transaction.commit();
//            log.info("Валюта с CurrencyName: " + currency.getCurrencyName() + " добавлена.");
//            return currency;
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            log.warn(e.getMessage());
//            // throw new DaoException("Ошибка при добавлении валюты", e);
//            return null;
//        }
//    }
//
//    @Override
//    public boolean update(Currency currency) {
//        Transaction transaction = null;
//        try (Session session = sessionFactory.openSession()) {
//            transaction = session.beginTransaction();
//            session.update(currency);
//            transaction.commit();
//            log.info("Валюта с ID: " + currency.getId() + " обновлена.");
//            return true;
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            log.warn(e.getMessage());
//            // throw new DaoException("Ошибка при обновлении валюты", e);
//            return false;
//        }
//    }
//
//    @Override
//    public boolean delete(Long id) {
//        Transaction transaction = null;
//        try (Session session = sessionFactory.openSession()) {
//            transaction = session.beginTransaction();
//            Currency currency = session.get(Currency.class, id);
//            if (currency != null) {
//                session.delete(currency);
//                transaction.commit();
//                log.info("Валюта с ID: " + id + " удалена.");
//                return true;
//            } else {
//                log.warn("Валюта с ID: " + id + " не найдена.");
//                return false;
//            }
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            log.warn(e.getMessage());
//            throw new DaoException("Ошибка при удалении валюты", e);
//        }
//    }
//
//    @Override
//    public Optional<Currency> findById(Long id) {
//        try (Session session = sessionFactory.openSession()) {
//            Currency currency = session.get(Currency.class, id);
//            if (currency != null) {
//                log.info("Валюта с ID: " + id + " найдена.");
//                return Optional.of(currency);
//            } else {
//                return Optional.empty();
//            }
//        } catch (Exception e) {
//            log.warn(e.getMessage());
//            throw new DaoException("Ошибка при поиске валюты по ID", e);
//        }
//    }
//
//    @Override
//    public List<Currency> findAll() {
//        try (Session session = sessionFactory.openSession()) {
//            Query<Currency> query = session.createQuery("from Currency", Currency.class);
//            List<Currency> currencies = query.list();
//            log.info("Все валюты были найдены.");
//            return currencies;
//        } catch (Exception e) {
//            log.warn(e.getMessage());
//            throw new DaoException("Ошибка при поиске всех валют", e);
//        }
//    }
//
//    @Override
//    public boolean createTableQuery(String sql) {
//        return false;
//    }
//
//    @Override
//    public Optional<Currency> findByValue(String currencyCode) {
//        try (Session session = sessionFactory.openSession()) {
//            Query<Currency> query = session.createQuery("from Currency where currencyDigitalCode = :currencyCode", Currency.class);
//            query.setParameter("currencyCode", currencyCode);
//            Currency currency = query.uniqueResult();
//            if (currency != null) {
//                log.info("Валюта с кодом: " + currencyCode + " найдена.");
//                return Optional.of(currency);
//            } else {
//                return Optional.empty();
//            }
//        } catch (Exception e) {
//            log.warn(e.getMessage());
//            throw new DaoException("Ошибка при поиске валюты по коду: " + currencyCode, e);
//        }
//    }
//
//
//    @Override
//    public boolean dropTable(String tableName) {
//        try (Session session = sessionFactory.openSession()) {
//            String hql = "drop table if exists " + tableName;
//            Query query = session.createQuery(hql);
//            query.executeUpdate();
//            log.info("Таблица " + tableName + " была удалена.");
//            return true;
//        } catch (Exception e) {
//            log.warn(e.getMessage());
//            throw new DaoException("Ошибка при удалении таблицы " + tableName, e);
//        }
//    }
//
//    @Override
//    public boolean deleteAll(String tableName) {
//        try (Session session = sessionFactory.openSession()) {
//            String hql = "delete from " + tableName;
//            Query query = session.createQuery(hql);
//            query.executeUpdate();
//            log.info("Все данные из таблицы " + tableName + " были удалены.");
//            return true;
//        } catch (Exception e) {
//            log.warn(e.getMessage());
//            throw new DaoException("Ошибка при удалении всех записей из таблицы " + tableName, e);
//        }
//    }
}

