package com.edme.pro.service;

import com.edme.pro.dao.DaoInterfaceSpring;
import com.edme.pro.dto.CardStatusDto;
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
    //private final DaoInterfaceSpring<Long, CardStatus> cardStatusDao;
    private final DaoInterfaceSpring<Long, CardStatus> cardStatusDao;

    @Autowired
    //public CardStatusDtoSpringService(DaoInterfaceSpring<Long, CardStatus> cardStatusDao) {
    public CardStatusDtoSpringService(DaoInterfaceSpring<Long, CardStatus> cardStatusDao) {
        this.cardStatusDao = cardStatusDao;
    }


    @Transactional
    public Optional<CardStatus> save(CardStatusDto cardStatusDto) {
        // Проверяем, есть ли уже сущность с таким именем
        Optional<CardStatus> existing = cardStatusDao.findByValue(cardStatusDto.getCardStatusName());
        if (existing.isPresent()) {
            log.info("CardStatus '{}' уже существует", cardStatusDto.getCardStatusName());
            return existing;
        }

        // Обнуляем ID, чтобы Hibernate не думал, что это уже существующая сущность
        cardStatusDto.setId(null);
        CardStatus entity = CardStatusMapper.toEntity(cardStatusDto);


        // Маппим в entity и сохраняем
        CardStatus saved = cardStatusDao.insert(entity);
        log.info("Saved card status '{}'", cardStatusDto.getCardStatusName());
        return Optional.of(saved);
    }


    public Optional<CardStatus> findById(Long id) {
        //return cardStatusDao.findById(id).map(CardStatusMapper::toDto);
        return cardStatusDao.findById(id);
    }
    public List<CardStatus> findAll() {
       return cardStatusDao.findAll();
    }

//    public List<CardStatusDto> findAll() {
//        return cardStatus.findAll().stream()
//                .map(CardStatusMapper::toDto)
//                .collect(Collectors.toList());
//    }


    @Transactional
    public Optional<CardStatus> update(Long id, CardStatusDto cardStatusDto) {
//        if (dto.getId() == null) {//если id не задан
//            log.info("CardStatus must have an ID");
//            //throw new IllegalArgumentException("ID должен быть задан для обновления");
//            return Optional.empty();
//        }

        if (id == null) {//если id не задан
            log.info("Comparable CardStatus must have an ID");
            //throw new IllegalArgumentException("ID должен быть задан для обновления");
            return Optional.empty();
        }

        Optional<CardStatus> existingEntityInDB = cardStatusDao.findById(id);
        if (existingEntityInDB.isEmpty()) {//если id не нашел
            log.info("CardStatus with Value = {} not found", cardStatusDto.getCardStatusName());
            return Optional.empty();
        }

        CardStatus existingEntity = existingEntityInDB.get();
        existingEntity.setCardStatusName(cardStatusDto.getCardStatusName());

        return Optional.of(existingEntity);
    }

    @Transactional
    public boolean delete(Long id) {
        return cardStatusDao.delete(id);
    }

    @Transactional
    public boolean deleteAll() {
        try {
            return cardStatusDao.deleteAll();
        } catch (Exception e) {
            log.error("Ошибка при удалении всех записей из card_statuses", e);
            throw e; // важно пробросить, чтобы Spring знал о причине rollback
        }
    }

//    public Optional<CardStatusDto> getByName(String name) {
//        return cardStatusDao.findByValue(name)
//                .map(CardStatusMapper::toDto);
//    }

    public Optional<CardStatusDto> getByValue(String name) {
        return cardStatusDao.findByValue(name)
                .map(CardStatusMapper::toDto);
    }

    @Transactional
    public boolean createTable() {
        return cardStatusDao.createTable();
    }

    @Transactional
    public boolean dropTable() {
        return cardStatusDao.dropTable();
    }

    @Transactional
    public boolean initializeTable() {
        boolean tableCreated = cardStatusDao.createTable();
        if (!tableCreated) {
            log.warn("Не удалось создать таблицу card_statuses");
            return false;
        }

        boolean valuesInserted = cardStatusDao.insertDefaultValues();
        if (!valuesInserted) {
            log.warn("Таблица создана, но значения не вставлены");
            return false;
        }

        log.info("Таблица создана и значения успешно добавлены");
        return true;
    }
}
