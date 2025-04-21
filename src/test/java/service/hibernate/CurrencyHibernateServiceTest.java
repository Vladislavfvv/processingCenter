package service.hibernate;

import dao.hibernate.CurrencyHibernateDaoImpl;
import model.Currency;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HibernateConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CurrencyHibernateServiceTest {
    private CurrencyHibernateDaoImpl currencyDao;
    private CurrencyHibernateService currencyService;
    private Session session;
    private Transaction transaction;

//    @BeforeEach
//    void setUp() {
//        currencyDao = mock(CurrencyHibernateDaoImpl.class);
//        currencyService = new CurrencyHibernateService(currencyDao);
//
//        session = mock(Session.class);
//        transaction = mock(Transaction.class);
//
//        // Мокаем HibernateConfig
//        HibernateConfig.setSessionFactory(mock(SessionFactory.class));
//        when(HibernateConfig.getSessionFactory().openSession()).thenReturn(session);
//        when(session.beginTransaction()).thenReturn(transaction);
//    }
//
//    @Test
//    void testCreate() {
//        Currency currency = new Currency();
//        when(currencyDao.insert(eq(session), any(Currency.class))).thenReturn(currency);
//
//        Currency result = currencyService.create(currency);
//
//        verify(currencyDao).insert(session, currency);
//        verify(transaction).commit();
//        verify(session).close();
//        assertEquals(currency, result);
//    }
//
//    @Test
//    void testUpdate() {
//        Currency currency = new Currency();
//        when(currencyDao.update(session, currency)).thenReturn(true);
//
//        boolean result = currencyService.update(currency);
//
//        verify(currencyDao).update(session, currency);
//        verify(transaction).commit();
//        verify(session).close();
//        assertTrue(result);
//    }
//
//    @Test
//    void testDelete() {
//        when(currencyDao.delete(session, 1L)).thenReturn(true);
//
//        boolean result = currencyService.delete(1L);
//
//        verify(currencyDao).delete(session, 1L);
//        verify(transaction).commit();
//        verify(session).close();
//        assertTrue(result);
//    }
//
//    @Test
//    void testFindById() {
//        Currency currency = new Currency();
//        when(currencyDao.findById(session, 1L)).thenReturn(Optional.of(currency));
//
//        Optional<Currency> result = currencyService.findById(1L);
//
//        verify(currencyDao).findById(session, 1L);
//        verify(session).close();
//        assertTrue(result.isPresent());
//        assertEquals(currency, result.get());
//    }
//
//    @Test
//    void testFindAll() {
//        List<Currency> currencies = List.of(new Currency(), new Currency());
//        when(currencyDao.findAll(session)).thenReturn(currencies);
//
//        List<Currency> result = currencyService.findAll();
//
//        verify(currencyDao).findAll(session);
//        verify(session).close();
//        assertEquals(2, result.size());
//    }
//
//    @Test
//    void testCreateTable() {
//        String sql = "CREATE TABLE Currency ...";
//
//        currencyService.createTable(sql);
//
//        verify(currencyDao).createTableQuery(session, sql);
//        verify(transaction).commit();
//        verify(session).close();
//    }
//
//    @Test
//    void testDeleteAll() {
//        String tableName = "currency";
//        when(currencyDao.deleteAll(session, tableName)).thenReturn(true);
//
//        boolean result = currencyService.deleteAll(tableName);
//
//        verify(currencyDao).deleteAll(session, tableName);
//        verify(transaction).commit();
//        verify(session).close();
//        assertTrue(result);
//    }
//
//    @Test
//    void testDropTable() {
//        String tableName = "currency";
//        when(currencyDao.dropTable(session, tableName)).thenReturn(true);
//
//        boolean result = currencyService.dropTable(tableName);
//
//        verify(currencyDao).dropTable(session, tableName);
//        verify(transaction).commit();
//        verify(session).close();
//        assertTrue(result);
//    }
}