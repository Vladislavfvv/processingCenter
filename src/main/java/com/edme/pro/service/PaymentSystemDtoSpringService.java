package com.edme.pro.service;

import com.edme.pro.dao.DaoInterfaceSpring;
import com.edme.pro.dto.PaymentSystemDto;

import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.edme.pro.mapper.PaymentSystemMapper;
import com.edme.pro.model.PaymentSystem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentSystemDtoSpringService {

    private final DaoInterfaceSpring<Long, PaymentSystem> paymentSystemDao;

    public PaymentSystemDtoSpringService(DaoInterfaceSpring<Long, PaymentSystem> paymentSystemDao) {
        this.paymentSystemDao = paymentSystemDao;
    }


    @Transactional
    public Optional<PaymentSystem> save(PaymentSystemDto paymentSystemDto) {
        // Проверяем, есть ли такая сущность в БД
        Optional<PaymentSystem> existing = paymentSystemDao.findByValue(paymentSystemDto.getPaymentSystemName());
        if (existing.isPresent()) {
            log.info("PaymentSystem '{}' уже существует", paymentSystemDto.getPaymentSystemName());
            return existing;
        }

        // Обнуляем ID, чтобы Hibernate не думал, что это уже существующий объект
        paymentSystemDto.setId(null);

        // Маппим в entity
        PaymentSystem entity = PaymentSystemMapper.toEntity(paymentSystemDto);

        // Используем merge вместо persist
        PaymentSystem saved = paymentSystemDao.insert(entity);
        log.info("Saved payment system '{}'", saved.getPaymentSystemName());
        return Optional.of(saved);
    }


    public Optional<PaymentSystem> findById(Long id) {
        //return paymentSystemDao.findById(id).map(PaymentSystemMapper::toDto);
        return paymentSystemDao.findById(id);
    }


    public List<PaymentSystem> findAll() {
//        List<PaymentSystemDto> paymentSystems = new ArrayList<>();
//        try {
//            return paymentSystemDao.findAll()
//                    .stream()
//                    .map(PaymentSystemMapper::toDto)
//                    .collect(Collectors.toList());
//        } catch (Exception e) {
//            log.info("Error while fetching all payment systems", e);
//            return paymentSystems;
//        }
        return paymentSystemDao.findAll();
    }




    public Optional<PaymentSystem> findByValue(String value) {
        //return paymentSystemDao.findByValue(value).map(PaymentSystemMapper::toDto);
        return paymentSystemDao.findByValue(value);
    }

    @Transactional
    public Optional<PaymentSystem> update(Long id, PaymentSystemDto paymentSystemDto) {
        if (id == null) {//если id не задан
            log.info("Comparable PaymentSystem must have an ID");
            //throw new IllegalArgumentException("ID должен быть задан для обновления");
            return Optional.empty();
        }

//        if (paymentSystemDto.getId() == null) {
//            log.info("Payment system with ID {} not found", paymentSystemDto.getId());
//            return Optional.empty();
//        }
        Optional<PaymentSystem> existingById = paymentSystemDao.findById(id);
        if (existingById.isEmpty()) {
            log.info("Payment system with ID {} not found", id);
            return Optional.empty();
        }

        PaymentSystem newPaymentSystemEntity = existingById.get();
        newPaymentSystemEntity.setPaymentSystemName(paymentSystemDto.getPaymentSystemName());
        paymentSystemDao.update(newPaymentSystemEntity);
        log.info("Payment system with ID {} updated", newPaymentSystemEntity.getId());
        return Optional.of(newPaymentSystemEntity);
    }

    @Transactional
    public boolean createTable() {
        return paymentSystemDao.createTable();
    }

    @Transactional
    public boolean delete(Long id) {
        return paymentSystemDao.delete(id);
    }

    @Transactional
    public boolean deleteAll() {

//        return paymentSystemDao.deleteAll();
        try {
            return paymentSystemDao.deleteAll();
        } catch (Exception e) {
            log.error("Ошибка при удалении всех записей из payment_system", e);
            throw e; // важно пробросить, чтобы Spring знал о причине rollback
        }
    }

    @Transactional
    public boolean dropTable() {
        return paymentSystemDao.dropTable();
    }

    @Transactional
    public boolean initializeTable() {
        boolean tableCreated = paymentSystemDao.createTable();
        if (!tableCreated) {
            log.warn("Не удалось создать таблицу payment_system");
            return false;
        }

        boolean valuesInserted = paymentSystemDao.insertDefaultValues();
        if (!valuesInserted) {
            log.warn("Таблица создана, но значения не вставлены");
            return false;
        }

        log.info("Таблица создана и значения успешно добавлены");
        return true;
    }
}
