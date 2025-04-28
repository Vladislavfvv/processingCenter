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
    public Optional<PaymentSystemDto> save(PaymentSystemDto dto) {
        // Проверяем, есть ли такая сущность в БД
        Optional<PaymentSystem> existing = paymentSystemDao.findByValue(dto.getPaymentSystemName());
        if (existing.isPresent()) {
            log.info("PaymentSystem '{}' уже существует", dto.getPaymentSystemName());
            return Optional.of(PaymentSystemMapper.toDto(existing.get()));
        }

        // Обнуляем ID, чтобы Hibernate не думал, что это уже существующий объект
        dto.setId(null);

        // Маппим в entity
        PaymentSystem entity = PaymentSystemMapper.toEntity(dto);

        // Используем merge вместо persist
        PaymentSystem saved = paymentSystemDao.insert(entity);

        return Optional.of(PaymentSystemMapper.toDto(saved));
    }

    public List<PaymentSystemDto> findAll() {
        List<PaymentSystemDto> paymentSystems = new ArrayList<>();
        try {
            return paymentSystemDao.findAll()
                    .stream()
                    .map(PaymentSystemMapper::toDto)
                    .collect(Collectors.toList());
        }catch (Exception e) {
            log.info("Error while fetching all payment systems", e);
            return paymentSystems;
        }
    }


    public Optional<PaymentSystemDto> findById(Long id) {
       return paymentSystemDao.findById(id)
               .map(PaymentSystemMapper::toDto);
    }


    public Optional<PaymentSystemDto> findByValue(String value) {
        return paymentSystemDao.findByValue(value)
                .map(PaymentSystemMapper::toDto);
    }

    @Transactional
    public Optional<PaymentSystemDto> update(PaymentSystemDto paymentSystemDto) {
        if (paymentSystemDto.getId() == null) {
            log.info("Payment system with ID {} not found", paymentSystemDto.getId());
            return Optional.empty();
        }
        Optional<PaymentSystem> existingById = paymentSystemDao.findById(paymentSystemDto.getId());
        if (existingById.isEmpty()) {
            log.info("Payment system with ID {} not found", paymentSystemDto.getId());
            return Optional.empty();
        }
        PaymentSystem paymentSystemEntity = existingById.get();
        paymentSystemEntity.setPaymentSystemName(paymentSystemDto.getPaymentSystemName());
        paymentSystemDao.update(paymentSystemEntity);
        log.info("Payment system with ID {} updated", paymentSystemDto.getId());
        return Optional.of(PaymentSystemMapper.toDto(paymentSystemEntity));
    }

    @Transactional
    public boolean delete(Long id) {
        return paymentSystemDao.delete(id);
    }

    @Transactional
    public boolean deleteAll() {
       return paymentSystemDao.deleteAll();
    }

    @Transactional
    public boolean dropTable(){
        return paymentSystemDao.dropTable();
    }
}
