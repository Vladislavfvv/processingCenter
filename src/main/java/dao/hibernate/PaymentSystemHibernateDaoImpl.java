package dao.hibernate;

import dao.DAOInterface;
import exception.DaoException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.PaymentSystem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateConfig;

import java.util.List;
import java.util.Optional;

public class PaymentSystemHibernateDaoImpl extends AbstractHibernateDao<Long, PaymentSystem> {

    public PaymentSystemHibernateDaoImpl() {
        super(PaymentSystem.class);
    }

    @Override
    public PaymentSystem insert(Session session, PaymentSystem entity) {
        session.save(entity);
        return entity;
    }

    @Override
    public boolean update(Session session, PaymentSystem entity) {
        session.update(entity);
        return true;
    }

    @Override
    public boolean delete(Session session, Long id) {
        PaymentSystem entity = session.get(PaymentSystem.class, id);
        if (entity != null) {
            session.delete(entity);
            return true;
        }
        return false;
    }

    @Override
    public Optional<PaymentSystem> findById(Session session, Long id) {
        return Optional.ofNullable(session.get(PaymentSystem.class, id));
    }

    @Override
    public List<PaymentSystem> findAll(Session session) {
        Query<PaymentSystem> query = session.createQuery("FROM PaymentSystem", PaymentSystem.class);
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
    public Optional<PaymentSystem> findByValue(Session session, String value) {
       return Optional.ofNullable(session.get(PaymentSystem.class, value));
    }


    //private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();//то если что только если используем hibernate.cfg.xml
    // т.к. Configuration().configure() ищет hibernate.cfg.xml

    //private static final String CREATE_CARD_STATUS = "INSERT INTO payment_system VALUES (?, ?)";

//    private static final SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
//    @Getter
//    private static final PaymentSystemHibernateDaoImpl Instance = new PaymentSystemHibernateDaoImpl();
//
//    @Override
//    public PaymentSystem insert(PaymentSystem paymentSystem) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            session.save(paymentSystem);
//            transaction.commit();
//            log.info("Вставляю PaymentSystem: {}", paymentSystem);
//            return paymentSystem;
//        } catch (Exception e) {
//            transaction.rollback();
//            log.error("Ошибка при вставке PaymentSystem: {}", paymentSystem, e);
//            throw new DaoException("Ошибка при вставке PaymentSystem", e);
//        }
//    }
//
//    @Override
//    public boolean update(PaymentSystem value) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            session.update(value);
//            transaction.commit();
//            log.info("Обнова PaymentSystem: {}", value);
//            return true;
//        } catch (Exception e) {
//            transaction.rollback();
//            log.error("Ошибка при обновлении PaymentSystem: {}", value, e);
//           // throw new DaoException("Ошибка при обновлении PaymentSystem", e);
//        }
//        return false;
//    }
//
//    @Override
//    public boolean delete(Long id) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            PaymentSystem paymentSystem = session.get(PaymentSystem.class, id);
//            if (paymentSystem != null) {
//                session.delete(paymentSystem);
//                transaction.commit();
//                log.info("Удален PaymentSystem с ID: {}", id);
//                return true;
//            } else {
//                transaction.rollback();
//                log.warn("Не найден PaymentSystem для удаления с ID: {}", id);
//                return false;
//            }
//        } catch (Exception e) {
//            transaction.rollback();
//            log.warn("Ошибка при удалении PaymentSystem с ID: {}", id);
//          //  throw new DaoException("Ошибка при удалении PaymentSystem", e);
//            return false;
//        }
//
//    }
//
//    @Override
//    public Optional<PaymentSystem> findById(Long id) {
//        Session session = sessionFactory.openSession();
//        PaymentSystem ps = session.get(PaymentSystem.class, id);
//        log.info("Поиск PaymentSystem по ID: {} — найдено: {}", id, ps);
//        return Optional.ofNullable(ps);
//    }
//
//    @Override
//    public List<PaymentSystem> findAll() {
//        Session session = sessionFactory.openSession();
//        String hql = "FROM PaymentSystem";
//        List<PaymentSystem> list = session.createQuery(hql, PaymentSystem.class).getResultList();
//        log.info("Список PaymentSystem готов");
//        return list;
//
//    }
//
//    @Override
//    public boolean createTableQuery(String sql) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            session.createSQLQuery(sql).executeUpdate();
//            transaction.commit();
//            log.info("Выполнен SQL для создания таблицы: {}", sql);
//            return true;
//        } catch (Exception e) {
//            log.error("Ошибка при выполнении SQL для создания таблицы: {}", sql, e);
//            transaction.rollback();
//            throw new DaoException("Ошибка при создании таблицы", e);
//        }
//    }
//
//    @Override
//    public boolean deleteAll(String sql) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            int deletedCount = session.createQuery(sql).executeUpdate();
//            transaction.commit();
//            log.info("Удалено {} записей", deletedCount);
//            return deletedCount > 0;
//        } catch (Exception e) {
//            transaction.rollback();
//            log.error("Ошибка при удалении всех записей с помощью HQL: {}", sql, e);
//            return false;
//        }
//    }
//
//    @Override
//    public boolean dropTable(String tableName) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            session.createSQLQuery("DROP TABLE IF EXISTS " + tableName).executeUpdate();
//            transaction.commit();
//            log.info("Удалена таблица: {}", tableName);
//            return true;
//        } catch (Exception e) {
//            transaction.rollback();
//            log.error("Ошибка при удалении таблицы: {}", tableName, e);
//            return false;
//        }
//    }
//
//    @Override
//    public Optional<PaymentSystem> findByValue(String value) {
//        Session session = sessionFactory.openSession();
//        String hql = "FROM PaymentSystem WHERE paymentSystemName = :name";
//        Query<PaymentSystem> query = session.createQuery(hql, PaymentSystem.class);
//        query.setParameter("name", value);
//        return query.uniqueResultOptional();
//    }
}
