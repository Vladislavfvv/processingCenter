package dao.spring;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import model.PaymentSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentSystemSpringDaoImplTest {
    private PaymentSystemSpringDaoImpl dao;
    private EntityManager em;

    @BeforeEach
    void setUp() {
        em = mock(EntityManager.class);
        dao = new PaymentSystemSpringDaoImpl();
        dao.setEntityManager(em);
    }

    @Test
    void testInsert() {
        PaymentSystem ps = new PaymentSystem();
        dao.insert(ps);
        verify(em).persist(ps);
        verify(em).flush();
    }

    @Test
    void testUpdate() {
        PaymentSystem ps = new PaymentSystem();
        when(em.merge(ps)).thenReturn(ps);
        PaymentSystem updated = dao.update(ps);
        assertEquals(ps, updated);
        verify(em).merge(ps);
    }

    @Test
    void testDeleteFound() {
        PaymentSystem ps = new PaymentSystem();
        when(em.find(PaymentSystem.class, 1L)).thenReturn(ps);
        boolean result = dao.delete(1L);
        assertTrue(result);
        verify(em).remove(ps);
    }

    @Test
    void testDeleteNotFound() {
        when(em.find(PaymentSystem.class, 1L)).thenReturn(null);
        boolean result = dao.delete(1L);
        assertFalse(result);
        verify(em, never()).remove(any());
    }

    @Test
    void testFindById() {
        PaymentSystem ps = new PaymentSystem();
        when(em.find(PaymentSystem.class, 1L)).thenReturn(ps);
        Optional<PaymentSystem> result = dao.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(ps, result.get());
    }


    @Test
    void testCreateTableSuccess() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(0);

        boolean result = dao.createTable();
        assertTrue(result);
        verify(query).executeUpdate();
    }

    @Test
    void testCreateTableFail() {
        when(em.createNativeQuery(anyString())).thenThrow(new RuntimeException("fail"));

        boolean result = dao.createTable();
        assertFalse(result);
    }

    @Test
    void testDeleteAllSuccess() {
        Query query = mock(Query.class);
        when(em.createQuery("DELETE FROM PaymentSystem")).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        boolean result = dao.deleteAll();
        assertTrue(result);
    }

    @Test
    void testDeleteAllFail() {
        when(em.createQuery("DELETE FROM PaymentSystem")).thenThrow(new RuntimeException("fail"));

        boolean result = dao.deleteAll();
        assertFalse(result);
    }

    @Test
    void testDropTableSuccess() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        boolean result = dao.dropTable();
        assertTrue(result);
    }

    @Test
    void testDropTableFail() {
        when(em.createNativeQuery(anyString())).thenThrow(new RuntimeException("fail"));

        boolean result = dao.dropTable();
        assertFalse(result);
    }

    @Test
    void testFindByValueFound() {
        TypedQuery<PaymentSystem> query = mock(TypedQuery.class);
        PaymentSystem ps = new PaymentSystem();
        when(em.createQuery(anyString(), eq(PaymentSystem.class))).thenReturn(query);
        when(query.setParameter(eq("name"), eq("VISA"))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(ps));

        Optional<PaymentSystem> result = dao.findByValue("VISA");
        assertTrue(result.isPresent());
        assertEquals(ps, result.get());
    }

    @Test
    void testFindByValueNotFound() {
        TypedQuery<PaymentSystem> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(PaymentSystem.class))).thenReturn(query);
        when(query.setParameter(eq("name"), eq("VISA"))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        Optional<PaymentSystem> result = dao.findByValue("VISA");
        assertFalse(result.isPresent());
    }
}