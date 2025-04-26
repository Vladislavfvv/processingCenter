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
       return Optional.ofNullable(session.get(CardStatus.class, value));
    }
}
