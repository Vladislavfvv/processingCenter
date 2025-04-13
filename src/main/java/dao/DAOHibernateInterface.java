package dao;

import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public interface DAOHibernateInterface <K, E> {
    E insert(Session session, E value);

    boolean update(Session session, E value);

    boolean delete(Session session, K id);

    Optional<E> findById(Session session, K id);

    List<E> findAll(Session session);

    boolean createTableQuery(Session session, String sql);

    boolean deleteAll(Session session, String tableName);

    boolean dropTable(Session session, String tableName);

    Optional<E> findByValue(Session session, String value);
}