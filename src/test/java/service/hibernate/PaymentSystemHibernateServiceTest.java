package service.hibernate;

import dao.hibernate.PaymentSystemHibernateDaoImpl;
import model.PaymentSystem;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentSystemHibernateServiceTest {

    @Mock
    private PaymentSystemHibernateDaoImpl paymentSystemHibernateDaoImpl; // Мокируем зависимость

    @Mock
    private Session session; // Мокируем Session

    @InjectMocks
    private PaymentSystemHibernateService paymentSystemHibernateService; // Сервис, который мы тестируем

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Инициализируем моки
    }

    @Test
    void testCreate() {
        // Подготовка
        PaymentSystem paymentSystem = new PaymentSystem();
        paymentSystem.setId(1L);
        paymentSystem.setPaymentSystemName("Test Payment System");

        // Мокаем поведение insert
        when(paymentSystemHibernateDaoImpl.insert(session, paymentSystem)).thenReturn(paymentSystem);

        // Вызов метода
        PaymentSystem result = paymentSystemHibernateService.create(paymentSystem);

        // Проверки
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Payment System", result.getPaymentSystemName());

        // Проверка, что insert был вызван
        verify(paymentSystemHibernateDaoImpl, times(1)).insert(session, paymentSystem);
    }

    @Test
    void testUpdate() {
        // Подготовка
        PaymentSystem paymentSystem = new PaymentSystem();
        paymentSystem.setId(1L);
        paymentSystem.setPaymentSystemName("Updated Payment System");

        // Мокаем поведение update
        when(paymentSystemHibernateDaoImpl.update(session, paymentSystem)).thenReturn(true);

        // Вызов метода
        boolean result = paymentSystemHibernateService.update(paymentSystem);

        // Проверки
        assertTrue(result);

        // Проверка, что update был вызван
        verify(paymentSystemHibernateDaoImpl, times(1)).update(session, paymentSystem);
    }

    @Test
    void testDelete() {
        // Подготовка
        Long id = 1L;

        // Мокаем поведение delete
        when(paymentSystemHibernateDaoImpl.delete(session, id)).thenReturn(true);

        // Вызов метода
        boolean result = paymentSystemHibernateService.delete(id);

        // Проверки
        assertTrue(result);

        // Проверка, что delete был вызван
        verify(paymentSystemHibernateDaoImpl, times(1)).delete(session, id);
    }

    @Test
    void testFindById() {
        // Подготовка
        Long id = 1L;
        PaymentSystem paymentSystem = new PaymentSystem();
        paymentSystem.setId(id);

        // Мокаем поведение findById
        when(paymentSystemHibernateDaoImpl.findById(session, id)).thenReturn(Optional.of(paymentSystem));

        // Вызов метода
        Optional<PaymentSystem> result = paymentSystemHibernateService.findById(id);

        // Проверки
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());

        // Проверка, что findById был вызван
        verify(paymentSystemHibernateDaoImpl, times(1)).findById(session, id);
    }

    @Test
    void testFindAll() {
        // Мокаем поведение findAll
        when(paymentSystemHibernateDaoImpl.findAll(session)).thenReturn(List.of(new PaymentSystem()));

        // Вызов метода
        var result = paymentSystemHibernateService.findAll();

        // Проверки
        assertNotNull(result);
        assertFalse(result.isEmpty());

        // Проверка, что findAll был вызван
        verify(paymentSystemHibernateDaoImpl, times(1)).findAll(session);
    }

    @Test
    void testCreateTable() {
        // Подготовка
        String sql = "CREATE TABLE payment_system (...)";

        // Вызов метода
        paymentSystemHibernateService.createTable(sql);

        // Проверки
        verify(paymentSystemHibernateDaoImpl, times(1)).createTableQuery(session, sql);
    }

    @Test
    void testDeleteAll() {
        // Подготовка
        String tableName = "payment_system";

        // Мокаем поведение deleteAll
        when(paymentSystemHibernateDaoImpl.deleteAll(session, tableName)).thenReturn(true);

        // Вызов метода
        boolean result = paymentSystemHibernateService.deleteAll(tableName);

        // Проверки
        assertTrue(result);

        // Проверка, что deleteAll был вызван
        verify(paymentSystemHibernateDaoImpl, times(1)).deleteAll(session, tableName);
    }

    @Test
    void testDropTable() {
        // Подготовка
        String tableName = "payment_system";

        // Мокаем поведение dropTable
        when(paymentSystemHibernateDaoImpl.dropTable(session, tableName)).thenReturn(true);

        // Вызов метода
        boolean result = paymentSystemHibernateService.dropTable(tableName);

        // Проверки
        assertTrue(result);

        // Проверка, что dropTable был вызван
        verify(paymentSystemHibernateDaoImpl, times(1)).dropTable(session, tableName);
    }
}