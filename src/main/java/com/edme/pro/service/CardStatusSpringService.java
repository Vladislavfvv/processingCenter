package com.edme.pro.service;

import com.edme.pro.dao.DaoInterfaceSpring;

import com.edme.pro.dto.CardStatusDto;
import com.edme.pro.mapper.CardStatusMapper;
import com.edme.pro.model.CardStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class CardStatusSpringService {
    private final DaoInterfaceSpring<Long, CardStatus> cardStatusDao;

    public CardStatusSpringService(DaoInterfaceSpring<Long, CardStatus> cardStatusDao) {
        this.cardStatusDao = cardStatusDao;
    }


//    public Optional<CardStatus> save(String cardStatusName) {
//        if (cardStatusDao.findByValue(cardStatusName).isPresent()) {
//            return Optional.empty();
//        }
//        CardStatus cardStatus = new CardStatus();
//        cardStatus.setCardStatusName(cardStatusName);
//        CardStatus savedCardStatus = cardStatusDao.insert(cardStatus);
//        return Optional.ofNullable(savedCardStatus);
//    }

    public Optional<CardStatusDto> save(CardStatusDto cardStatusDto) {
        Optional<CardStatus> isExist = cardStatusDao.findByValue(cardStatusDto.getCardStatusName());
        if (isExist.isPresent()) {
            log.info("Card status already exists: " + cardStatusDto.getCardStatusName());
            return Optional.empty();
        }
        CardStatus cardStatus = CardStatusMapper.toEntity(cardStatusDto);
        CardStatus savedCardStatus = cardStatusDao.insert(cardStatus);
        log.info("Saved card status: {}", savedCardStatus);
        return Optional.of(CardStatusMapper.toDto(savedCardStatus));
    }


    public Optional<CardStatus> getById(Long id) {
        return cardStatusDao.findById(id);
    }


    public List<CardStatus> getAll() {
        return cardStatusDao.findAll();
    }


//    public Optional<CardStatus> update(CardStatus entity) {
//        Optional<CardStatus> existingCardStatus = cardStatusDao.findById(entity.getId());
//        if (existingCardStatus.isEmpty()) {
//            return Optional.empty();
//        }
//        CardStatus cardStatus = cardStatusDao.update(entity);
//        return Optional.ofNullable(cardStatus);
//    }

    public Optional<CardStatusDto> update(CardStatusDto cardStatusDto) {
        Optional<CardStatus> existingCardStatus = cardStatusDao.findByValue(cardStatusDto.getCardStatusName());
        if (existingCardStatus.isEmpty()) {
            log.info("Card status does not exists: " + cardStatusDto.getCardStatusName());
            return Optional.empty();
        }
        CardStatus cardStatus = CardStatusMapper.toEntity(cardStatusDto);
        CardStatus updatedCardStatus = cardStatusDao.update(cardStatus);
        log.info("Updated card status: {}", updatedCardStatus);
        return Optional.of(CardStatusMapper.toDto(updatedCardStatus));
    }


    public boolean delete(Long id) {
        return cardStatusDao.delete(id);
    }


    public boolean deleteALL() {
        return cardStatusDao.deleteAll();
    }


    public Optional<CardStatus> getByName(String name) {
        return cardStatusDao.findByValue(name);
    }


    public boolean dropTable() {
        return cardStatusDao.dropTable();
    }
}
