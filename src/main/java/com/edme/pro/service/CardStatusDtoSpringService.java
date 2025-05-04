package com.edme.pro.service;

import com.edme.pro.dao.DaoInterfaceSpring;
import com.edme.pro.dto.CardStatusDto;
import com.edme.pro.repository.CardStatusRepository;
import lombok.extern.slf4j.Slf4j;
import com.edme.pro.mapper.CardStatusMapper;
import com.edme.pro.model.CardStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CardStatusDtoSpringService {

    @Autowired
    private CardStatusRepository cardStatusRepository;


    @Transactional
    public Optional<CardStatus> save(CardStatusDto cardStatusDto) {
        // Проверяем, есть ли уже сущность с таким именем
        Optional<CardStatus> existing = cardStatusRepository.findByCardStatusName(cardStatusDto.getCardStatusName());
        if (existing.isPresent()) {
            log.info("CardStatus '{}' уже существует", cardStatusDto.getCardStatusName());
            return existing;
        }

        // Обнуляем ID, чтобы Hibernate не думал, что это уже существующая сущность
        cardStatusDto.setId(null);
        CardStatus entity = CardStatusMapper.toEntity(cardStatusDto);


        // Маппим в entity и сохраняем
        CardStatus saved = cardStatusRepository.saveAndFlush(entity);
        log.info("Saved card status '{}'", cardStatusDto.getCardStatusName());
        return Optional.of(saved);
    }


    public Optional<CardStatus> findById(Long id) {
        return cardStatusRepository.findById(id);
    }

    public List<CardStatus> findAll() {
        return cardStatusRepository.findAll();
    }


    @Transactional
    public Optional<CardStatus> update(Long id, CardStatusDto cardStatusDto) {
        Optional<CardStatus> existingEntityInDB = cardStatusRepository.findById(id);
        if (existingEntityInDB.isEmpty()) {//если id не нашел
            log.info("CardStatus with Value = {} not found", cardStatusDto.getCardStatusName());
            return Optional.empty();
        }

        CardStatus existingEntity = existingEntityInDB.get();
        existingEntity.setCardStatusName(cardStatusDto.getCardStatusName());
        cardStatusRepository.save(existingEntity);
        log.info("Updated card status '{}'", cardStatusDto.getCardStatusName());
        return Optional.of(existingEntity);
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<CardStatus> existingEntityInDB = cardStatusRepository.findById(id);
        if (existingEntityInDB.isPresent()) {
            cardStatusRepository.delete(existingEntityInDB.get());
            log.info("Deleted card status '{}'", existingEntityInDB.get().getCardStatusName());
            return true;
        } else {
            log.info("CardStatus with Value = {} not found", id);
            return false;
        }
    }

    @Transactional
    public boolean deleteAll() {
        try {
            cardStatusRepository.deleteAll();
            log.info("Deleted card status list");
            return true;
        } catch (Exception e) {
            log.error("Ошибка при удалении всех записей из card_statuses", e);
            log.error("Error when attempt to delete all CardStatus {}", e.getMessage());
            return false;
        }
    }


    @Transactional
    public boolean createTable() {
        try {
            cardStatusRepository.createTable();
            log.info("Created card status list");
            return true;
        } catch (Exception e) {
            log.error("Error when attempt to create table CardStatus: {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean dropTable() {
        try {
            cardStatusRepository.dropTable();
            log.info("Dropped card status list");
            return true;
        } catch (Exception e) {
            log.error("Error when attempt to drop table CardStatus: {}", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean initializeTable() {
       createTable();
       try {
           cardStatusRepository.insertDefaultValues();
           log.info("In CardStatus table was inserted with default values");
           return true;
       } catch (Exception e) {
           log.error("Error when attempt to initialize table CardStatus: {}", e.getMessage());
           return false;
       }
    }
}
