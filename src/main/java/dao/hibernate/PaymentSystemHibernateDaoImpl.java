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

}
