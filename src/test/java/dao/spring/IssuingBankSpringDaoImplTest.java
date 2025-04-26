package dao.spring;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import model.IssuingBank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IssuingBankSpringDaoImplTest {
    private EntityManager em;
    private IssuingBankSpringDaoImpl dao;

    @BeforeEach
    void setUp() {
        em = mock(EntityManager.class);
        dao = new IssuingBankSpringDaoImpl();
        dao.setEntityManager(em);
    }

    @Test
    void insert_ShouldPersistAndReturnEntity() {
        IssuingBank bank = new IssuingBank();

        IssuingBank result = dao.insert(bank);

        verify(em).persist(bank);
        verify(em).flush();
        assertEquals(bank, result);
    }

    @Test
    void update_ShouldMergeEntity() {
        IssuingBank bank = new IssuingBank();
        when(em.merge(bank)).thenReturn(bank);

        IssuingBank result = dao.update(bank);

        assertEquals(bank, result);
        verify(em).merge(bank);
    }

    @Test
    void delete_ShouldRemoveIfExists() {
        IssuingBank bank = new IssuingBank();
        when(em.find(IssuingBank.class, 1L)).thenReturn(bank);

        boolean result = dao.delete(1L);

        verify(em).remove(bank);
        assertTrue(result);
    }

    @Test
    void delete_ShouldReturnFalseIfNotExists() {
        when(em.find(IssuingBank.class, 1L)).thenReturn(null);

        boolean result = dao.delete(1L);

        verify(em, never()).remove(any());
        assertFalse(result);
    }

    @Test
    void findById_ShouldReturnEntity() {
        IssuingBank bank = new IssuingBank();
        when(em.find(IssuingBank.class, 1L)).thenReturn(bank);

        Optional<IssuingBank> result = dao.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(bank, result.get());
    }

    @Test
    void findAll_ShouldReturnList() {
        TypedQuery<IssuingBank> query = mock(TypedQuery.class);
        List<IssuingBank> banks = List.of(new IssuingBank(), new IssuingBank());

        when(em.createQuery("from IssuingBank", IssuingBank.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(banks);

        List<IssuingBank> result = dao.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void deleteAll_ShouldExecuteUpdate() {
        Query query = mock(Query.class);
        when(em.createQuery("delete from IssuingBank")).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        boolean result = dao.deleteAll();

        verify(query).executeUpdate();
        assertFalse(result); // метод всегда возвращает false по коду
    }

    @Test
    void dropTable_ShouldReturnTrueOnSuccess() {
        Query query = mock(Query.class);
        when(em.createNativeQuery(anyString())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        boolean result = dao.dropTable();

        assertTrue(result);
    }

    @Test
    void dropTable_ShouldReturnFalseOnException() {
        when(em.createNativeQuery(anyString())).thenThrow(RuntimeException.class);

        boolean result = dao.dropTable();

        assertFalse(result);
    }

    @Test
    void findByValue_ShouldReturnFirstMatchingEntity() {
        TypedQuery<IssuingBank> query = mock(TypedQuery.class);
        IssuingBank bank = new IssuingBank();

        when(em.createQuery(anyString(), eq(IssuingBank.class))).thenReturn(query);
        when(query.setParameter(eq("name"), eq("Test Bank"))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(bank));

        Optional<IssuingBank> result = dao.findByValue("Test Bank");

        assertTrue(result.isPresent());
        assertEquals(bank, result.get());
    }

    @Test
    void findByValue_ShouldReturnEmptyIfNoneFound() {
        TypedQuery<IssuingBank> query = mock(TypedQuery.class);

        when(em.createQuery(anyString(), eq(IssuingBank.class))).thenReturn(query);
        when(query.setParameter(eq("name"), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of());

        Optional<IssuingBank> result = dao.findByValue("Unknown");

        assertTrue(result.isEmpty());
    }
}