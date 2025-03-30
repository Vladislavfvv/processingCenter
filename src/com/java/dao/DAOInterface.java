package com.java.dao;

import java.util.List;
import java.util.Optional;

public interface DAOInterface<E> {
    E insert(E value);
    boolean update(E value);
    boolean delete(Long key);
    Optional<E> findById(Long key);
    List<E> getAll();
    boolean createTable();
    boolean dropTable();
    boolean deleteAll();
}
