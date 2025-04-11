package service;

import java.util.List;
import java.util.Optional;

public interface ServiceInterface<E, K> {


    E create(E value);

    boolean update(E value);

    boolean delete(K id);

    Optional<E> findById(K id);

    List<E> findAll();

    void createTable(String sql);

    boolean deleteAll(String s);

    boolean dropTable(String s);

}
