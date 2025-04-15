package service.hibernate;

import java.util.List;
import java.util.Optional;

public class AbstractHibernateClass<K, E> implements ServiceInterfaceHibernate <K, E>{

    @Override
    public E create(E value) {
        return null;
    }

    @Override
    public boolean update(E value) {
        return false;
    }

    @Override
    public boolean delete(K id) {
        return false;
    }

    @Override
    public Optional<E> findById(K id) {
        return Optional.empty();
    }

    @Override
    public List<E> findAll() {
        return List.of();
    }

    @Override
    public void createTable(String sql) {

    }

    @Override
    public boolean deleteAll(String tableName) {
        return false;
    }

    @Override
    public boolean dropTable(String tableName) {
        return false;
    }
}
