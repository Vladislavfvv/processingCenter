package dao.hibernate;

import dao.DAOHibernateInterface;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class AbstractHibernateDao <K, V> implements DAOHibernateInterface<K, V> {
    private final Class<V> entityClass;

    protected AbstractHibernateDao(Class<V> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public List<V> findAll(Session session) {
        String hql = "FROM " + entityClass.getSimpleName();
        Query<V> query = session.createQuery(hql, entityClass);
        return query.getResultList();
    }

    @Override
    public Optional<V> findById(Session session, K id) {
        V entity = session.get(entityClass, (java.io.Serializable) id);
        return Optional.ofNullable(entity);
    }

    @Override
    public boolean delete(Session session, K id) {
        Optional<V> entity = findById(session, id);
        if (entity.isPresent()) {
            session.delete(entity.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean createTableQuery(Session session, String sql) {
        try {
            session.createNativeQuery(sql).executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteAll(Session session, String tableName) {
        try {
            String hql = "DELETE FROM " + entityClass.getSimpleName();
            session.createQuery(hql).executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean dropTable(Session session, String tableName) {
        try {
            String sql = "DROP TABLE IF EXISTS " + tableName;
            session.createNativeQuery(sql).executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //длфя реализациии в дочерних классах
    @Override
    public Optional<V> findByValue(Session session, String value) {
        return Optional.empty();
    }

    @Override
    public V insert(Session session, V value) {
        session.save(value);
        return value;
    }

    @Override
    public boolean update(Session session, V value) {
        try {
            session.update(value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
