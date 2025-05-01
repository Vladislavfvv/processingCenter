package com.edme.pro.controller;

import com.edme.pro.dto.CardDto;
import com.edme.pro.model.Account;
import com.edme.pro.model.Card;
import com.edme.pro.model.CardStatus;
import com.edme.pro.model.PaymentSystem;
import com.edme.pro.service.CardDtoSpringService;
import javafx.util.converter.LocalDateStringConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CardControllerTest {
    @Mock
    private CardDtoSpringService cardService; // Мок сервиса

    @InjectMocks
    private CardController cardController; // Контроллер, в который инжектируем мок

    @BeforeEach
    void setUp() {
        // Инициализация моков перед каждым тестом
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCards_success() {
        // Подготовка тестовых данных
        CardDto cardDto1 = new CardDto(1L, "1234", null, "John Doe", 1L, 1L, 1L, null, null);
        CardDto cardDto2 = new CardDto(2L, "5678", null, "Jane Smith", 2L, 2L, 2L, null, null);
        when(cardService.findAll()).thenReturn(List.of(cardDto1, cardDto2)); // Мокируем метод findAll

        // Вызов метода контроллера
        ResponseEntity<List<CardDto>> response = cardController.getAllCardsList();

        // Проверки
        assertEquals(200, response.getStatusCodeValue()); // Ожидаем HTTP 200
        assertNotNull(response.getBody()); // Тело ответа не должно быть пустым
        assertEquals(2, response.getBody().size()); // Должны быть две карточки
    }

    @Test
    void testGetCardById_found() {
        // Подготовка тестовых данных
        CardDto cardDto = new CardDto(1L, "1234", null, "John Doe", 1L, 1L, 1L, null, null);
        when(cardService.findById(1L)).thenReturn(Optional.of(cardDto)); // Мокируем успешный поиск

        // Вызов метода контроллера
        ResponseEntity<?> response = cardController.getCardById(1L);

        // Проверки
        assertEquals(200, response.getStatusCodeValue()); // Ожидаем HTTP 200
        assertNotNull(response.getBody()); // Тело ответа не должно быть пустым
        assertEquals(cardDto, response.getBody()); // Ответ должен содержать карточку
    }

    @Test
    void testGetCardById_notFound() {
        // Мокируем случай, когда карточка не найдена
        when(cardService.findById(1L)).thenReturn(Optional.empty());

        // Вызов метода контроллера
        ResponseEntity<?> response = cardController.getCardById(1L);

        // Проверки
        assertEquals(404, response.getStatusCodeValue()); // Ожидаем HTTP 404
        assertNull(response.getBody()); // Тело ответа должно быть пустым
    }

    @Test
    void testCreateCard_success() {
        // Подготовка тестовых данных
        CardStatus cardStatus = new CardStatus();
        cardStatus.setId(1L);

        PaymentSystem paymentSystem = new PaymentSystem();
        paymentSystem.setId(1L);

        Account account = new Account();
        account.setId(1L);
        CardDto cardDto = new CardDto(1L, "1234", LocalDate.of(2025, 5, 5), "John Doe", 1L, 1L, 1L, null, null);
        Card card = new Card(1L, "1234", Date.valueOf(LocalDate.of(2025, 5, 5)), "John Doe", cardStatus, paymentSystem, account, null, null); // Сущность Card
        when(cardService.save2(cardDto)).thenReturn(Optional.of(card)); // Мокируем успешное сохранение

        // Вызов метода контроллера
        ResponseEntity<?> response = cardController.createCard(cardDto);

        // Проверки
        assertEquals(200, response.getStatusCodeValue()); // Ожидаем HTTP 200
        assertNotNull(response.getBody()); // Тело ответа не должно быть пустым
        assertEquals(card, response.getBody()); // Ответ должен содержать сохранённую карточку
    }

    @Test
    void testCreateCard_badRequest() {
        // Подготовка тестовых данных
        CardDto cardDto = new CardDto(1L, "1234", null, "John Doe", 1L, 1L, 1L, null, null);
        when(cardService.save2(cardDto)).thenReturn(Optional.empty()); // Мокируем ошибку сохранения

        // Вызов метода контроллера
        ResponseEntity<?> response = cardController.createCard(cardDto);

        // Проверки
        assertEquals(400, response.getStatusCodeValue()); // Ожидаем HTTP 400
        assertEquals("Card с таким названием уже существует", response.getBody()); // Сообщение ошибки
    }

    @Test
    void testUpdateCard_success() {
        // Подготовка тестовых данных
        CardStatus cardStatus = new CardStatus();
        cardStatus.setId(1L);

        PaymentSystem paymentSystem = new PaymentSystem();
        paymentSystem.setId(1L);

        Account account = new Account();
        account.setId(1L);
        CardDto cardDto = new CardDto(1L, "1234", null, "John Doe", 1L, 1L, 1L, null, null);
        Card card = new Card(1L, "1234", Date.valueOf(LocalDate.of(2025, 5, 5)), "John Doe", cardStatus, paymentSystem, account, null, null);
        when(cardService.update(1L, cardDto)).thenReturn(Optional.of(card)); // Мокируем успешное обновление

        // Вызов метода контроллера
        ResponseEntity<Card> response = cardController.updateCard(1L, cardDto);

        // Проверки
        assertEquals(200, response.getStatusCodeValue()); // Ожидаем HTTP 200
        assertNotNull(response.getBody()); // Тело ответа не должно быть пустым
        assertEquals(card, response.getBody()); // Ответ должен содержать обновлённую карточку
    }

    @Test
    void testUpdateCard_notFound() {
        // Подготовка тестовых данных
        CardDto cardDto = new CardDto(1L, "1234", null, "John Doe", 1L, 1L, 1L, null, null);
        when(cardService.update(1L, cardDto)).thenReturn(Optional.empty()); // Мокируем неудачное обновление

        // Вызов метода контроллера
        ResponseEntity<Card> response = cardController.updateCard(1L, cardDto);

        // Проверки
        assertEquals(404, response.getStatusCodeValue()); // Ожидаем HTTP 404
        assertNull(response.getBody()); // Тело ответа должно быть пустым
    }

    @Test
    void testDeleteCard_success() {
        // Мокируем успешное удаление
        when(cardService.delete(1L)).thenReturn(true);

        // Вызов метода контроллера
        ResponseEntity<Void> response = cardController.deleteCard(1L);

        // Проверки
        assertEquals(204, response.getStatusCodeValue()); // Ожидаем HTTP 204 (No Content)
    }

    @Test
    void testDeleteCard_notFound() {
        // Мокируем неудачное удаление
        when(cardService.delete(1L)).thenReturn(false);

        // Вызов метода контроллера
        ResponseEntity<Void> response = cardController.deleteCard(1L);

        // Проверки
        assertEquals(404, response.getStatusCodeValue()); // Ожидаем HTTP 404
    }
}