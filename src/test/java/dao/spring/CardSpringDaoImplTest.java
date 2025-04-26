package dao.spring;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import model.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardSpringDaoImplTest {
    private CardSpringDaoImpl cardDao;
    private EntityManager em;

    @BeforeEach
    void setUp() {
        em = mock(EntityManager.class);
        cardDao = new CardSpringDaoImpl();
        cardDao.setEntityManager(em);
    }

    @Test
    void insert_ShouldPersistAndFlushCard() {
        Card card = new Card();

        Card result = cardDao.insert(card);

        verify(em).persist(card);
        verify(em).flush();
        assertEquals(card, result);
    }

    @Test
    void update_ShouldMergeCard() {
        Card card = new Card();
        Card mergedCard = new Card();

        when(em.merge(card)).thenReturn(mergedCard);

        Card result = cardDao.update(card);

        verify(em).merge(card);
        assertEquals(mergedCard, result);
    }

    static Stream<Arguments> findByIdProvider() {
        return Stream.of(
                Arguments.of(1L, new Card(), true),
                Arguments.of(2L, null, false)
        );
    }

    @ParameterizedTest
    @MethodSource("findByIdProvider")
    void findById_ShouldReturnOptional(Long id, Card foundCard, boolean expectedPresent) {
        when(em.find(Card.class, id)).thenReturn(foundCard);

        Optional<Card> result = cardDao.findById(id);

        assertEquals(expectedPresent, result.isPresent());
        if (expectedPresent) {
            assertEquals(foundCard, result.get());
        }
    }

    @Test
    void findAll_ShouldReturnListOfCards() {
        List<Card> cards = List.of(new Card(), new Card());
        TypedQuery<Card> mockQuery = mock(TypedQuery.class);

        when(em.createQuery("select c from Card c", Card.class)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(cards);

        List<Card> result = cardDao.findAll();

        verify(em).createQuery("select c from Card c", Card.class);
        assertEquals(2, result.size());
    }

    @Test
    void delete_WhenCardExists_ShouldRemoveAndReturnTrue() {
        Card card = new Card();
        when(em.find(Card.class, 1L)).thenReturn(card);

        boolean result = cardDao.delete(1L);

        verify(em).find(Card.class, 1L);
        verify(em).remove(card);
        assertTrue(result);
    }

    @Test
    void delete_WhenCardDoesNotExist_ShouldReturnFalse() {
        when(em.find(Card.class, 2L)).thenReturn(null);

        boolean result = cardDao.delete(2L);

        verify(em).find(Card.class, 2L);
        verify(em, never()).remove(any(Card.class));
        assertFalse(result);
    }

    @Test
    void createTable_WhenSuccess_ShouldReturnTrue() {
        Query mockQuery = mock(Query.class);
        when(em.createNativeQuery(anyString())).thenReturn(mockQuery);

        boolean result = cardDao.createTable();

        verify(em).createNativeQuery(anyString());
        verify(mockQuery).executeUpdate();
        assertTrue(result);
    }

    @Test
    void createTable_WhenException_ShouldReturnFalse() {
        when(em.createNativeQuery(anyString())).thenThrow(new RuntimeException());

        boolean result = cardDao.createTable();

        assertFalse(result);
    }

    @Test
    void deleteAll_WhenSuccess_ShouldReturnTrue() {
        Query mockQuery = mock(Query.class);
        when(em.createQuery("DELETE FROM Card")).thenReturn(mockQuery);

        boolean result = cardDao.deleteAll();

        verify(em).createQuery("DELETE FROM Card");
        verify(mockQuery).executeUpdate();
        assertTrue(result);
    }

    @Test
    void deleteAll_WhenException_ShouldReturnFalse() {
        when(em.createQuery("DELETE FROM Card")).thenThrow(new RuntimeException());

        boolean result = cardDao.deleteAll();

        assertFalse(result);
    }

    @Test
    void dropTable_WhenSuccess_ShouldReturnTrue() {
        Query mockQuery = mock(Query.class);
        when(em.createNativeQuery(anyString())).thenReturn(mockQuery);

        boolean result = cardDao.dropTable();

        verify(em).createNativeQuery(anyString());
        verify(mockQuery).executeUpdate();
        assertTrue(result);
    }

    @Test
    void dropTable_WhenException_ShouldReturnFalse() {
        when(em.createNativeQuery(anyString())).thenThrow(new RuntimeException());

        boolean result = cardDao.dropTable();

        assertFalse(result);
    }

    @Test
    void findByValue_WhenCardExists_ShouldReturnCard() {
        String cardNumber = "1234";
        List<Card> cards = List.of(new Card());
        TypedQuery<Card> mockQuery = mock(TypedQuery.class);

        when(em.createQuery("FROM Card c WHERE c.cardNumber = :name", Card.class)).thenReturn(mockQuery);
        when(mockQuery.setParameter("name", cardNumber)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(cards);

        Optional<Card> result = cardDao.findByValue(cardNumber);

        verify(em).createQuery("FROM Card c WHERE c.cardNumber = :name", Card.class);
        verify(mockQuery).setParameter("name", cardNumber);
        assertTrue(result.isPresent());
    }

    @Test
    void findByValue_WhenCardNotExists_ShouldReturnEmptyOptional() {
        String cardNumber = "9999";
        TypedQuery<Card> mockQuery = mock(TypedQuery.class);

        when(em.createQuery("FROM Card c WHERE c.cardNumber = :name", Card.class)).thenReturn(mockQuery);
        when(mockQuery.setParameter("name", cardNumber)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(List.of());

        Optional<Card> result = cardDao.findByValue(cardNumber);

        assertFalse(result.isPresent());
    }


    @Test
    void findAll_ShouldCreateCorrectQuery() {
        // 1. Подготавливаем список объектов, который должен вернуться в методе
        List<Card> cards = List.of(new Card(), new Card());
// 2. Мокаем (создаем имитацию) объекта TypedQuery, который будет использоваться для выполнения запроса.
        TypedQuery<Card> mockQuery = mock(TypedQuery.class);
        // 3. Настроим, что когда создается запрос с любым SQL-запросом и типом Card, он должен вернуть наш mockQuery.
        when(em.createQuery(anyString(), eq(Card.class))).thenReturn(mockQuery);
        // 4. Настроим, что когда вызывается getResultList() на mockQuery, он должен вернуть заранее подготовленный список cards.
        when(mockQuery.getResultList()).thenReturn(cards);
        // 5. Вызываем метод findAll(), который в свою очередь будет создавать запрос и выполнять его.
        List<Card> result = cardDao.findAll();
// 6. Создаем ArgumentCaptor для захвата SQL-запроса, переданного в метод createQuery.
        ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);
        // 7. Проверяем, что в метод createQuery был передан запрос, и захватываем его в переменную queryCaptor.
        verify(em).createQuery(queryCaptor.capture(), eq(Card.class));
        // 8. Извлекаем захваченный SQL-запрос и сохраняем его в переменную.
        // Проверяем, что запрос правильный
        String capturedQuery = queryCaptor.getValue();
        assertEquals("select c from Card c", capturedQuery);

        assertEquals(2, result.size());
    }

    @Test
    void findAll_ShouldReturnCorrectResults() {
        // Подготовка списка объектов, который должен вернуться в результате запроса
        Card card1 = new Card();
        card1.setCardNumber("1234");  // Пример установки значений
        Card card2 = new Card();
        card2.setCardNumber("5678");  // Пример установки значений
        List<Card> cards = List.of(card1, card2);

        // Мокаем (создаем имитацию) объекта TypedQuery, который будет использоваться для выполнения запроса.
        TypedQuery<Card> mockQuery = mock(TypedQuery.class);

        // Настроим, что когда создается запрос с любым SQL-запросом и типом Card, он должен вернуть наш mockQuery.
        when(em.createQuery(anyString(), eq(Card.class))).thenReturn(mockQuery);

        // Настроим, что когда вызывается getResultList() на mockQuery, он должен вернуть заранее подготовленный список cards.
        when(mockQuery.getResultList()).thenReturn(cards);

        // Вызываем метод findAll(), который в свою очередь будет создавать запрос и выполнять его.
        List<Card> result = cardDao.findAll();

        // Проверяем, что метод findAll() вернул правильный список.
        assertNotNull(result);  // Сначала проверяем, что результат не null
        assertEquals(2, result.size());  // Проверяем, что количество элементов в списке 2

        // Дополнительно проверяем, что объекты в списке правильные
        assertEquals("1234", result.get(0).getCardNumber());  // Проверяем, что первый объект имеет правильный номер карты
        assertEquals("5678", result.get(1).getCardNumber());  // Проверяем, что второй объект имеет правильный номер карты

        // Захватываем и проверяем SQL-запрос, как и в предыдущем тесте
        ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);
        verify(em).createQuery(queryCaptor.capture(), eq(Card.class));
        String capturedQuery = queryCaptor.getValue();
        assertEquals("select c from Card c", capturedQuery);
    }
}