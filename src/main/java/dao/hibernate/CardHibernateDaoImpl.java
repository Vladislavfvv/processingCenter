package dao.hibernate;

import model.Card;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class CardHibernateDaoImpl  extends AbstractHibernateDao<Long, Card> {
    public CardHibernateDaoImpl() {
        super(Card.class);
    }

    @Override
    public Card insert(Session session, Card card) {
        session.save(card);
        return card;
    }

    @Override
    public boolean update(Session session, Card card) {
        session.update(card);
        return true;
    }

    @Override
    public boolean delete(Session session, Long id) {
        Card card = session.get(Card.class, id);
        if (card != null) {
            session.delete(card);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Card> findById(Session session, Long id) {
        return Optional.ofNullable(session.get(Card.class, id));
    }

    @Override
    public List<Card> findAll(Session session) {
        Query<Card> query = session.createQuery("FROM Card", Card.class);
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
        Query<?> query = session.createQuery(hql);
        query.executeUpdate();
        return true;
    }

    @Override
    public boolean dropTable(Session session, String tableName) {
        String sql = "DROP TABLE IF EXISTS " + tableName;
        session.createNativeQuery(sql).executeUpdate();
        return true;
    }

    @Override
    public Optional<Card> findByValue(Session session, String value) {
        String hql = "FROM Card WHERE cardNumber = :value";
        Query<Card> query = session.createQuery(hql, Card.class);
        query.setParameter("value", value);
        return query.uniqueResultOptional();
    }

}
