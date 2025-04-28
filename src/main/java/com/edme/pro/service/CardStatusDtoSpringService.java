package com.edme.pro.service;

import com.edme.pro.dao.DaoInterfaceSpring;
import com.edme.pro.dto.CardDto;
import com.edme.pro.dto.CardStatusDto;
import com.edme.pro.mapper.CardMapper;
import lombok.extern.slf4j.Slf4j;
import com.edme.pro.mapper.CardStatusMapper;
import com.edme.pro.model.CardStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CardStatusDtoSpringService {
    //private final DaoInterfaceSpring<Long, CardStatus> cardStatusDao;
    private final DaoInterfaceSpring<Long, CardStatus> cardStatus;

    //public CardStatusDtoSpringService(DaoInterfaceSpring<Long, CardStatus> cardStatusDao) {
    public CardStatusDtoSpringService(DaoInterfaceSpring<Long, CardStatus> cardStatus) {
        this.cardStatus = cardStatus;
    }


    @Transactional
    public Optional<CardStatus> save(CardStatusDto cardStatusDto) {
        // Проверяем, есть ли уже сущность с таким именем
        Optional<CardStatus> existing = cardStatus.findByValue(cardStatusDto.getCardStatusName());
        if (existing.isPresent()) {
            log.info("CardStatus '{}' уже существует", cardStatusDto.getCardStatusName());
            return existing;
        }

        CardStatus entity = CardStatusMapper.toEntity(cardStatusDto);
        // Обнуляем ID, чтобы Hibernate не думал, что это уже существующая сущность
        // dto.setId(null);

        // Маппим в entity и сохраняем
        CardStatus saved = cardStatus.insert(entity);
        log.info("Saved card status '{}'", cardStatusDto.getCardStatusName());
        return Optional.of(saved);
    }


    public Optional<CardStatus> findById(Long id) {
        //return cardStatusDao.findById(id).map(CardStatusMapper::toDto);
        return cardStatus.findById(id);
    }
    public List<CardStatus> findAll() {
       return cardStatus.findAll();
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

        Optional<CardStatus> existingEntityInDB = cardStatus.findById(id);
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
        return cardStatus.delete(id);
    }

    @Transactional
    public boolean deleteAll() {
        return cardStatus.deleteAll();
    }

//    public Optional<CardStatusDto> getByName(String name) {
//        return cardStatusDao.findByValue(name)
//                .map(CardStatusMapper::toDto);
//    }

    public Optional<CardStatusDto> getByValue(String name) {
        return cardStatus.findByValue(name)
                .map(CardStatusMapper::toDto);
    }

    @Transactional
    public boolean dropTable() {
        return cardStatus.dropTable();
    }
}
