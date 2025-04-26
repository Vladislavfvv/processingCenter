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
    public boolean update(Session session, Account entity) {//--------возврат сущности!!!
        session.update(entity);// - здесь создается DTO
        return true;// возврат DTO
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

}
