package dao;

import java.util.List;
import java.util.Optional;

public interface DAOInterface<K, E> {
    E insert(E value);
    boolean update(E value);
    boolean delete(K id);
    Optional<E> findById(K id);
    List<E> findAll();
    boolean createTableQuery(String sql);

    boolean deleteAll(String s);

    boolean dropTable(String s);

    Optional<E> findByValue(String cardNumber);
}
