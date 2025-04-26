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

}

