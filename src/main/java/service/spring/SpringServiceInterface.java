package service.spring;

import model.Currency;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SpringServiceInterface<K, E> {

    @Transactional
    public Optional<E> save(E entity);

    public Optional<E> getById(K id);

    public List<E> getAll();

    @Transactional
    public Optional<E> update(E entity);

    @Transactional
    public boolean delete(K id);

    @Transactional
    public boolean deleteALL();

    public Optional<E> getByName(String name);

    public boolean dropTable();



}
