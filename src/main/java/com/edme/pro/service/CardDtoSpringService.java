package com.edme.pro.service;

import com.edme.pro.dao.DaoInterfaceSpring;
import com.edme.pro.dto.CardDto;
import com.edme.pro.repository.AccountRepository;
import com.edme.pro.repository.CardRepository;
import com.edme.pro.repository.CardStatusRepository;
import com.edme.pro.repository.PaymentSystemRepository;
import lombok.extern.slf4j.Slf4j;
import com.edme.pro.mapper.CardMapper;
import com.edme.pro.model.Account;
import com.edme.pro.model.Card;
import com.edme.pro.model.CardStatus;
import com.edme.pro.model.PaymentSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edme.pro.config.CardValidator;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CardDtoSpringService {

    @Autowired
    private CardStatusRepository statusRepository;
    @Autowired
    private PaymentSystemRepository paymentSystemRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardStatusRepository cardStatusRepository;


    @Transactional
    public Optional<CardDto> save(CardDto cardDto) {
        //проверка есть ли такой еще
        Optional<Card> existCard = cardRepository.findByCardNumber(cardDto.getCardNumber());
        if (existCard.isPresent()) {
            log.info("Card already exists: {}", cardDto.getCardNumber());
            return Optional.empty();
        }

        //валидация номера карты напоследок
        if (!CardValidator.validateCardNumber(cardDto.getCardNumber())) {
            log.warn("Invalid card number, card didnt save");
            return Optional.empty();
        }

        cardDto.setId(null);
        //иначе создаем новую
        //и прежде проверка есть ли для нее вложенные обьекты
        CardStatus cardStatus = statusRepository.findById(cardDto.getCardStatusId()).orElse(null);
        PaymentSystem paymentSystem = paymentSystemRepository.findById(cardDto.getPaymentSystemId()).orElse(null);
        Account account = accountRepository.findById(cardDto.getAccountId()).orElse(null);

        if (cardStatus == null || paymentSystem == null || account == null) {
            log.warn("One of related entities not found");
            return Optional.empty();
        }

        Card entity = CardMapper.toEntity(cardDto, cardStatus, paymentSystem, account);

        Card savedCard = cardRepository.saveAndFlush(entity);


        log.info("Saved card: {}", savedCard.getId());
        return Optional.of(CardMapper.toDto(savedCard));
    }

    ////    @Transactional
    ////    public Optional<CardDto> save(CardDto cardDto) {
    ////        if (cardDto.getId() != null) {
    ////            Optional<Card> card = cardDao.findById(cardDto.getId());
    ////            if (card.isPresent()) {
    ////                log.info("Card already exists: {}", cardDto.getId());
    ////                return Optional.empty();
    ////            }
    ////        }
    ////        //проверка есть ли такой еще
    ////        Optional<Card> existCard = cardDao.findByValue(cardDto.getCardNumber());
    ////        if (existCard.isPresent()) {
    ////            log.info("Card already exists: {}", cardDto.getCardNumber());
    ////            return Optional.empty();
    ////        }
    ////        //иначе создаем новую
    ////        //и прежде проверка есть ли для нее вложенные обьекты
    ////        CardStatus cardStatus = cardStatusDao.findById(cardDto.getCardStatusId()).orElse(null);
    ////        PaymentSystem paymentSystem = paymentSystemDao.findById(cardDto.getPaymentSystemId()).orElse(null);
    ////        Account account = accountDao.findById(cardDto.getAccountId()).orElse(null);
    ////
    ////        if (cardStatus == null || paymentSystem == null || account == null) {
    ////            log.warn("One of related entities not found");
    ////            return Optional.empty();
    ////        }
    ////
    ////        Card card = CardMapper.toEntity(cardDto, cardStatus, paymentSystem, account);
    ////
    ////        //валидация номера карты напоследок
    ////        if (!CardValidator.validateCardNumber(cardDto.getCardNumber())){
    ////            log.warn("Invalid card number, card didnt save");
    ////            return Optional.empty();
    ////        }
    ////        card.setId(null);//обнуляем перед сохранением
    ////        Card cardSaved = cardDao.insert(card);
    ////        log.info("Saved card: {}", cardDto);
    ////        return Optional.of(CardMapper.toDto(cardSaved));
    ////    }
    //
    //    @Transactional
    //    public Optional<CardDto> save(CardDto cardDto) {
    ////        if (cardDto.getId() != null) {
    ////            Optional<Card> card = cardDao.findById(cardDto.getId());
    ////            if (card.isPresent()) {
    ////                log.info("Card already exists: {}", cardDto.getId());
    ////                return Optional.empty();
    ////            }
    ////        }
    //
    //        //проверка есть ли такой еще
    //        Optional<Card> existCard = cardDao.findByValue(cardDto.getCardNumber());
    //        if (existCard.isPresent()) {
    //            log.info("Card already exists: {}", cardDto.getCardNumber());
    //            return Optional.empty();
    //        }
    //
    //        //валидация номера карты напоследок
    //        if (!CardValidator.validateCardNumber(cardDto.getCardNumber())){
    //            log.warn("Invalid card number, card didnt save");
    //            return Optional.empty();
    //        }
    //
    //        cardDto.setId(null);
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

    /// /
    //        card.setId(null);//обнуляем перед сохранением
    //        Card cardSaved = cardDao.insert(card);
    //        log.info("Saved card: {}", cardDto);
    //        return Optional.of(CardMapper.toDto(cardSaved));
    //    }
    //
    @Transactional
    public Optional<Card> save2(CardDto cardDto) {

        if (cardDto.getId() != null) {
            Optional<Card> card = cardRepository.findById(cardDto.getId());
            if (card.isPresent()) {
                log.info("Card already exists: {}", cardDto.getId());
                return Optional.empty();
            }
        }
        //проверка есть ли такой еще
        Optional<Card> existCard = cardRepository.findByCardNumber(cardDto.getCardNumber());
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
        CardStatus cardStatus = cardStatusRepository.findById(cardDto.getCardStatusId()).orElse(null);
        PaymentSystem paymentSystem = paymentSystemRepository.findById(cardDto.getPaymentSystemId()).orElse(null);
        Account account = accountRepository.findById(cardDto.getAccountId()).orElse(null);

        if (cardStatus == null || paymentSystem == null || account == null) {
            log.warn("One of related entities not found");
            return Optional.empty();
        }
        cardDto.setId(null);//обнуляем перед сохранением
        Card card = CardMapper.toEntity(cardDto, cardStatus, paymentSystem, account);

//

        Card cardSaved = cardRepository.save(card);
        log.info("Saved card: {}", cardDto);
        return Optional.of(cardSaved);
    }

//        public Optional<CardDto> findById (Long id){
//            return cardRepository.findById(id).map(CardMapper::toDto);
//        }

    public Optional<CardDto> findById(Long id){
        return cardRepository.findByIdWithRelations(id)
                .map(CardMapper::toDto);
    }

//        public List<CardDto> findAll () {
//            return cardRepository.findAll()
//                    .stream()
//                    .map(CardMapper::toDto)
//                    .collect(Collectors.toList());
//            // return cardDao.findAll();
//        }

public List<CardDto> findAll() {
    return cardRepository.findAllWithDetails().stream()
            .map(CardMapper::toDto)
            .toList();
}

//    public Optional<CardDto> findByValue(String value) {
//        return cardDao.findByValue(value).map(CardMapper::toDto);
//    }

        public Optional<Card> update (Long id, CardDto cardDto){
            Optional<Card> existingCard = cardRepository.findById(id);
            if (existingCard.isEmpty()) {
                log.info("Card does not exist: {}", id);
                return Optional.empty();
            }

            if (!CardValidator.validateCardNumber(cardDto.getCardNumber())) {
                log.warn("Invalid card number, card didn't update");
                return Optional.empty();
            }

            Card card = existingCard.get();
            CardStatus cardStatus = statusRepository.findById(cardDto.getCardStatusId()).get();
            PaymentSystem paymentSystem = paymentSystemRepository.findById(cardDto.getPaymentSystemId()).get();
            Account account = accountRepository.findById(cardDto.getAccountId()).get();

            // Обновляем поля существующего объекта
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


//        //валидация номера карты напоследок
//        if (!CardValidator.validateCardNumber(cardDto.getCardNumber())){
//            log.warn("Invalid card number, card didnt update");
//            return Optional.empty();
//        }

            // Обновляем в базе
            Card updatedCard = cardRepository.save(card);

            log.info("Updated card: {}", updatedCard);
            return Optional.of(updatedCard);
        }


        @Transactional
        public boolean delete (Long id){
            Optional<Card> card = cardRepository.findById(id);
            if (card.isPresent()) {
                cardRepository.delete(card.get());
                log.info("Card deleted: {}", id);
                return true;
            } else {
                log.info("Card does not exist: {}", id);
                return false;
            }
        }

    @Transactional
    public Optional<Card> deleteAndReturnCard(Long id) {
        Optional<Card> card = cardRepository.findById(id);
        card.ifPresent(cardRepository::delete);
        if (card.isPresent()) {
            cardRepository.delete(card.get());
            log.info("Card deleted: {}", id);
            return card;
        } else {
            log.info("Card does not exist: {}", id);
            return Optional.empty();
        }
        // card.ifPresent(cardRepository::delete);
        //    return card;
    }

        @Transactional
        public boolean deleteAll () {
            try {
                cardRepository.deleteAll();
                log.warn("All cards deleted:");
                return true;
            } catch (Exception e) {
                log.error("Ошибка при удалении всех записей из cards", e);
                return false;
            }
        }

        @Transactional
        public boolean dropTable () {
            try {
                cardRepository.dropTable();
                log.info("Table Card dropped");
                return true;
            } catch (Exception e) {
                log.error("Error dropping account table: {}", e.getMessage());
                return false;
            }
        }

        @Transactional
        public boolean createTable () {
            try {
                cardRepository.createTable();
                log.info("Table Card created");
                return true;
            } catch (Exception e) {
                log.error("Error creating account table: {}", e.getMessage());
                return false;
            }
        }

        @Transactional
        public boolean initializeTable () {
            cardRepository.createTable();
            try {
                cardRepository.insertDefaultValues();
                log.info("Table Card initialized");
                return true;
            } catch (Exception e) {
                log.error("Error initializing account table: {}", e.getMessage());
                return false;
            }
        }
    }
