package com.java.service;

import com.java.model.SalesPoint;

import java.util.List;
import java.util.Optional;

public interface ServiceInterface<E, K> {


    E create(E value);

    boolean update(E value);

    boolean delete(K id);

    Optional<E> findById(K id);

    List<E> findAll();

    void createTable();

    boolean deleteAll(String s);

    boolean dropTable(String s);


    // Метод для очистки всех записей из таблицы sales_point
    boolean clearSalesPoints();

    // Метод для удаления таблицы sales_point
    boolean removeSalesPointsTable();
}
