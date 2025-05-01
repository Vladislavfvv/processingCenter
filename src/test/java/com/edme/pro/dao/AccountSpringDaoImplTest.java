package com.edme.pro.dao;

import com.edme.pro.model.Account;
import com.edme.pro.model.Currency;
import com.edme.pro.model.IssuingBank;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountSpringDaoImplTest {
    @InjectMocks
    private AccountSpringDaoImpl dao;

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<Account> typedQuery;

    @Mock
    private Query nativeQuery;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dao.setEntityManager(em);
    }

    private Account createSampleAccount() {
        Currency currency = new Currency();
        currency.setId(1L);

        IssuingBank bank = new IssuingBank();
        bank.setId(1L);

        Account account = new Account();
        account.setId(1L);
        account.setAccountNumber("ACC12345");
        account.setBalance(BigDecimal.valueOf(1000));
        account.setCurrencyId(currency);
        account.setIssuingBankId(bank);
        return account;
    }

    @Test
    void testInsert() {
        Account account = createSampleAccount();

        Account result = dao.insert(account);

        verify(em).persist(account);
        verify(em).flush();
        assertEquals(account, result);
    }

    @Test
    void testUpdate() {
        Account account = createSampleAccount();
        when(em.merge(account)).thenReturn(account);

        Account result = dao.update(account);

        assertEquals(account, result);
        verify(em).merge(account);
    }

    @Test
    void testDelete_WhenAccountExists() {
        Account account = createSampleAccount();
        when(em.find(Account.class, 1L)).thenReturn(account);

        boolean deleted = dao.delete(1L);

        assertTrue(deleted);
        verify(em).remove(account);
    }

    @Test
    void testDelete_WhenAccountNotExists() {
        when(em.find(Account.class, 1L)).thenReturn(null);

        boolean deleted = dao.delete(1L);

        assertFalse(deleted);
        verify(em, never()).remove(any());
    }

    @Test
    void testFindById_Found() {
        Account account = createSampleAccount();
        when(em.createQuery(anyString(), eq(Account.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("id"), eq(1L))).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(account);

        Optional<Account> result = dao.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(account, result.get());
    }

    @Test
    void testFindById_NotFound() {
        when(em.createQuery(anyString(), eq(Account.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("id"), eq(1L))).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenThrow(NoResultException.class);

        Optional<Account> result = dao.findById(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAll() {
        List<Account> accounts = List.of(createSampleAccount());
        when(em.createQuery(anyString(), eq(Account.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(accounts);

        List<Account> result = dao.findAll();

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
        when(em.createQuery("DELETE FROM Account")).thenReturn(nativeQuery);
        when(nativeQuery.executeUpdate()).thenReturn(1);

        boolean result = dao.deleteAll();

        assertTrue(result);
    }

    @Test
    void testDeleteAll_Failure() {
        when(em.createQuery("DELETE FROM Account")).thenThrow(RuntimeException.class);

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
        Account account = createSampleAccount();
        when(em.createQuery("FROM Account a WHERE a.accountNumber=:name", Account.class)).thenReturn(typedQuery);
        when(typedQuery.setParameter("name", "ACC12345")).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(account));

        Optional<Account> result = dao.findByValue("ACC12345");

        assertTrue(result.isPresent());
        assertEquals(account, result.get());
    }

    @Test
    void testFindByValue_NotFound() {
        when(em.createQuery("FROM Account a WHERE a.accountNumber=:name", Account.class)).thenReturn(typedQuery);
        when(typedQuery.setParameter("name", "NOT_EXISTS")).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of());

        Optional<Account> result = dao.findByValue("NOT_EXISTS");

        assertTrue(result.isEmpty());
    }
}
