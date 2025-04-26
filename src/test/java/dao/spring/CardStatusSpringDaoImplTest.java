package dao.spring;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import model.CardStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardStatusSpringDaoImplTest {
    private CardStatusSpringDaoImpl cardStatusDao;
    private EntityManager em;
    private TypedQuery<CardStatus> query;

    @BeforeEach
    void setUp() {
        em = mock(EntityManager.class); // Мокируем EntityManager
        query = mock(TypedQuery.class); // Мокируем Query

        cardStatusDao = new CardStatusSpringDaoImpl();
        // Сеттим мок в приватное поле
        cardStatusDao.setEntityManager(em);
    }





    @Test
    void testInsert() {
        CardStatus cardStatus = new CardStatus();

        CardStatus result = cardStatusDao.insert(cardStatus);

        assertEquals(cardStatus, result);
        verify(em).persist(cardStatus); // Проверяем, что метод persist был вызван
        verify(em).flush(); // Проверяем, что метод flush был вызван
    }

    @Test
    void testUpdate() {
        CardStatus cardStatus = new CardStatus();

        when(em.merge(cardStatus)).thenReturn(cardStatus);

        CardStatus result = cardStatusDao.update(cardStatus);

        assertEquals(cardStatus, result);
        verify(em).merge(cardStatus); // Проверяем, что метод merge был вызван
    }

    @Test
    void testDeleteFound() {
        CardStatus cardStatus = new CardStatus();
        when(em.find(CardStatus.class, 1L)).thenReturn(cardStatus);

        boolean result = cardStatusDao.delete(1L);

        assertTrue(result);
        verify(em).remove(cardStatus); // Проверяем, что метод remove был вызван
    }

    @Test
    void testDeleteNotFound() {
        // Настроим мок для метода find, чтобы вернуть null (CardStatus не найден)
        when(em.find(CardStatus.class, 1L)).thenReturn(null);

        // Вызов метода DAO
        boolean result = cardStatusDao.delete(1L);

        // Проверяем, что метод remove не был вызван
        assertFalse(result);
        verify(em, never()).remove(any()); // Проверяем, что метод remove не был вызван
    }

    @Test
    void testFindById() {
        CardStatus cardStatus = new CardStatus();
        when(em.find(CardStatus.class, 1L)).thenReturn(cardStatus);

        Optional<CardStatus> result = cardStatusDao.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(cardStatus, result.get());
    }

    @Test
    void testFindAll() {
        List<CardStatus> expected = Collections.singletonList(new CardStatus());

        // Мокируем возвращаемый результат от EntityManager
        when(em.createQuery("FROM CardStatus", CardStatus.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(expected);

        List<CardStatus> result = cardStatusDao.findAll();

        assertEquals(expected, result);
    }

    @Test
    void testFindByValue() {
        CardStatus cardStatus = new CardStatus();
        List<CardStatus> resultList = Collections.singletonList(cardStatus);

        when(em.createQuery("FROM CardStatus cs WHERE cs.cardStatusName = :name", CardStatus.class))
                .thenReturn(query);
        when(query.setParameter("name", "Test")).thenReturn(query);
        when(query.getResultList()).thenReturn(resultList);

        Optional<CardStatus> result = cardStatusDao.findByValue("Test");

        assertTrue(result.isPresent());
        assertEquals(cardStatus, result.get());
    }

    @Test
    void testCreateTable() {
        when(em.createNativeQuery(anyString())).thenReturn(mock(Query.class));

        boolean result = cardStatusDao.createTable();

        assertTrue(result);
        verify(em).createNativeQuery(anyString()); // Check that native query for creating table was executed
//        boolean result = cardStatusDao.createTable();
//
//        assertTrue(result);
//        verify(em).createNativeQuery(anyString()); // Проверяем, что запрос для создания таблицы был выполнен
    }

//    @Test
//    void testDeleteAll() {
//        // Mocking the behavior of createNativeQuery to return a mock Query object
//        when(em.createNativeQuery("DELETE FROM processingcenterschema.card_status")).thenReturn(mock(Query.class));
//
//        boolean result = cardStatusDao.deleteAll();
//
//        assertTrue(result);
//        verify(em).createNativeQuery("DELETE  FROM processingcenterschema.card_status"); // Verify the SQL delete query
////        boolean result = cardStatusDao.deleteAll();
////
////        assertTrue(result);
////        verify(em).createNativeQuery("DELETE FROM processingcenterschema.card_status"); // Проверяем, что запрос на удаление всех записей был выполнен
//    }

@Test
void testDeleteAll() {
    // Mocking the behavior of createNativeQuery to return a mock Query object
    Query mockQuery = mock(Query.class);
    when(em.createNativeQuery("DELETE FROM processingcenterschema.card_status")).thenReturn(mockQuery);
    when(mockQuery.executeUpdate()).thenReturn(1);  // Simulating that one row was deleted

    boolean result = cardStatusDao.deleteAll();

    assertTrue(result);
    verify(em).createNativeQuery("DELETE FROM processingcenterschema.card_status");
    verify(mockQuery).executeUpdate();  // Verify that executeUpdate was called
}

    @Test
    void testDropTable() {
        // Mocking the behavior of createNativeQuery to return a mock Query object
        when(em.createNativeQuery("DROP TABLE IF EXISTS processingcenterschema.card_status CASCADE"))
                .thenReturn(mock(Query.class));

        boolean result = cardStatusDao.dropTable();

        assertTrue(result);
        verify(em).createNativeQuery("DROP TABLE IF EXISTS processingcenterschema.card_status CASCADE"); // Verify the SQL drop table query
//        boolean result = cardStatusDao.dropTable();
//
//        assertTrue(result);
//        verify(em).createNativeQuery("DROP TABLE IF EXISTS processingcenterschema.card_status CASCADE"); // Проверяем, что запрос на удаление таблицы был выполнен
    }
}