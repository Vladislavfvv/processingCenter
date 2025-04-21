package service.hibernate;

import dao.hibernate.AccountHibernateDaoImpl;
import util.HibernateConfig;
import model.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AccountHibernateServiceTest {
    private AccountHibernateDaoImpl accountDao;
    private AccountHibernateService accountService;
    private Session session;
    private Transaction transaction;

//    @BeforeEach
//    void setUp() {
//        accountDao = mock(AccountHibernateDaoImpl.class);
//        accountService = new AccountHibernateService(accountDao);
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
//        Account account = new Account();
//        when(accountDao.insert(eq(session), any(Account.class))).thenReturn(account);
//
//        Account result = accountService.create(account);
//
//        verify(accountDao).insert(session, account);//Mockito фиксирует все вызовы и потом verify() может проверить, были ли нужные вызовы.
//        verify(transaction).commit();
//        verify(session).close();
//        assertEquals(account, result);
//        //verify() — это часть Mockito
//        //Используется в тестах для проверки вызова методов у мок-объектов
//        //Помогает убедиться, что бизнес-логика правильно использует зависимости
//        //Чтобы убедиться, что сервис:
//        //открыл сессию
//        //вызвал insert
//        // закоммитил транзакцию
//        // закрыл сессию
//        //и не нарушил порядок или не забыл что-то важное.
//    }
//
//    @Test
//    void testUpdate() {
//        Account account = new Account();
//        when(accountDao.update(session, account)).thenReturn(true);
//
//        boolean result = accountService.update(account);
//
//        verify(accountDao).update(session, account);
//        verify(transaction).commit();
//        verify(session).close();
//        assertTrue(result);
//    }
//
//    @Test
//    void testDelete() {
//        when(accountDao.delete(session, 1L)).thenReturn(true);
//
//        boolean result = accountService.delete(1L);
//
//        verify(accountDao).delete(session, 1L);
//        verify(transaction).commit();
//        verify(session).close();
//        assertTrue(result);
//    }
//
//    @Test
//    void testFindById() {
//        Account account = new Account();
//        when(accountDao.findById(session, 1L)).thenReturn(Optional.of(account));
//
//        Optional<Account> result = accountService.findById(1L);
//
//        verify(accountDao).findById(session, 1L);
//        verify(session).close();
//        assertTrue(result.isPresent());
//        assertEquals(account, result.get());
//    }
//
//    @Test
//    void testFindAll() {
//        List<Account> accounts = List.of(new Account(), new Account());
//        when(accountDao.findAll(session)).thenReturn(accounts);
//
//        List<Account> result = accountService.findAll();
//
//        verify(accountDao).findAll(session);
//        verify(session).close();
//        assertEquals(2, result.size());
//    }
//
//    @Test
//    void testCreateTable() {
//        String sql = "CREATE TABLE Account ...";
//
//        accountService.createTable(sql);
//
//        verify(accountDao).createTableQuery(session, sql);
//        verify(transaction).commit();
//        verify(session).close();
//    }
//
//    @Test
//    void testDeleteAll() {
//        String tableName = "account";
//        when(accountDao.deleteAll(session, tableName)).thenReturn(true);
//
//        boolean result = accountService.deleteAll(tableName);
//
//        verify(accountDao).deleteAll(session, tableName);
//        verify(transaction).commit();
//        verify(session).close();
//        assertTrue(result);
//    }
//
//    @Test
//    void testDropTable() {
//        String tableName = "account";
//        when(accountDao.dropTable(session, tableName)).thenReturn(true);
//
//        boolean result = accountService.dropTable(tableName);
//
//        verify(accountDao).dropTable(session, tableName);
//        verify(transaction).commit();
//        verify(session).close();
//        assertTrue(result);
//    }

}