package com.edme.pro.service;

import com.edme.pro.dao.DaoInterfaceSpring;
import com.edme.pro.dto.CardDto;
import lombok.extern.slf4j.Slf4j;
import com.edme.pro.mapper.CardMapper;
import com.edme.pro.model.Account;
import com.edme.pro.model.Card;
import com.edme.pro.model.CardStatus;
import com.edme.pro.model.PaymentSystem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edme.pro.config.CardValidator;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CardDtoSpringService {
    private final DaoInterfaceSpring<Long, CardStatus> cardStatusDao;
    private final DaoInterfaceSpring<Long, PaymentSystem> paymentSystemDao;
    private final DaoInterfaceSpring<Long, Account> accountDao;
    private final DaoInterfaceSpring<Long, Card> cardDao;

    public CardDtoSpringService(DaoInterfaceSpring<Long, CardStatus> cardStatusDao, DaoInterfaceSpring<Long, PaymentSystem> paymentSystemDao, DaoInterfaceSpring<Long, Account> accountDao, DaoInterfaceSpring<Long, Card> cardDao) {
        this.cardStatusDao = cardStatusDao;
        this.paymentSystemDao = paymentSystemDao;
        this.accountDao = accountDao;
        this.cardDao = cardDao;
    }

//    @Transactional
//    public Optional<CardDto> save(CardDto cardDto) {
//        if (cardDto.getId() != null) {
//            Optional<Card> card = cardDao.findById(cardDto.getId());
//            if (card.isPresent()) {
//                log.info("Card already exists: {}", cardDto.getId());
//                return Optional.empty();
//            }
//        }
//        //проверка есть ли такой еще
//        Optional<Card> existCard = cardDao.findByValue(cardDto.getCardNumber());
//        if (existCard.isPresent()) {
//            log.info("Card already exists: {}", cardDto.getCardNumber());
//            return Optional.empty();
//        }
//        //иначе создаем новую
//        //и прежде проверка есть ли для нее вложенные обьекты
//        CardStatus cardStatus = cardStatusDao.findById(cardDto.getCardStatusId()).orElse(null);
//        PaymentSystem paymentSystem = paymentSystemDao.findById(cardDto.getPaymentSystemId()).orElse(null);
//        Account account = accountDao.findById(cardDto.getAccountId()).orElse(null);
//
//        if (cardStatus == null || paymentSystem == null || account == null) {
//            log.warn("One of related entities not found");
//            return Optional.empty();
//        }
//
//        Card card = CardMapper.toEntity(cardDto, cardStatus, paymentSystem, account);
//
//        //валидация номера карты напоследок
//        if (!CardValidator.validateCardNumber(cardDto.getCardNumber())){
//            log.warn("Invalid card number, card didnt save");
//            return Optional.empty();
//        }
//        card.setId(null);//обнуляем перед сохранением
//        Card cardSaved = cardDao.insert(card);
//        log.info("Saved card: {}", cardDto);
//        return Optional.of(CardMapper.toDto(cardSaved));
//    }

    @Transactional
    public Optional<CardDto> save(CardDto cardDto) {
//        if (cardDto.getId() != null) {
//            Optional<Card> card = cardDao.findById(cardDto.getId());
//            if (card.isPresent()) {
//                log.info("Card already exists: {}", cardDto.getId());
//                return Optional.empty();
//            }
//        }

        //проверка есть ли такой еще
        Optional<Card> existCard = cardDao.findByValue(cardDto.getCardNumber());
        if (existCard.isPresent()) {
            log.info("Card already exists: {}", cardDto.getCardNumber());
            return Optional.empty();
        }

        //валидация номера карты напоследок
        if (!CardValidator.validateCardNumber(cardDto.getCardNumber())){
            log.warn("Invalid card number, card didnt save");
            return Optional.empty();
        }

        cardDto.setId(null);
        //иначе создаем новую
        //и прежде проверка есть ли для нее вложенные обьекты
        CardStatus cardStatus = cardStatusDao.findById(cardDto.getCardStatusId()).orElse(null);
        PaymentSystem paymentSystem = paymentSystemDao.findById(cardDto.getPaymentSystemId()).orElse(null);
        Account account = accountDao.findById(cardDto.getAccountId()).orElse(null);

        if (cardStatus == null || paymentSystem == null || account == null) {
            log.warn("One of related entities not found");
            return Optional.empty();
        }

        Card card = CardMapper.toEntity(cardDto, cardStatus, paymentSystem, account);

//
        card.setId(null);//обнуляем перед сохранением
        Card cardSaved = cardDao.insert(card);
        log.info("Saved card: {}", cardDto);
        return Optional.of(CardMapper.toDto(cardSaved));
    }

    @Transactional
    public Optional<Card> save2(CardDto cardDto) {
//        if (cardDto.getId() != null) {
//            Optional<Card> card = cardDao.findById(cardDto.getId());
//            if (card.isPresent()) {
//                log.info("Card already exists: {}", cardDto.getId());
//                return Optional.empty();
//            }
//        }

        //проверка есть ли такой еще
        Optional<Card> existCard = cardDao.findByValue(cardDto.getCardNumber());
        if (existCard.isPresent()) {
            log.info("Card already exists: {}", cardDto.getCardNumber());
            return Optional.empty();
        }

        //валидация номера карты напоследок
        if (!CardValidator.validateCardNumber(cardDto.getCardNumber())){
            log.warn("Invalid card number, card didnt save");
            return Optional.empty();
        }
        cardDto.setId(null);
        //иначе создаем новую
        //и прежде проверка есть ли для нее вложенные обьекты
        CardStatus cardStatus = cardStatusDao.findById(cardDto.getCardStatusId()).orElse(null);
        PaymentSystem paymentSystem = paymentSystemDao.findById(cardDto.getPaymentSystemId()).orElse(null);
        Account account = accountDao.findById(cardDto.getAccountId()).orElse(null);

        if (cardStatus == null || paymentSystem == null || account == null) {
            log.warn("One of related entities not found");
            return Optional.empty();
        }
        cardDto.setId(null);//обнуляем перед сохранением
        Card card = CardMapper.toEntity(cardDto, cardStatus, paymentSystem, account);

//

        Card cardSaved = cardDao.insert(card);
        log.info("Saved card: {}", cardDto);
        return Optional.of(cardSaved);
    }

    public Optional<CardDto> findById(Long id) {
        return cardDao.findById(id).map(CardMapper::toDto);
    }

    public List<CardDto> findAll() {
        return cardDao.findAll()
                .stream()
                .map(CardMapper::toDto)
                .collect(Collectors.toList());
        // return cardDao.findAll();
    }

    public Optional<CardDto> findByValue(String value) {
        return cardDao.findByValue(value).map(CardMapper::toDto);
    }

    public Optional<Card> update(Long id, CardDto cardDto) {
        if (id == null) {
            log.info("Comparable Card must have an ID");
            return Optional.empty();
        }
        Optional<Card> existingCard = cardDao.findById(id);
        if (existingCard.isEmpty()) {
           log.info("Card does not exist: {}", id);
           return Optional.empty();
        }


        CardStatus cardStatus = cardStatusDao.findById(cardDto.getCardStatusId()).orElse(null);
        PaymentSystem paymentSystem = paymentSystemDao.findById(cardDto.getPaymentSystemId()).orElse(null);
        Account account = accountDao.findById(cardDto.getAccountId()).orElse(null);

        if (cardStatus == null || paymentSystem == null || account == null) {
            log.info("One of the required references is null. cardStatus: {}, paymentSystem: {}, account: {}",
                    cardStatus, paymentSystem, account);
            return Optional.empty();
        }

        // Обновляем поля существующего объекта
        Card card = existingCard.get();
        card.setCardNumber(cardDto.getCardNumber());
        //card.setExpirationDate(cardDto.getExpirationDate());
        card.setExpirationDate(Date.valueOf(cardDto.getExpirationDate()));
        card.setHolderName(cardDto.getHolderName());
        card.setCardStatusId(cardStatus);
        card.setPaymentSystemId(paymentSystem);
        card.setAccountId(account);
        //card.setReceivedFromIssuingBank(cardDto.getReceivedFromIssuingBank());
        card.setReceivedFromIssuingBank(Timestamp.valueOf(cardDto.getReceivedFromIssuingBank()));
        //card.setSentToIssuingBank(cardDto.getSentToIssuingBank());
        card.setSentToIssuingBank(Timestamp.valueOf(cardDto.getSentToIssuingBank()));


        //валидация номера карты напоследок
        if (!CardValidator.validateCardNumber(cardDto.getCardNumber())){
            log.warn("Invalid card number, card didnt update");
            return Optional.empty();
        }

        // Обновляем в базе
        Card updatedCard = cardDao.update(card);

        log.info("Updated card: {}", updatedCard);
        return Optional.of(updatedCard);
    }

    @Transactional
    public boolean delete(Long id) {
        boolean result = cardDao.delete(id);
        log.warn("Card with id = {} deleted: {}", id, result);
        return result;
    }

    @Transactional
    public boolean deleteAll() {
        boolean result = cardDao.deleteAll();
        log.warn("All cards deleted: {}", result);
        return result;
    }

    @Transactional
    public boolean dropTable() {
        boolean result = cardDao.dropTable();
        log.warn("Table was deleted: {}", result);
        return result;
    }

}
