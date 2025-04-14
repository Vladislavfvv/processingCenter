package dao.hibernate;

import dao.DAOInterface;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.CardStatus;
import model.IssuingBank;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateConfig;
import util.SqlQueryLoader;

import java.util.List;
import java.util.Optional;

@Slf4j
public class IssuingBankHibernateDaoImpl extends AbstractHibernateDao<Long, IssuingBank> {
    public IssuingBankHibernateDaoImpl() {
        super(IssuingBank.class);
    }

    @Override
    public IssuingBank insert(Session session, IssuingBank entity) {
        session.save(entity);
        return entity;
    }

    @Override
    public boolean update(Session session, IssuingBank entity) {
        session.update(entity);
        return true;
    }

    @Override
    public boolean delete(Session session, Long id) {
        IssuingBank entity = session.get(IssuingBank.class, id);
        if (entity != null) {
            session.delete(entity);
            return true;
        }
        return false;
    }

    @Override
    public Optional<IssuingBank> findById(Session session, Long id) {
        return Optional.ofNullable(session.get(IssuingBank.class, id));
    }

    @Override
    public List<IssuingBank> findAll(Session session) {
        Query<IssuingBank> query = session.createQuery("FROM IssuingBank", IssuingBank.class);
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
    public Optional<IssuingBank> findByValue(Session session, String value) {

        return Optional.ofNullable(session.get(IssuingBank.class, value));
    }


//    private static final SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
//
//    @Getter
//    private static final IssuingBankHibernateDaoImpl INSTANCE = new IssuingBankHibernateDaoImpl();
//
//    @Override
//    public IssuingBank insert(IssuingBank value) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            session.save(value);
//            transaction.commit();
//            log.info("Inserting IssuingBank: {}", value);
//            return value;
//        } catch (Exception e) {
//            transaction.rollback();
//            log.error("Error inserting IssuingBank: {}", value, e);
//            // throw new RuntimeException("Error inserting card status", e);
//            return null;
//        }
//    }
//
//    @Override
//    public boolean update(IssuingBank value) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            session.update(value);
//            log.info("Updating IssuingBank: {}", value);
//            transaction.commit();
//            return true;
//        } catch (Exception e) {
//            log.error("Error updating IssuingBank: {}", value, e);
//            transaction.rollback();
//        }
//        return false;
//    }
//
//    @Override
//    public boolean delete(Long id) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            IssuingBank issuingBank = session.get(IssuingBank.class, id);
//            if (issuingBank != null) {
//                session.delete(issuingBank);
//                log.info("Deleting IssuingBank: {}", issuingBank);
//                transaction.commit();
//                return true;
//            } else {
//                log.info("IssuingBank not found: {}", id);
//                transaction.rollback();
//                return false;
//            }
//        } catch (Exception e) {
//            transaction.rollback();
//            log.error("Error deleting IssuingBank: {}", id, e);
//            return false;
//        }
//    }
//
//    @Override
//    public Optional<IssuingBank> findById(Long id) {
//        Session session = sessionFactory.openSession();
//        IssuingBank issuingBank = session.get(IssuingBank.class, id);
//        log.info("Founded IssuingBank {} with id: {}", issuingBank, id);
//        return Optional.ofNullable(issuingBank);
//    }
//
//    @Override
//    public List<IssuingBank> findAll() {
//        Session session = sessionFactory.openSession();
//        String hql = "FROM IssuingBank";
//        List<IssuingBank> issuingBanks = session.createQuery(hql, IssuingBank.class).getResultList();
//        log.info("Found {} IssuingBanks", issuingBanks.size());
//        return issuingBanks;
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
//    public Optional<IssuingBank> findByValue(String cardNumber) {
//        return Optional.empty();
//    }
}
