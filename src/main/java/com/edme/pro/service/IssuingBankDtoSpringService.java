package com.edme.pro.service;

import com.edme.pro.dao.DaoInterfaceSpring;
import com.edme.pro.dto.IssuingBankDto;
import lombok.extern.slf4j.Slf4j;
import com.edme.pro.mapper.IssuingBankMapper;
import com.edme.pro.model.IssuingBank;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IssuingBankDtoSpringService {
    private final DaoInterfaceSpring<Long, IssuingBank> issuingBankDao;

    public IssuingBankDtoSpringService(DaoInterfaceSpring<Long, IssuingBank> issuingBankDao) {
        this.issuingBankDao = issuingBankDao;
    }

    @Transactional
    public Optional<IssuingBank> save(IssuingBankDto issuingBankDto) {
        // 1. Проверка по id, если он задан
//        if (issuingBankDto.getId() != null) {
//            Optional<IssuingBank> exitingIssuingBank = issuingBankDao.findById(issuingBankDto.getId());
//            if (exitingIssuingBank.isPresent()) {
//                log.info("Issuing Bank already exists with id {}", issuingBankDto.getId());
//                return Optional.ofNullable(IssuingBankMapper.toDto(exitingIssuingBank.get()));
//            }
//        }
        // 2. Проверка по имени (уникальное имя
        Optional<IssuingBank> exitingIssuingBank = issuingBankDao.findByValue(issuingBankDto.getAbbreviatedName());
        if (exitingIssuingBank.isPresent()) {
            log.info("Issuing Bank already exists with name {}", issuingBankDto.getAbbreviatedName());
            return exitingIssuingBank;
        }
        // Обнуляем ID, чтобы Hibernate не думал, что это уже существующий объект
        issuingBankDto.setId(null);

        IssuingBank issuingBank = IssuingBankMapper.toEntity(issuingBankDto);

        IssuingBank saved = issuingBankDao.insert(issuingBank);
        log.info("Issuing Bank {} created", saved.getId());
        return Optional.of(saved);
    }


    public Optional<IssuingBank> findById(Long id) {
        return issuingBankDao.findById(id);
    }

    public List<IssuingBank> findAll() {
       return issuingBankDao.findAll();
    }

    public Optional<IssuingBank> findByValue(String value) {
        return issuingBankDao.findByValue(value);
    }

    @Transactional
    public Optional<IssuingBank> update(Long id, IssuingBankDto issuingBankDto) {
        //если id не задан
        if (id == null) {
            log.info("Comparable IssuingBank must have an ID");
            return Optional.empty();
        }
        Optional<IssuingBank> existingIssuingBank = issuingBankDao.findById(id);
        //если нет такой сущности
        if (existingIssuingBank.isEmpty()) {
            log.info("Issuing Bank does not exist with id {}", id);
            return Optional.empty();
        }

        IssuingBank issuingBankEntity = existingIssuingBank.get();
        issuingBankEntity.setAbbreviatedName(issuingBankDto.getAbbreviatedName());
        issuingBankEntity.setBin(issuingBankDto.getBin());
        issuingBankEntity.setBic(issuingBankDto.getBic());
        issuingBankDao.update(issuingBankEntity);
        log.info("Issuing Bank {} updated", id);
        return Optional.of(issuingBankEntity);
    }

    @Transactional
    public boolean delete(Long id) {
        return issuingBankDao.delete(id);
    }

    @Transactional
    public boolean deleteAll(){
        return issuingBankDao.deleteAll();
    }

    @Transactional
    public boolean dropTable() {
        return issuingBankDao.dropTable();
    }
}
