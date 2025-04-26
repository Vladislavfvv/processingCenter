package service.spring;

import dao.DaoInterfaceSpring;
import dto.CardDto;
import model.Account;
import model.Card;
import model.CardStatus;
import model.PaymentSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CardDtoSpringServiceTest {
    @Mock
    private DaoInterfaceSpring<Long, CardStatus> cardStatusDao;

    @Mock
    private DaoInterfaceSpring<Long, PaymentSystem> paymentSystemDao;

    @Mock
    private DaoInterfaceSpring<Long, Account> accountDao;

    @Mock
    private DaoInterfaceSpring<Long, Card> cardDao;

    private CardDtoSpringService cardDtoSpringService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cardDtoSpringService = new CardDtoSpringService(cardStatusDao, paymentSystemDao, accountDao, cardDao);
    }

    @Test
    void save_ShouldReturnCardDto_WhenCardIsValid() {
        //Полный цикл работы:
        //Тест создает DTO карты и настраивает окружение
        //Сервис получает DTO, проверяет уникальность номера карты
        //Сервис загружает все необходимые зависимости (статус, платежную систему, аккаунт)
        //Сервис преобразует DTO в Entity и сохраняет
        //Сервис преобразует сохраненную Entity обратно в DTO
        //Тест проверяет корректность возвращенного DTO
        // Подготовка тестовых данных - создаем новую карту
        CardDto cardDto = new CardDto();
        cardDto.setCardNumber("4111111111111111");
        cardDto.setExpirationDate(Date.valueOf(LocalDate.now().plusYears(3)));
        cardDto.setCardStatusId(1L);
        cardDto.setPaymentSystemId(1L);
        cardDto.setAccountId(1L);
        //Создание мок-объектов
        CardStatus cardStatus = mock(CardStatus.class);
        PaymentSystem paymentSystem = mock(PaymentSystem.class);
        Account account = mock(Account.class);

        // Настраиваем моки зависимостей - поведения моков
        when(cardStatus.getId()).thenReturn(1L);
        when(paymentSystem.getId()).thenReturn(1L);
        when(account.getId()).thenReturn(1L);
        //Создание тестовой карты - cоздается объект Card, который будет возвращаться как результат сохранения
        //Также заполняются все поля, включая связи с мок-объектами
        Card savedCard = new Card();
        savedCard.setId(1L);
        savedCard.setCardNumber(cardDto.getCardNumber());
        savedCard.setExpirationDate(cardDto.getExpirationDate());
        savedCard.setCardStatusId(cardStatus);
        savedCard.setPaymentSystemId(paymentSystem);
        savedCard.setAccountId(account);
        //Настройка поведения DAO
        //findByValue(): возвращает Optional.empty() - карты с таким номером нет
        when(cardDao.findByValue(cardDto.getCardNumber())).thenReturn(Optional.empty());
        //findById() для зависимостей: возвращают соответствующие мок-объекты
        when(cardStatusDao.findById(cardDto.getCardStatusId())).thenReturn(Optional.of(cardStatus));
        when(paymentSystemDao.findById(cardDto.getPaymentSystemId())).thenReturn(Optional.of(paymentSystem));
        when(accountDao.findById(cardDto.getAccountId())).thenReturn(Optional.of(account));
        //insert(): возвращает подготовленный объект savedCard
        when(cardDao.insert(any(Card.class))).thenReturn(savedCard);

        // Вызов тестируемого метода
        //Сервис должен:
        //Проверить существование карты
        //Получить все зависимости
        //Сохранить новую карту
        //Вернуть DTO сохраненной карты
        Optional<CardDto> result = cardDtoSpringService.save(cardDto);

        // Assert
        assertTrue(result.isPresent());//Результат существует (isPresent())
        assertEquals(cardDto.getCardNumber(), result.get().getCardNumber());//Номер карты в результате совпадает с переданным
        assertEquals(1L, result.get().getId());//ID карты соответствует ожидаемому (1L)
    }

    @Test
    void save_ShouldReturnEmpty_WhenCardNumberAlreadyExists() {
        // Arrange
        CardDto cardDto = new CardDto();
        cardDto.setCardNumber("4111111111111111");

        when(cardDao.findByValue(cardDto.getCardNumber())).thenReturn(Optional.of(mock(Card.class)));

        // Act
        Optional<CardDto> result = cardDtoSpringService.save(cardDto);

        // Assert
        assertFalse(result.isPresent());
    }

//    @Test
//    void findById_ShouldReturnCardDto_WhenCardExists() {
//        // Arrange
//        Long cardId = 1L;
//        Card card = new Card();
//        card.setId(cardId);
//        when(cardDao.findById(cardId)).thenReturn(Optional.of(card));
//
//        // Act
//        Optional<CardDto> result = cardDtoSpringService.findById(cardId);
//
//        // Assert
//        assertTrue(result.isPresent());
//        assertEquals(cardId, result.get().getId());
//    }

//    @Test
//    void findById_ShouldReturnCardDto_WhenCardExists() {
//        // Arrange
//        Card mockCard = new Card();
//        mockCard.setId(1L);
//        mockCard.setCardNumber("4111111111111111");
//        mockCard.setExpirationDate(LocalDate.now().plusYears(3));
//        mockCard.setCvv("123");
//
//        // Создаем связанные сущности и устанавливаем их
//        CardStatus mockStatus = new CardStatus();
//        mockStatus.setId(1L);
//        mockStatus.setName("Active");
//
//        PaymentSystem mockPaymentSystem = new PaymentSystem();
//        mockPaymentSystem.setId(1L);
//        mockPaymentSystem.setName("Visa");
//
//        Account mockAccount = new Account();
//        mockAccount.setId(1L);
//        mockAccount.setAccountNumber("ACC123456");
//
//        mockCard.setCardStatusId(mockStatus);
//        mockCard.setPaymentSystemId(mockPaymentSystem);
//        mockCard.setAccountId(mockAccount);
//
//        // Настроить поведение репозитория
//        when(cardRepository.findById(1L)).thenReturn(Optional.of(mockCard));
//
//        // Act
//        Optional<CardDto> result = cardDtoSpringService.findById(1L);
//
//        // Assert
//        assertTrue(result.isPresent());
//        CardDto cardDto = result.get();
//        assertEquals(1L, cardDto.getId());
//        assertEquals("4111111111111111", cardDto.getCardNumber());
//        assertEquals(mockStatus.getId(), cardDto.getCardStatusId());
//        assertEquals(mockPaymentSystem.getId(), cardDto.getPaymentSystemId());
//        assertEquals(mockAccount.getId(), cardDto.getAccountId());
//
//        // Также можно проверить, что findById вызывался
//        verify(cardRepository, times(1)).findById(1L);
//    }
@Test
public void findById_ShouldReturnCardDto_WhenCardExists() {
    // Arrange
    Long cardId = 1L;
    CardStatus cardStatus = new CardStatus();
    cardStatus.setId(2L);

    PaymentSystem paymentSystem = new PaymentSystem();
    paymentSystem.setId(3L);

    Account account = new Account();
    account.setId(4L);

    Card card = Card.builder()
            .id(cardId)
            .cardNumber("1234567890123456")
            .expirationDate(Date.valueOf("2030-12-31"))
            .holderName("John Doe")
            .cardStatusId(cardStatus)
            .paymentSystemId(paymentSystem)
            .accountId(account)
            .receivedFromIssuingBank(Timestamp.valueOf("2024-01-01 10:00:00"))
            .sentToIssuingBank(Timestamp.valueOf("2024-01-02 12:00:00"))
            .build();

    when(cardDao.findById(cardId)).thenReturn(Optional.of(card));

    // Act
    Optional<CardDto> result = cardDtoSpringService.findById(cardId);

    // Assert
    assertTrue(result.isPresent());
    CardDto cardDto = result.get();
    assertEquals(card.getId(), cardDto.getId());
    assertEquals(card.getCardNumber(), cardDto.getCardNumber());
    assertEquals(card.getExpirationDate(), cardDto.getExpirationDate());
    assertEquals(card.getHolderName(), cardDto.getHolderName());
    assertEquals(card.getCardStatusId().getId(), cardDto.getCardStatusId());
    assertEquals(card.getPaymentSystemId().getId(), cardDto.getPaymentSystemId());
    assertEquals(card.getAccountId().getId(), cardDto.getAccountId());
    assertEquals(card.getReceivedFromIssuingBank(), cardDto.getReceivedFromIssuingBank());
    assertEquals(card.getSentToIssuingBank(), cardDto.getSentToIssuingBank());

    verify(cardDao, times(1)).findById(cardId);
}


    @Test
    void findById_ShouldReturnEmpty_WhenCardNotFound() {
        // Arrange
        Long cardId = 1L;
        when(cardDao.findById(cardId)).thenReturn(Optional.empty());

        // Act
        Optional<CardDto> result = cardDtoSpringService.findById(cardId);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void update_ShouldReturnUpdatedCardDto_WhenCardExists() {
        // Arrange
        CardDto cardDto = new CardDto();
        cardDto.setId(1L);
        cardDto.setCardNumber("4111111111111111");
        cardDto.setCardStatusId(1L);
        cardDto.setPaymentSystemId(1L);
        cardDto.setAccountId(1L);

        Card existingCard = mock(Card.class);
        CardStatus cardStatus = mock(CardStatus.class);
        PaymentSystem paymentSystem = mock(PaymentSystem.class);
        Account account = mock(Account.class);

        // Настраиваем моки для зависимостей
        when(existingCard.getCardStatusId()).thenReturn(cardStatus);
        when(existingCard.getPaymentSystemId()).thenReturn(paymentSystem);
        when(existingCard.getAccountId()).thenReturn(account);
        //Добавлены ID для связанных сущностей:
        when(cardStatus.getId()).thenReturn(1L);
        when(paymentSystem.getId()).thenReturn(1L);
        when(account.getId()).thenReturn(1L);

        when(cardDao.findById(cardDto.getId())).thenReturn(Optional.of(existingCard));
        when(cardStatusDao.findById(cardDto.getCardStatusId())).thenReturn(Optional.of(cardStatus));
        when(paymentSystemDao.findById(cardDto.getPaymentSystemId())).thenReturn(Optional.of(paymentSystem));
        when(accountDao.findById(cardDto.getAccountId())).thenReturn(Optional.of(account));
        //when(cardDao.update(any(Card.class))).thenReturn(new Card());
        when(cardDao.update(any(Card.class))).thenReturn(existingCard);
        // Act
        Optional<CardDto> result = cardDtoSpringService.update(cardDto);

        // Assert
        assertTrue(result.isPresent());
        verify(cardDao).update(any(Card.class));
    }

    @Test
    void update_ShouldReturnEmpty_WhenCardDoesNotExist() {
        // Arrange
        CardDto cardDto = new CardDto();
        cardDto.setId(1L);

        when(cardDao.findById(cardDto.getId())).thenReturn(Optional.empty());

        // Act
        Optional<CardDto> result = cardDtoSpringService.update(cardDto);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void delete_ShouldReturnTrue_WhenCardIsDeleted() {
        // Arrange
        Long cardId = 1L;
        when(cardDao.delete(cardId)).thenReturn(true);

        // Act
        boolean result = cardDtoSpringService.delete(cardId);

        // Assert
        assertTrue(result);
    }

    @Test
    void delete_ShouldReturnFalse_WhenCardNotDeleted() {
        // Arrange
        Long cardId = 1L;
        when(cardDao.delete(cardId)).thenReturn(false);

        // Act
        boolean result = cardDtoSpringService.delete(cardId);

        // Assert
        assertFalse(result);
    }

    @Test
    void deleteAll_ShouldReturnTrue_WhenAllCardsDeleted() {
        // Arrange
        when(cardDao.deleteAll()).thenReturn(true);

        // Act
        boolean result = cardDtoSpringService.deleteAll();

        // Assert
        assertTrue(result);
    }

    @Test
    void deleteAll_ShouldReturnFalse_WhenNotAllCardsDeleted() {
        // Arrange
        when(cardDao.deleteAll()).thenReturn(false);

        // Act
        boolean result = cardDtoSpringService.deleteAll();

        // Assert
        assertFalse(result);
    }
}