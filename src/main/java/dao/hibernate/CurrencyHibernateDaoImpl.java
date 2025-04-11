package dao.hibernate;

import dao.DAOInterface;
import exception.DaoException;
import model.Currency;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class CurrencyHibernateDaoImpl implements DAOInterface<Long, Currency> {

    private static final Logger logger = Logger.getLogger(CurrencyHibernateDaoImpl.class.getName());
    private final SessionFactory sessionFactory;

    public CurrencyHibernateDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Currency insert(Currency currency) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(currency);
            transaction.commit();
            logger.info("Валюта с CurrencyName: " + currency.getCurrencyName() + " добавлена.");
            return currency;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при добавлении валюты", e);
        }
    }

    @Override
    public boolean update(Currency currency) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(currency);
            transaction.commit();
            logger.info("Валюта с ID: " + currency.getId() + " обновлена.");
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при обновлении валюты", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Currency currency = session.get(Currency.class, id);
            if (currency != null) {
                session.delete(currency);
                transaction.commit();
                logger.info("Валюта с ID: " + id + " удалена.");
                return true;
            } else {
                logger.warning("Валюта с ID: " + id + " не найдена.");
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при удалении валюты", e);
        }
    }

    @Override
    public Optional<Currency> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Currency currency = session.get(Currency.class, id);
            if (currency != null) {
                logger.info("Валюта с ID: " + id + " найдена.");
                return Optional.of(currency);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при поиске валюты по ID", e);
        }
    }

    @Override
    public List<Currency> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Currency> query = session.createQuery("from Currency", Currency.class);
            List<Currency> currencies = query.list();
            logger.info("Все валюты были найдены.");
            return currencies;
        } catch (Exception e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при поиске всех валют", e);
        }
    }

    @Override
    public boolean createTableQuery(String sql) {
        return false;
    }

    @Override
    public Optional<Currency> findByValue(String currencyCode) {
        try (Session session = sessionFactory.openSession()) {
            Query<Currency> query = session.createQuery("from Currency where currencyDigitalCode = :currencyCode", Currency.class);
            query.setParameter("currencyCode", currencyCode);
            Currency currency = query.uniqueResult();
            if (currency != null) {
                logger.info("Валюта с кодом: " + currencyCode + " найдена.");
                return Optional.of(currency);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при поиске валюты по коду: " + currencyCode, e);
        }
    }

    // Дополнительные методы для работы с Hibernate

    public boolean createTableQuery() {
        // В Hibernate таблицы создаются автоматически при первом запуске приложения,
        // если они не существуют, и если у вас настроена конфигурация Hibernate с DDL Auto.
        // Если необходимо явное создание таблиц, можно использовать SchemaExport.
        return true;
    }

    public boolean dropTable(String tableName) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "drop table if exists " + tableName;
            Query query = session.createQuery(hql);
            query.executeUpdate();
            logger.info("Таблица " + tableName + " была удалена.");
            return true;
        } catch (Exception e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при удалении таблицы " + tableName, e);
        }
    }

    public boolean deleteAll(String tableName) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "delete from " + tableName;
            Query query = session.createQuery(hql);
            query.executeUpdate();
            logger.info("Все данные из таблицы " + tableName + " были удалены.");
            return true;
        } catch (Exception e) {
            logger.severe(e.getMessage());
            throw new DaoException("Ошибка при удалении всех записей из таблицы " + tableName, e);
        }
    }
}

