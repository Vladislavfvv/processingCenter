package com.edme.pro.service;

import com.edme.pro.dao.DaoInterfaceSpring;
import com.edme.pro.dto.IssuingBankDto;
import com.edme.pro.repository.IssuingBankRepository;
import lombok.extern.slf4j.Slf4j;
import com.edme.pro.mapper.IssuingBankMapper;
import com.edme.pro.model.IssuingBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IssuingBankDtoSpringService {
    @Autowired
    private IssuingBankRepository issuingBankRepository;


//    private final DaoInterfaceSpring<Long, IssuingBank> issuingBankDao;
//
//    public IssuingBankDtoSpringService(DaoInterfaceSpring<Long, IssuingBank> issuingBankDao) {
//        this.issuingBankDao = issuingBankDao;
//    }

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
        Optional<IssuingBank> exitingIssuingBank = issuingBankRepository.findByBic(issuingBankDto.getBic());
        if (exitingIssuingBank.isPresent()) {
            log.info("Issuing Bank already exists with name {}", issuingBankDto.getAbbreviatedName());
            return exitingIssuingBank;
        }
        // Обнуляем ID, чтобы Hibernate не думал, что это уже существующий объект
        issuingBankDto.setId(null);

        IssuingBank issuingBank = IssuingBankMapper.toEntity(issuingBankDto);

        IssuingBank saved = issuingBankRepository.saveAndFlush(issuingBank);
        log.info("Issuing Bank {} created", saved.getId());
        return Optional.of(saved);
    }


    public Optional<IssuingBank> findById(Long id) {
        return issuingBankRepository.findById(id);
    }

    public List<IssuingBank> findAll() {
       return issuingBankRepository.findAll();
    }

//    public Optional<IssuingBank> findByValue(String value) {
//        return issuingBankDao.findByValue(value);
//    }

    @Transactional
    public Optional<IssuingBank> update(Long id, IssuingBankDto issuingBankDto) {
        //если id не задан
//        if (id == null) {
//            log.info("Comparable IssuingBank must have an ID");
//            return Optional.empty();
//        }
        Optional<IssuingBank> existingIssuingBank = issuingBankRepository.findById(id);
        //если нет такой сущности
        if (existingIssuingBank.isEmpty()) {
            log.info("Issuing Bank does not exist with id {}", id);
            return Optional.empty();
        }

        IssuingBank issuingBankEntity = existingIssuingBank.get();
        issuingBankEntity.setAbbreviatedName(issuingBankDto.getAbbreviatedName());
        issuingBankEntity.setBin(issuingBankDto.getBin());
        issuingBankEntity.setBic(issuingBankDto.getBic());
        issuingBankRepository.save(issuingBankEntity);
        log.info("Issuing Bank {} updated", id);
        return Optional.of(issuingBankEntity);
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<IssuingBank> exitingIssuingBank = issuingBankRepository.findById(id);
        if (exitingIssuingBank.isPresent()) {
            issuingBankRepository.delete(exitingIssuingBank.get());
            log.info("Issuing Bank {} deleted", id);
            return true;
        } else {
            log.info("Issuing Bank does not exist with id {}", id);
            return false;
        }
    }

    @Transactional
    public boolean deleteAll(){
        try {
            issuingBankRepository.deleteAll();
            log.info("Issuing Bank List deleted");
            return true;
        } catch (Exception e) {
            log.error("Ошибка при удалении всех записей из issuing_banks", e);
//            throw e;
            return false;
        }
    }

    @Transactional
    public boolean dropTable() {
        try {
            issuingBankRepository.dropTable();
            log.info("Issuing Bank Drop Table");
            return true;
        } catch (Exception e) {
            log.error("Error when attempt to drop table: {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean createTable() {
        try {
            issuingBankRepository.createTable();
            log.info("Issuing Bank Create Table");
            return true;
        } catch (Exception e) {
            log.error("Error when attempt to create table: {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean initializeTable() {
        createTable();
        try {
            issuingBankRepository.insertDefaultValues();
            log.info("Issuing Bank Insert DefaultValues");
            return true;
        }catch (Exception e) {
            log.error("Error when attempt to insert to table: {}", e.getMessage());
            return false;
        }
    }
}
