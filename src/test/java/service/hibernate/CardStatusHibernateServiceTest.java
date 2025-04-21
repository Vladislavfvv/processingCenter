package service.hibernate;

import dao.hibernate.CardStatusHibernateDaoImpl;
import model.CardStatus;
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

class CardStatusHibernateServiceTest {
    private CardStatusHibernateDaoImpl cardStatusDao;
    private CardStatusHibernateService cardStatusService;
    private Session session;
    private Transaction transaction;

//    @BeforeEach
//    void setUp() {
//        cardStatusDao = mock(CardStatusHibernateDaoImpl.class);
//        cardStatusService = new CardStatusHibernateService(cardStatusDao);
//
//        session = mock(Session.class);
//        transaction = mock(Transaction.class);
//
//        SessionFactory sessionFactory = mock(SessionFactory.class);
//        when(sessionFactory.openSession()).thenReturn(session);
//        when(session.beginTransaction()).thenReturn(transaction);
//
//        // Мокаем HibernateConfig статически
//        HibernateConfig.setSessionFactory(sessionFactory);
//    }
//
//    @Test
//    void testCreate() {
//        CardStatus cardStatus = new CardStatus();
//        when(cardStatusDao.insert(eq(session), any(CardStatus.class))).thenReturn(cardStatus);
//
//        CardStatus result = cardStatusService.create(cardStatus);
//
//        verify(cardStatusDao).insert(session, cardStatus); // Проверка, что insert был вызван
//        verify(transaction).commit();
//        verify(session).close();
//        assertEquals(cardStatus, result); // Проверка, что результат совпадает с ожидаемым
//    }
//
//    @Test
//    void testUpdate() {
//        CardStatus cardStatus = new CardStatus();
//        when(cardStatusDao.update(session, cardStatus)).thenReturn(true);
//
//        boolean result = cardStatusService.update(cardStatus);
//
//        verify(cardStatusDao).update(session, cardStatus);
//        verify(transaction).commit();
//        verify(session).close();
//        assertTrue(result); // Проверка, что результат true
//    }
//
//    @Test
//    void testDelete() {
//        when(cardStatusDao.delete(session, 1L)).thenReturn(true);
//
//        boolean result = cardStatusService.delete(1L);
//
//        verify(cardStatusDao).delete(session, 1L);
//        verify(transaction).commit();
//        verify(session).close();
//        assertTrue(result); // Проверка, что результат true
//    }
//
//    @Test
//    void testFindById() {
//        CardStatus cardStatus = new CardStatus();
//        when(cardStatusDao.findById(session, 1L)).thenReturn(Optional.of(cardStatus));
//
//        Optional<CardStatus> result = cardStatusService.findById(1L);
//
//        verify(cardStatusDao).findById(session, 1L);
//        verify(session).close();
//        assertTrue(result.isPresent());
//        assertEquals(cardStatus, result.get()); // Проверка, что результат совпадает с ожидаемым
//    }
//
//    @Test
//    void testFindAll() {
//        List<CardStatus> cardStatuses = List.of(new CardStatus(), new CardStatus());
//        when(cardStatusDao.findAll(session)).thenReturn(cardStatuses);
//
//        List<CardStatus> result = cardStatusService.findAll();
//
//        verify(cardStatusDao).findAll(session);
//        verify(session).close();
//        assertEquals(2, result.size()); // Проверка, что список имеет размер 2
//    }
//
//    @Test
//    void testCreateTable() {
//        String sql = "CREATE TABLE CardStatus ...";
//
//        cardStatusService.createTable(sql);
//
//        verify(cardStatusDao).createTableQuery(session, sql);
//        verify(transaction).commit();
//        verify(session).close();
//    }
//
//    @Test
//    void testDeleteAll() {
//        String tableName = "card_status";
//        when(cardStatusDao.deleteAll(session, tableName)).thenReturn(true);
//
//        boolean result = cardStatusService.deleteAll(tableName);
//
//        verify(cardStatusDao).deleteAll(session, tableName);
//        verify(transaction).commit();
//        verify(session).close();
//        assertTrue(result); // Проверка, что результат true
//    }
//
//    @Test
//    void testDropTable() {
//        String tableName = "card_status";
//        when(cardStatusDao.dropTable(session, tableName)).thenReturn(true);
//
//        boolean result = cardStatusService.dropTable(tableName);
//
//        verify(cardStatusDao).dropTable(session, tableName);
//        verify(transaction).commit();
//        verify(session).close();
//        assertTrue(result); // Проверка, что результат true
//    }
}