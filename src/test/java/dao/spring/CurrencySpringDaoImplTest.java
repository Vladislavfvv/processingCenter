package dao.spring;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CurrencySpringDaoImplTest {
    private CurrencySpringDaoImpl currencyDao;
    private EntityManager em;
    private TypedQuery<Currency> query;

    @BeforeEach
    void setUp() {
        em = mock(EntityManager.class); // мок EntityManager
        query = mock(TypedQuery.class); // мок Query

        currencyDao = new CurrencySpringDaoImpl();
        // сетим мок в приватное поле
        currencyDao.setEntityManager(em);
    }

    @Test
    void testInsert() {
        Currency currency = new Currency();

        Currency result = currencyDao.insert(currency);

        assertEquals(currency, result);
        verify(em).persist(currency); // проверяем что вызвался метод persist
    }

    @Test
    void testUpdate() {
        Currency currency = new Currency();

        when(em.merge(currency)).thenReturn(currency);

        Currency result = currencyDao.update(currency);

        assertEquals(currency, result);
        verify(em).merge(currency);
    }

    @Test
    void testDeleteFound() {
        Currency currency = new Currency();
        when(em.find(Currency.class, 1L)).thenReturn(currency);

        boolean result = currencyDao.delete(1L);

        assertTrue(result);
        verify(em).remove(currency);
    }

    @Test
    void testDeleteNotFound() {
        when(em.find(Currency.class, 1L)).thenReturn(null);

        boolean result = currencyDao.delete(1L);

        assertFalse(result);
        verify(em, never()).remove(any());
    }

    @Test
    void testFindAll() {
        List<Currency> expected = Collections.singletonList(new Currency());

        // Говорим mock EntityManager, что при вызове createQuery(...) он вернёт mock Query
        when(em.createQuery(anyString(), eq(Currency.class))).thenReturn(query);
       // А у mock Query на getResultList() вернуть заранее заданный список
        when(query.getResultList()).thenReturn(expected);

        List<Currency> result = currencyDao.findAll();

        assertEquals(expected, result);
    }

    @Test
    void testFindByValue() {
        Currency currency = new Currency();
        List<Currency> resultList = Collections.singletonList(currency);

        when(em.createQuery(anyString(), eq(Currency.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(resultList);

        Optional<Currency> result = currencyDao.findByValue("Test");

        assertTrue(result.isPresent());
        assertEquals(currency, result.get());
    }
}