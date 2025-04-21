package service.hibernate;

import dao.hibernate.CardHibernateDaoImpl;
import model.Card;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;//Mockito----------Библиотека для создания мок-объектов
// В тестах не подключаться к реальной базе — вместо этого подмена зависимости моками (заглушками),
// которые эмулируют поведение DAO-объекта. Логика:
//изолируешь CardHibernateService от реального DAO, подставляя мок-объект, чтобы:
// проверить только логику сервиса
//убедиться что он корректно работает с DAO
//ловит исключения там, где нужно
//вызывает DAO-методы ожидаемое число раз
class CardHibernateServiceTest {
//    private CardHibernateDaoImpl cardDaoMock;
//    private CardHibernateService cardService;
//
//    //схема тестирования:
//    //настроить поведение DAO мока через when(...).thenReturn(...)
//    //вызвать метод сервиса
//    //проверить результат через assert
//    //проверить что мок-метод вызвался через verify
//
//    @BeforeEach
//    void setUp() {
//        cardDaoMock = mock(CardHibernateDaoImpl.class);//Это заглушка вместо настоящего DAO.
//        // Её методы можно настроить возвращать нужные значения или проверять вызовы
//        cardService = new CardHibernateService(cardDaoMock); //мок внутрь сервиса, чтобы в тестах можно было контролировать поведение DAO
//    }
//
//    @Test
//    void create_ValidCard_Success() {
//        //создаём карту с валидным номером
//        Card validCard = new Card();
//        validCard.setCardNumber("4532015112830366"); // валидный номер по Луну
//        //если DAO-метод insert вызовется — он вернёт эту же карту
//        when(cardDaoMock.insert(any(Session.class), eq(validCard))).thenReturn(validCard);
//
//
//        Card result = cardService.create(validCard);
//
//        // Assert
//        assertNotNull(result); //проверка что вернулось не null
//        assertEquals(validCard, result); //проверка что вернулась та же карта
//        verify(cardDaoMock, times(1)).insert(any(Session.class), eq(validCard));
//        //проверка что DAO-метод insert вызвался ровно 1 раз с любым Session и именно этой картой
//    }
//
//    @Test
//    void create_InvalidCardNumber_ThrowsException() {
//        // Arrange
//        Card invalidCard = new Card();
//        invalidCard.setCardNumber("1234567890123456"); // невалидный
//
//        // при невалидном номере карты метод create_InvalidCardNumber_ThrowsException выбросит IllegalArgumentException
//        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
//            cardService.create(invalidCard);
//        });
//        assertEquals("Некорректный номер карты: не проходит проверку по алгоритму Луна.", ex.getMessage());
//        verify(cardDaoMock, never()).insert(any(Session.class), any(Card.class));
//        //DAO-метод insert вообще не вызовется
//    }
//
//    @Test
//    void update_ValidCard_Success() {
//        Card validCard = new Card();
//        validCard.setCardNumber("4532015112830366");
//
//        when(cardDaoMock.update(any(Session.class), eq(validCard))).thenReturn(true);
//
//        boolean result = cardService.update(validCard);
//
//        assertTrue(result);
//        verify(cardDaoMock, times(1)).update(any(Session.class), eq(validCard));
//    }
//
//    @Test
//    void delete_CardById_Success() {
//        when(cardDaoMock.delete(any(Session.class), eq(1L))).thenReturn(true);
//
//        boolean result = cardService.delete(1L);
//
//        assertTrue(result);
//        verify(cardDaoMock, times(1)).delete(any(Session.class), eq(1L));
//    }
//
//    @Test
//    void findById_CardExists_ReturnsCard() {
//        Card card = new Card();
//        card.setCardNumber("4532015112830366");
//
//        when(cardDaoMock.findById(any(Session.class), eq(1L))).thenReturn(Optional.of(card));
//
//        Optional<Card> result = cardService.findById(1L);
//
//        assertTrue(result.isPresent());
//        assertEquals(card, result.get());
//        verify(cardDaoMock, times(1)).findById(any(Session.class), eq(1L));
//    }
//
//    @Test
//    void findAll_ReturnsList() {
//        when(cardDaoMock.findAll(any(Session.class))).thenReturn(List.of(new Card(), new Card()));
//
//        List<Card> result = cardService.findAll();
//
//        assertEquals(2, result.size());
//        verify(cardDaoMock, times(1)).findAll(any(Session.class));
//    }
//
//    @Test
//    void deleteAll_ReturnsTrue() {
//        when(cardDaoMock.deleteAll(any(Session.class), eq("CardTable"))).thenReturn(true);
//
//        boolean result = cardService.deleteAll("CardTable");
//
//        assertTrue(result);
//        verify(cardDaoMock, times(1)).deleteAll(any(Session.class), eq("CardTable"));
//    }
//
//    @Test
//    void dropTable_ReturnsTrue() {
//        when(cardDaoMock.dropTable(any(Session.class), eq("CardTable"))).thenReturn(true);
//
//        boolean result = cardService.dropTable("CardTable");
//
//        assertTrue(result);
//        verify(cardDaoMock, times(1)).dropTable(any(Session.class), eq("CardTable"));
//    }
}