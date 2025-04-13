package dao.hibernate;

import dao.DAOInterface;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.CardStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateConfig;
import util.SqlQueryLoader;

import java.util.List;
import java.util.Optional;


public class CardStatusHibernateDaoImpl  extends AbstractHibernateDao<Long, CardStatus> {
    public CardStatusHibernateDaoImpl() {
        super(CardStatus.class);
    }

    @Override
    public CardStatus insert(Session session, CardStatus entity) {
        session.save(entity);
        return entity;
    }

    @Override
    public boolean update(Session session, CardStatus entity) {
        session.update(entity);
        return true;
    }

    @Override
    public boolean delete(Session session, Long id) {
        CardStatus entity = session.get(CardStatus.class, id);
        if (entity != null) {
            session.delete(entity);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CardStatus> findById(Session session, Long id) {
        return Optional.ofNullable(session.get(CardStatus.class, id));
    }

    @Override
    public List<CardStatus> findAll(Session session) {
        Query<CardStatus> query = session.createQuery("FROM CardStatus", CardStatus.class);
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
    public Optional<CardStatus> findByValue(Session session, String value) {
        String hql = "FROM CardStatus WHERE statusName = :value";
        Query<CardStatus> query = session.createQuery(hql, CardStatus.class);
        query.setParameter("value", value);
        return query.uniqueResultOptional();
    }
//    private static final SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
//    @Getter
//    private static final CardStatusHibernateDaoImpl INSTANCE = new CardStatusHibernateDaoImpl();
//
//
//    @Override
//    public CardStatus insert(CardStatus cardStatus) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            session.save(cardStatus);
//            transaction.commit();
//            log.info("Inserting CardStatus: {}", cardStatus);
//            return cardStatus;
//        } catch (Exception e) {
//            transaction.rollback();
//            log.error("Error inserting CardStatus: {}", cardStatus, e);
//           // throw new RuntimeException("Error inserting card status", e);
//            return null;
//        }
//    }
//
//
//    @Override
//    public boolean update(CardStatus cardStatus) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            session.update(cardStatus);
//            log.info("Updating CardStatus: {}", cardStatus);
//            transaction.commit();
//            return true;
//        } catch (Exception e) {
//            log.error("Error updating CardStatus: {}", cardStatus, e);
//            transaction.rollback();
//        }
//        return false;
//    }
//
//
//    @Override
//    public boolean delete(Long id) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            CardStatus cardStatus = session.get(CardStatus.class, id);
//            if (cardStatus != null) {
//                session.delete(cardStatus);
//                log.info("Deleting CardStatus: {}", cardStatus);
//                transaction.commit();
//                return true;
//            } else {
//                log.info("CardStatus not found: {}", id);
//                transaction.rollback();
//                return false;
//            }
//        } catch (Exception e) {
//            transaction.rollback();
//            log.error("Error deleting CardStatus: {}", id, e);
//            return false;
//        }
//    }
//
//    @Override
//    public Optional<CardStatus> findById(Long id) {
//        Session session = sessionFactory.openSession();
//        CardStatus cardStatus = session.get(CardStatus.class, id);
//        log.info("Founded CardStatus {} with id: {}", cardStatus, id);
//        return Optional.ofNullable(cardStatus);
//    }
//
//    @Override
//    public List<CardStatus> findAll() {
//        Session session = sessionFactory.openSession();
//        String hql = "FROM CardStatus";
//        List<CardStatus> cardStatuses = session.createQuery(hql, CardStatus.class).getResultList();
//        log.info("Found {} card statuses", cardStatuses.size());
//        return cardStatuses;
//    }
//
//    @Override
//    public boolean createTableQuery(String sqlKey) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        String sqlValue = SqlQueryLoader.getQuery(sqlKey); // получаем запрос по ключу
//        try {
//            session.createSQLQuery(sqlValue).executeUpdate();
//            transaction.commit();
//            log.info("Created table query: {}", sqlValue);
//            return true;
//        } catch (Exception e) {
//            log.error("Error creating table query: {}", sqlValue, e);
//            transaction.rollback();
//            //  throw new DaoException("Ошибка при создании таблицы", e);
//            return false;
//        }
//    }
//
//    @Override
//    public boolean deleteAll(String sqlKey) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        String sql = SqlQueryLoader.getQuery(sqlKey);
//        try {
//            int deletedCount = session.createQuery(sql).executeUpdate();
//            transaction.commit();
//            log.info("Deleted {}", deletedCount);
//            return deletedCount > 0;
//        } catch (Exception e) {
//            transaction.rollback();
//            log.error("Error deleting: {}", sql, e);
//            return false;
//        }
//    }
//
//    @Override
//    public boolean dropTable(String tableName) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        //String sql = SqlQueryLoader.getQuery(sqlKey);
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
//    public Optional<CardStatus> findByValue(String value) {
//        Session session = sessionFactory.openSession();
//        String hql = "FROM CardStatus WHERE cardStatusName = :name";
//        Query<CardStatus> query = session.createQuery(hql, CardStatus.class);
//        query.setParameter("name", value);
//        return query.uniqueResultOptional();
//    }
}
