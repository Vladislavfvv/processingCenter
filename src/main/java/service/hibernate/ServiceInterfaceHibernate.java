package service.hibernate;

import java.util.List;
import java.util.Optional;

public interface ServiceInterfaceHibernate <K, E> {


    E create(E value);

    boolean update(E value);

    boolean delete(K id);

    Optional<E> findById(K id);

    List<E> findAll();

    void createTable(String sql);

    boolean deleteAll(String tableName);

    boolean dropTable(String tableName);

}
