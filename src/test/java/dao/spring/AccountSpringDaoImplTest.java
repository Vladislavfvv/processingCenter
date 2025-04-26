package dao.spring;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountSpringDaoImplTest {
    private AccountSpringDaoImpl dao;
    private EntityManager em;

    @BeforeEach
    void setUp() {
        dao = new AccountSpringDaoImpl();
        em = mock(EntityManager.class);
        dao.setEntityManager(em);
    }

    @Test
    void testInsert() {
        Account account = new Account();
        dao.insert(account);
        verify(em).persist(account);
        verify(em).flush();
    }

    @Test
    void testUpdate() {
        Account account = new Account();
        when(em.merge(account)).thenReturn(account);
        Account updated = dao.update(account);
        assertEquals(account, updated);
        verify(em).merge(account);
    }

    @Test
    void testDeleteFound() {
        Account account = new Account();
        when(em.find(Account.class, 1L)).thenReturn(account);
        boolean result = dao.delete(1L);
        assertTrue(result);
        verify(em).remove(account);
    }

    @Test
    void testDeleteNotFound() {
        when(em.find(Account.class, 2L)).thenReturn(null);
        boolean result = dao.delete(2L);
        assertFalse(result);
        verify(em, never()).remove(any());
    }

    @Test
    void testFindById() {
        Account account = new Account();
        when(em.find(Account.class, 1L)).thenReturn(account);
        Optional<Account> result = dao.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(account, result.get());
    }

    @Test
    void testFindAll() {
        List<Account> mockList = List.of(new Account(), new Account());
        TypedQuery<Account> query = mock(TypedQuery.class);

        when(em.createQuery("FROM Account", Account.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(mockList);

        List<Account> result = dao.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testFindByValueFound() {
        Account account = new Account();
        account.setAccountNumber("12345");

        TypedQuery<Account> query = mock(TypedQuery.class);
        when(em.createQuery("FROM Account a WHERE a.accountNumber=:name", Account.class)).thenReturn(query);
        when(query.setParameter("name", "12345")).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(account));

        Optional<Account> result = dao.findByValue("12345");
        assertTrue(result.isPresent());
        assertEquals(account, result.get());
    }

    @Test
    void testFindByValueNotFound() {
        TypedQuery<Account> query = mock(TypedQuery.class);
        when(em.createQuery("FROM Account a WHERE a.accountNumber=:name", Account.class)).thenReturn(query);
        when(query.setParameter("name", "NOT_EXIST")).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        Optional<Account> result = dao.findByValue("NOT_EXIST");
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateTableSuccess() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        boolean result = dao.createTable();
        assertTrue(result);
    }

    @Test
    void testCreateTableFail() {
        when(em.createNativeQuery(anyString())).thenThrow(RuntimeException.class);
        boolean result = dao.createTable();
        assertFalse(result);
    }

    @Test
    void testDeleteAllSuccess() {
        Query query = mock(Query.class);
        when(em.createQuery("DELETE FROM Account")).thenReturn(query);
        when(query.executeUpdate()).thenReturn(2);

        boolean result = dao.deleteAll();
        assertTrue(result);
    }

    @Test
    void testDeleteAllFail() {
        when(em.createQuery("DELETE FROM Account")).thenThrow(RuntimeException.class);
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
        when(em.createNativeQuery(anyString())).thenThrow(RuntimeException.class);
        boolean result = dao.dropTable();
        assertFalse(result);
    }

}