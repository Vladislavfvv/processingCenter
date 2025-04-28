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
    public Optional<IssuingBankDto> save(IssuingBankDto issuingBankDto) {
        // 1. Проверка по id, если он задан
        if (issuingBankDto.getId() != null) {
            Optional<IssuingBank> exitingIssuingBank = issuingBankDao.findById(issuingBankDto.getId());
            if (exitingIssuingBank.isPresent()) {
                log.info("Issuing Bank already exists with id {}", issuingBankDto.getId());
                return Optional.ofNullable(IssuingBankMapper.toDto(exitingIssuingBank.get()));
            }
        }
        // 2. Проверка по имени (уникальное имя
        Optional<IssuingBank> exitingIssuingBank = issuingBankDao.findByValue(issuingBankDto.getAbbreviatedName());
        if (exitingIssuingBank.isPresent()) {
            log.info("Issuing Bank already exists with name {}", issuingBankDto.getAbbreviatedName());
            return Optional.ofNullable(IssuingBankMapper.toDto(exitingIssuingBank.get()));
        }

        IssuingBank issuingBank = IssuingBankMapper.toEntity(issuingBankDto);
        IssuingBank saved = issuingBankDao.insert(issuingBank);
        log.info("Issuing Bank {} created", saved.getId());
        return Optional.ofNullable(IssuingBankMapper.toDto(saved));
    }


    public Optional<IssuingBankDto> findById(Long id) {
        return issuingBankDao.findById(id).map(IssuingBankMapper::toDto);
    }

    public List<IssuingBankDto> findAll() {
        try {
            log.info("Issuing Bank findAll");
            return issuingBankDao.findAll()
                    .stream()
                    .map(IssuingBankMapper::toDto)
                    .collect(Collectors.toList());
        }catch (Exception e) {
            log.error("Issuing Bank findAll error", e);
            return new ArrayList<>();
        }
    }

    public Optional<IssuingBankDto> findByValue(String value) {
        return issuingBankDao.findByValue(value).map(IssuingBankMapper::toDto);
    }

    @Transactional
    public Optional<IssuingBankDto> update(IssuingBankDto issuingBankDto) {
        //если id не задан
        if (issuingBankDto.getId() == null) {
            log.info("Issuing Bank id is null");
            return Optional.empty();
        }
        Optional<IssuingBank> existingIssuingBank = issuingBankDao.findById(issuingBankDto.getId());
        //если нет такой сущности
        if (existingIssuingBank.isEmpty()) {
            log.info("Issuing Bank does not exist with id {}", issuingBankDto.getId());
            return Optional.empty();
        }
        IssuingBank issuingBankEntity = existingIssuingBank.get();
        issuingBankEntity.setAbbreviatedName(issuingBankDto.getAbbreviatedName());
        issuingBankEntity.setBin(issuingBankDto.getBin());
        issuingBankEntity.setBic(issuingBankDto.getBic());
        issuingBankDao.update(issuingBankEntity);
        log.info("Issuing Bank {} updated", issuingBankDto.getId());
        return Optional.ofNullable(IssuingBankMapper.toDto(issuingBankEntity));
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
