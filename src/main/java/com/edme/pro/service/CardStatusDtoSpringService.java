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
    private final DaoInterfaceSpring<Long, CardStatus> cardStatusDao;

    public CardStatusDtoSpringService(DaoInterfaceSpring<Long, CardStatus> cardStatusDao) {
        this.cardStatusDao = cardStatusDao;
    }


    @Transactional
    public Optional<CardStatusDto> save(CardStatusDto dto) {
        // Проверяем, есть ли уже сущность с таким именем
        Optional<CardStatus> existing = cardStatusDao.findByValue(dto.getCardStatusName());
        if (existing.isPresent()) {
            log.info("CardStatus '{}' уже существует", dto.getCardStatusName());
            return Optional.of(CardStatusMapper.toDto(existing.get()));
        }

        CardStatus entity = CardStatusMapper.toEntity(dto);
        // Обнуляем ID, чтобы Hibernate не думал, что это уже существующая сущность
       // dto.setId(null);

        // Маппим в entity и сохраняем
        CardStatus saved = cardStatusDao.insert(entity);
        log.info("Saved card status '{}'", dto.getCardStatusName());
        return Optional.of(CardStatusMapper.toDto(saved));
    }


    public Optional<CardStatusDto> findById(Long id) {
        return cardStatusDao.findById(id).map(CardStatusMapper::toDto);
    }
//    public List<CardStatusDto> findAll() {
//       return cardStatusDao.findAll();
//    }

    public List<CardStatusDto> findAll() {
        return cardStatusDao.findAll().stream()
                .map(CardStatusMapper::toDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public Optional<CardStatusDto> update(CardStatusDto dto) {
        if (dto.getId() == null) {//если id не задан
            log.info("CardStatus must have an ID");
            //throw new IllegalArgumentException("ID должен быть задан для обновления");
            return Optional.empty();
        }

        Optional<CardStatus> existingEntityOpt = cardStatusDao.findById(dto.getId());
        if (existingEntityOpt.isEmpty()) {//если id не нашел
            log.info("CardStatus with ID = {} not found", dto.getId());
            return Optional.empty();
        }

        CardStatus existingEntity = existingEntityOpt.get();
        existingEntity.setCardStatusName(dto.getCardStatusName());

        return Optional.of(CardStatusMapper.toDto(existingEntity));
    }

    @Transactional
    public boolean delete(Long id) {
        return cardStatusDao.delete(id);
    }

    @Transactional
    public boolean deleteAll() {
        return cardStatusDao.deleteAll();
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
    public boolean dropTable() {
        return cardStatusDao.dropTable();
    }
}
