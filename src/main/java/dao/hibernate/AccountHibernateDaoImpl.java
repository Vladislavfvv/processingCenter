package dao.hibernate;

import dao.DAOHibernateInterface;
import dao.DAOInterface;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateConfig;

import java.util.List;
import java.util.Optional;

@Slf4j

public class AccountHibernateDaoImpl extends AbstractHibernateDao<Long, Account> {

    public AccountHibernateDaoImpl() {
        super(Account.class);
    }

    @Override
    public Account insert(Session session, Account entity) {
        session.save(entity);
        return entity;
    }

    @Override
    public boolean update(Session session, Account entity) {
        session.update(entity);
        return true;
    }

    @Override
    public boolean delete(Session session, Long id) {
        Account entity = session.get(Account.class, id);
        if (entity != null) {
            session.delete(entity);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Account> findById(Session session, Long id) {
        return Optional.ofNullable(session.get(Account.class, id));
    }

    @Override
    public List<Account> findAll(Session session) {
        Query<Account> query = session.createQuery("FROM Account", Account.class);
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
    public Optional<Account> findByValue(Session session, String value) {
        String hql = "FROM Account WHERE accountNumber = :value";
        Query<Account> query = session.createQuery(hql, Account.class);
        query.setParameter("value", value);
        return query.uniqueResultOptional();
    }

    //    @Override
//    public Account insert(Session session, Account value) {
//        return null;
//    }
//
//    @Override
//    public boolean update(Session session, Account value) {
//        return false;
//    }
//
//    @Override
//    public boolean delete(Session session, Long id) {
//        return false;
//    }
//
//    @Override
//    public Optional<Account> findById(Session session, Long id) {
//        return Optional.empty();
//    }
//
//    @Override
//    public List<Account> findAll(Session session) {
//        return List.of();
//    }
//
//    @Override
//    public boolean createTableQuery(Session session, String sql) {
//        return false;
//    }
//
//    @Override
//    public boolean deleteAll(Session session, String tableName) {
//        return false;
//    }
//
//    @Override
//    public boolean dropTable(Session session, String tableName) {
//        return false;
//    }
//
//    @Override
//    public Optional<Account> findByValue(Session session, String value) {
//        return Optional.empty();
//    }


//    private static final SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
//    @Getter
//    private static final AccountHibernateDaoImpl Instance = new AccountHibernateDaoImpl();
//
//    @Override
//    public Account insert(Account account) {
//        Transaction transaction = null;
//        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//
//            // Проверяем вложенные объекты, если они без id — сохраняем их
//            if (account.getCurrencyId() != null && account.getCurrencyId().getId() == null) {
//                session.save(account.getCurrencyId());
//            }
//
//            if (account.getIssuingBankId() != null && account.getIssuingBankId().getId() == null) {
//                session.save(account.getIssuingBankId());
//            }
//
//            Account savedAccount = dao.insert(session, account);
//
//            transaction.commit();
//            return savedAccount;
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            log.error("Ошибка при сохранении Account", e);
//            throw e;
//        }
//    }
//
//    @Override
//    public boolean update(Account value) {
//        return false;
//    }
//
//    @Override
//    public boolean delete(Long id) {
//        return false;
//    }
//
//    @Override
//    public Optional<Account> findById(Long id) {
//        return Optional.empty();
//    }
//
//    @Override
//    public List<Account> findAll() {
//        return List.of();
//    }
//
//    @Override
//    public boolean createTableQuery(String sql) {
//        return false;
//    }
//
//    @Override
//    public boolean deleteAll(String s) {
//        return false;
//    }
//
//    @Override
//    public boolean dropTable(String s) {
//        return false;
//    }
//
//    @Override
//    public Optional<Account> findByValue(String value) {
//        return Optional.empty();
//    }
}
