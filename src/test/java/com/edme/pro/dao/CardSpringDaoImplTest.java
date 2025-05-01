package com.edme.pro.dao;

import com.edme.pro.model.Account;
import com.edme.pro.model.Card;
import com.edme.pro.model.CardStatus;
import com.edme.pro.model.PaymentSystem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardSpringDaoImplTest {
    @InjectMocks
    private CardSpringDaoImpl dao;

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<Card> typedQuery;

    @Mock
    private Query nativeQuery;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dao.setEntityManager(em);
    }

    private Card createSampleCard() {
        CardStatus status = new CardStatus();
        status.setId(1L);

        PaymentSystem system = new PaymentSystem();
        system.setId(1L);

        Account account = new Account();
        account.setId(1L);

        return new Card(1L, "1234-5678-9012", Date.valueOf(LocalDate.now()),
                "John Doe", status, system, account, null, null);
    }

    @Test
    void testInsert() {
        Card card = createSampleCard();

        Card result = dao.insert(card);

        verify(em).persist(card);
        verify(em).flush();
        assertEquals(card, result);
    }

    @Test
    void testUpdate() {
        Card card = createSampleCard();
        when(em.merge(card)).thenReturn(card);

        Card result = dao.update(card);

        assertEquals(card, result);
        verify(em).merge(card);
    }

    @Test
    void testDelete_WhenCardExists() {
        Card card = createSampleCard();
        when(em.find(Card.class, 1L)).thenReturn(card);

        boolean deleted = dao.delete(1L);

        assertTrue(deleted);
        verify(em).remove(card);
    }

    @Test
    void testDelete_WhenCardNotExists() {
        when(em.find(Card.class, 1L)).thenReturn(null);

        boolean deleted = dao.delete(1L);

        assertFalse(deleted);
        verify(em, never()).remove(any());
    }

    @Test
    void testFindById() {
        Card card = createSampleCard();
        when(em.find(Card.class, 1L)).thenReturn(card);

        Optional<Card> result = dao.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(card, result.get());
    }

    @Test
    void testFindAll() {
        List<Card> cards = List.of(createSampleCard());
        when(em.createQuery(anyString(), eq(Card.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(cards);

        List<Card> result = dao.findAll();

        assertEquals(1, result.size());
        verify(typedQuery).getResultList();
    }

    @Test
    void testCreateTable_Success() {
        when(em.createNativeQuery(anyString())).thenReturn(nativeQuery);
        when(nativeQuery.executeUpdate()).thenReturn(1);

        boolean result = dao.createTable();

        assertTrue(result);
    }

    @Test
    void testCreateTable_Failure() {
        when(em.createNativeQuery(anyString())).thenThrow(RuntimeException.class);

        boolean result = dao.createTable();

        assertFalse(result);
    }

    @Test
    void testDeleteAll_Success() {
        when(em.createQuery("DELETE FROM Card")).thenReturn(nativeQuery);
        when(nativeQuery.executeUpdate()).thenReturn(1);

        boolean result = dao.deleteAll();

        assertTrue(result);
    }

    @Test
    void testDeleteAll_Failure() {
        when(em.createQuery("DELETE FROM Card")).thenThrow(RuntimeException.class);

        boolean result = dao.deleteAll();

        assertFalse(result);
    }

    @Test
    void testDropTable_Success() {
        when(em.createNativeQuery(anyString())).thenReturn(nativeQuery);
        when(nativeQuery.executeUpdate()).thenReturn(1);

        boolean result = dao.dropTable();

        assertTrue(result);
    }

    @Test
    void testDropTable_Failure() {
        when(em.createNativeQuery(anyString())).thenThrow(RuntimeException.class);

        boolean result = dao.dropTable();

        assertFalse(result);
    }

    @Test
    void testFindByValue_Found() {
        Card card = createSampleCard();
        when(em.createQuery("FROM Card c WHERE c.cardNumber = :name", Card.class)).thenReturn(typedQuery);
        when(typedQuery.setParameter("name", "1234-5678-9012")).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(card));

        Optional<Card> result = dao.findByValue("1234-5678-9012");

        assertTrue(result.isPresent());
        assertEquals(card, result.get());
    }

    @Test
    void testFindByValue_NotFound() {
        when(em.createQuery("FROM Card c WHERE c.cardNumber = :name", Card.class)).thenReturn(typedQuery);
        when(typedQuery.setParameter("name", "not-exist")).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of());

        Optional<Card> result = dao.findByValue("not-exist");

        assertTrue(result.isEmpty());
    }
}