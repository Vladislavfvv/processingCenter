package com.edme.pro.service;

import com.edme.pro.dao.DaoInterfaceSpring;
import com.edme.pro.dto.CardDto;
import com.edme.pro.model.Account;
import com.edme.pro.model.Card;
import com.edme.pro.model.CardStatus;
import com.edme.pro.model.PaymentSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CardDtoSpringServiceTest {
    private DaoInterfaceSpring<Long, CardStatus> cardStatusDao;
    private DaoInterfaceSpring<Long, PaymentSystem> paymentSystemDao;
    private DaoInterfaceSpring<Long, Account> accountDao;
    private DaoInterfaceSpring<Long, Card> cardDao;

    private CardDtoSpringService service;

    @BeforeEach
    void setUp() {
        cardStatusDao = mock(DaoInterfaceSpring.class);
        paymentSystemDao = mock(DaoInterfaceSpring.class);
        accountDao = mock(DaoInterfaceSpring.class);
        cardDao = mock(DaoInterfaceSpring.class);
        service = new CardDtoSpringService(cardStatusDao, paymentSystemDao, accountDao, cardDao);
    }

    @Test
    void save_ReturnsEmpty_WhenCardAlreadyExists() {
        CardDto dto = new CardDto();
        dto.setCardNumber("1234");
        when(cardDao.findByValue("1234")).thenReturn(Optional.of(new Card()));

        Optional<CardDto> result = service.save(dto);
        assertTrue(result.isEmpty());
    }

    @Test
    void save_ReturnsEmpty_WhenCardNumberInvalid() {
        CardDto dto = new CardDto();
        dto.setCardNumber("invalid-number");
        when(cardDao.findByValue(anyString())).thenReturn(Optional.empty());

        Optional<CardDto> result = service.save(dto);
        assertTrue(result.isEmpty());
    }

    @Test
    void save_ReturnsEmpty_WhenDependenciesMissing() {
        CardDto dto = new CardDto();
        dto.setCardNumber("4111111111111111");
        dto.setCardStatusId(1L);
        dto.setPaymentSystemId(2L);
        dto.setAccountId(3L);

        when(cardDao.findByValue(anyString())).thenReturn(Optional.empty());
        when(cardStatusDao.findById(1L)).thenReturn(Optional.empty());
        when(paymentSystemDao.findById(2L)).thenReturn(Optional.of(new PaymentSystem()));
        when(accountDao.findById(3L)).thenReturn(Optional.of(new Account()));

        Optional<CardDto> result = service.save(dto);
        assertTrue(result.isEmpty());
    }

    @Test
    void save_SuccessfullySavesCard() {
        CardDto dto = new CardDto();
        dto.setCardNumber("4111111111111111");
        dto.setCardStatusId(1L);
        dto.setPaymentSystemId(2L);
        dto.setAccountId(3L);
        dto.setHolderName("John Doe");
        dto.setExpirationDate(LocalDate.of(2026, 1, 1));
        dto.setReceivedFromIssuingBank(LocalDateTime.parse("2024-01-01T00:00:00"));
        dto.setSentToIssuingBank(LocalDateTime.parse("2024-01-02T00:00:00"));

        when(cardDao.findByValue(anyString())).thenReturn(Optional.empty());
        when(cardStatusDao.findById(1L)).thenReturn(Optional.of(new CardStatus()));
        when(paymentSystemDao.findById(2L)).thenReturn(Optional.of(new PaymentSystem()));
        when(accountDao.findById(3L)).thenReturn(Optional.of(new Account()));
        when(cardDao.insert(any())).thenAnswer(inv -> inv.getArgument(0));

        Optional<CardDto> result = service.save(dto);
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getHolderName());
    }

    @Test
    void update_ReturnsEmpty_WhenCardNotFound() {
        when(cardDao.findById(1L)).thenReturn(Optional.empty());

        CardDto dto = new CardDto();
        Optional<Card> result = service.update(1L, dto);

        assertTrue(result.isEmpty());
    }

    @Test
    void update_ReturnsEmpty_WhenValidationFails() {
        CardDto dto = new CardDto();
        dto.setCardNumber("invalid"); // неправильный номер карты
        dto.setCardStatusId(1L);
        dto.setPaymentSystemId(2L);
        dto.setAccountId(3L);
        dto.setExpirationDate(LocalDate.of(2026, 1, 1));
        dto.setReceivedFromIssuingBank(LocalDateTime.of(2024, 1, 1, 0, 0));
        dto.setSentToIssuingBank(LocalDateTime.of(2024, 1, 2, 0, 0));
        dto.setHolderName("Invalid Holder");

        when(cardDao.findById(1L)).thenReturn(Optional.of(new Card()));
        when(cardStatusDao.findById(any())).thenReturn(Optional.of(new CardStatus()));
        when(paymentSystemDao.findById(any())).thenReturn(Optional.of(new PaymentSystem()));
        when(accountDao.findById(any())).thenReturn(Optional.of(new Account()));

        Optional<Card> result = service.update(1L, dto);

        assertTrue(result.isEmpty());
    }

    @Test
    void update_UpdatesSuccessfully() {
        Card card = new Card();
        card.setId(1L);
        when(cardDao.findById(1L)).thenReturn(Optional.of(card));
        when(cardStatusDao.findById(any())).thenReturn(Optional.of(new CardStatus()));
        when(paymentSystemDao.findById(any())).thenReturn(Optional.of(new PaymentSystem()));
        when(accountDao.findById(any())).thenReturn(Optional.of(new Account()));
        when(cardDao.update(any())).thenAnswer(inv -> inv.getArgument(0));

        CardDto dto = new CardDto();
        dto.setCardNumber("4111111111111111");
        dto.setExpirationDate(LocalDate.parse("2026-01-01"));
        dto.setReceivedFromIssuingBank(LocalDateTime.of(2024, 1, 1, 0, 0));
        dto.setSentToIssuingBank(LocalDateTime.of(2024, 1, 2, 0, 0));
        dto.setHolderName("Test Holder");
        dto.setCardStatusId(1L);
        dto.setPaymentSystemId(2L);
        dto.setAccountId(3L);

        Optional<Card> result = service.update(1L, dto);
        assertTrue(result.isPresent());
        assertEquals("4111111111111111", result.get().getCardNumber());
    }

    @Test
    void delete_CallsDao() {
        when(cardDao.delete(1L)).thenReturn(true);

        boolean result = service.delete(1L);
        assertTrue(result);
        verify(cardDao, times(1)).delete(1L);
    }

    @Test
    void findAll_ReturnsList() {
        CardStatus status = new CardStatus();
        status.setId(1L);

        PaymentSystem paymentSystem = new PaymentSystem();
        paymentSystem.setId(2L);

        Account account = new Account();
        account.setId(3L);

        Card card1 = new Card();
        card1.setId(1L);
        card1.setCardNumber("4111111111111111");
        card1.setHolderName("John Doe");
        card1.setExpirationDate(Date.valueOf("2026-01-01"));
        card1.setReceivedFromIssuingBank(Timestamp.valueOf(LocalDateTime.parse("2024-01-01T00:00:00")));
        card1.setSentToIssuingBank(Timestamp.valueOf(LocalDateTime.parse("2024-01-02T00:00:00")));
        card1.setCardStatusId(status);
        card1.setPaymentSystemId(paymentSystem);
        card1.setAccountId(account);

        Card card2 = new Card();
        card2.setId(2L);
        card2.setCardNumber("5500000000000004");
        card2.setHolderName("Jane Smith");
        card2.setExpirationDate(Date.valueOf("2026-01-01"));
        card2.setReceivedFromIssuingBank(Timestamp.valueOf(LocalDateTime.parse("2024-01-01T00:00:00")));
        card2.setSentToIssuingBank(Timestamp.valueOf(LocalDateTime.parse("2024-01-02T00:00:00")));
        card2.setCardStatusId(status);
        card2.setPaymentSystemId(paymentSystem);
        card2.setAccountId(account);

        when(cardDao.findAll()).thenReturn(List.of(card1, card2));

        List<CardDto> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getHolderName());
        assertEquals("Jane Smith", result.get(1).getHolderName());
    }


    @Test
    void findById_ReturnsDtoIfExists() {
        CardStatus status = new CardStatus();
        status.setId(1L);

        PaymentSystem paymentSystem = new PaymentSystem();
        paymentSystem.setId(1L);

        Account account = new Account();
        account.setId(1L);

        Card card1 = new Card();
        card1.setId(1L);
        card1.setCardNumber("4111111111111111");
        card1.setHolderName("John Doe");
        card1.setExpirationDate(Date.valueOf("2026-01-01"));
        card1.setReceivedFromIssuingBank(Timestamp.valueOf("2024-01-01 00:00:00"));
        card1.setSentToIssuingBank(Timestamp.valueOf("2024-01-02 00:00:00"));
        card1.setCardStatusId(status);
        card1.setPaymentSystemId(paymentSystem);
        card1.setAccountId(account);


        when(cardDao.findById(1L)).thenReturn(Optional.of(card1));

        Optional<CardDto> result = service.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("4111111111111111", result.get().getCardNumber());
    }
}