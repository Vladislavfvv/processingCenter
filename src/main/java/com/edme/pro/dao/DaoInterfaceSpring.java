package com.edme.pro.dao;

import com.edme.pro.dto.CurrencyDto;

import java.util.List;
import java.util.Optional;

public interface DaoInterfaceSpring <K, E>{
    E insert(E value);
    E update(E value);
    boolean delete(K id);
    Optional<E> findById(K id);
    List<E> findAll();
    boolean createTable();
    boolean deleteAll();
    boolean dropTable();
    Optional<E> findByValue(String name);

}
