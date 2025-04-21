package service.hibernate;

import dao.hibernate.IssuingBankHibernateDaoImpl;
import model.IssuingBank;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import util.HibernateConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class IssuingBankHibernateServiceTest {
//
//    @Mock
//    private IssuingBankHibernateDaoImpl issuingBankHibernateDao;
//
//    @Mock
//    private Session session;
//
//    @Mock
//    private Transaction transaction;
//
//    private IssuingBankHibernateService issuingBankHibernateService;
//    //В методе setUp() инициализируется сервис issuingBankHibernateService,
//    // который будет использоваться в тестах. Это гарантирует, что каждый тест
//    // будет работать с новым экземпляром сервиса, и не будет использоваться состояние,
//    // изменённое в других тестах
//
//    //Изолированность тестов: Каждый тест должен быть независимым, чтобы один тест не влиял на другие.
//    // Это важно для обеспечения точности и предсказуемости результатов.
//    //Повторяемость тестов: Каждый тест будет выполняться с чистой конфигурацией,
//    // что предотвращает использование данных, изменённых в предыдущих тестах.
//    //Снижение зависимости от состояния: С помощью @BeforeEach вы гарантируете,
//    // что каждый тест начинается с одинаковыми условиями.
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this); //инициирует мокирование объектов, помеченных аннотацией @Mock.
//        // Это нужно, чтобы Mockito создавал моки (замещающие объекты) для зависимостей,
//        // указанных в классе тестов
//        issuingBankHibernateService = new IssuingBankHibernateService(issuingBankHibernateDao);
//    }
//
//    @Test
//    void create_ShouldReturnCreatedIssuingBank() {
//        // Given
//        IssuingBank issuingBank = new IssuingBank();
//        issuingBank.setId(1L);
//        when(HibernateConfig.getSessionFactory().openSession()).thenReturn(session);
//        when(session.beginTransaction()).thenReturn(transaction);
//        when(issuingBankHibernateDao.insert(session, issuingBank)).thenReturn(issuingBank);
//
//        // When
//        IssuingBank result = issuingBankHibernateService.create(issuingBank);
//
//        // Then
//        verify(session).beginTransaction();
//        verify(issuingBankHibernateDao).insert(session, issuingBank);
//        verify(transaction).commit();
//        assert(result != null);
//        assert(result.getId().equals(1L));
//    }
//
//    @Test
//    void update_ShouldReturnTrueWhenUpdated() {
//        // Given
//        IssuingBank issuingBank = new IssuingBank();
//        issuingBank.setId(1L);
//        when(HibernateConfig.getSessionFactory().openSession()).thenReturn(session);
//        when(session.beginTransaction()).thenReturn(transaction);
//        when(issuingBankHibernateDao.update(session, issuingBank)).thenReturn(true);
//
//        // When
//        boolean result = issuingBankHibernateService.update(issuingBank);
//
//        // Then
//        verify(session).beginTransaction();
//        verify(issuingBankHibernateDao).update(session, issuingBank);
//        verify(transaction).commit();
//        assert(result);
//    }
//
//    @Test
//    void delete_ShouldReturnTrueWhenDeleted() {
//        // Given
//        long id = 1L;
//        when(HibernateConfig.getSessionFactory().openSession()).thenReturn(session);
//        when(session.beginTransaction()).thenReturn(transaction);
//        when(issuingBankHibernateDao.delete(session, id)).thenReturn(true);
//
//        // When
//        boolean result = issuingBankHibernateService.delete(id);
//
//        // Then
//        verify(session).beginTransaction();
//        verify(issuingBankHibernateDao).delete(session, id);
//        verify(transaction).commit();
//        assert(result);
//    }
//
//    @Test
//    void findById_ShouldReturnOptionalWithIssuingBank() {
//        // Given
//        long id = 1L;
//        IssuingBank issuingBank = new IssuingBank();
//        issuingBank.setId(id);
//        when(HibernateConfig.getSessionFactory().openSession()).thenReturn(session);
//        when(issuingBankHibernateDao.findById(session, id)).thenReturn(Optional.of(issuingBank));
//
//        // When
//        Optional<IssuingBank> result = issuingBankHibernateService.findById(id);
//
//        // Then
//        verify(issuingBankHibernateDao).findById(session, id);
//        assert(result.isPresent());
//        assert(result.get().getId() == id);
//    }
//
//    @Test
//    void findAll_ShouldReturnListOfIssuingBanks() {
//        // Given
//        when(HibernateConfig.getSessionFactory().openSession()).thenReturn(session);
//        when(issuingBankHibernateDao.findAll(session)).thenReturn(List.of(new IssuingBank()));
//
//        // When
//        var result = issuingBankHibernateService.findAll();
//
//        // Then
//        verify(issuingBankHibernateDao).findAll(session);
//        assert(result != null);
//        assert(result.size() > 0);
//    }
//
//    @Test
//    void createTable_ShouldExecuteCreateTableQuery() {
//        // Given
//        String sql = "CREATE TABLE IF NOT EXISTS issuing_bank (...);";
//        when(HibernateConfig.getSessionFactory().openSession()).thenReturn(session);
//        when(session.beginTransaction()).thenReturn(transaction);
//
//        // When
//        issuingBankHibernateService.createTable(sql);
//
//        // Then
//        verify(issuingBankHibernateDao).createTableQuery(session, sql);
//        verify(transaction).commit();
//    }
//
//    @Test
//    void deleteAll_ShouldReturnTrueWhenDeleted() {
//        // Given
//        String tableName = "issuing_bank";
//        when(HibernateConfig.getSessionFactory().openSession()).thenReturn(session);
//        when(session.beginTransaction()).thenReturn(transaction);
//        when(issuingBankHibernateDao.deleteAll(session, tableName)).thenReturn(true);
//
//        // When
//        boolean result = issuingBankHibernateService.deleteAll(tableName);
//
//        // Then
//        verify(issuingBankHibernateDao).deleteAll(session, tableName);
//        verify(transaction).commit();
//        assert(result);
//    }
//
//    @Test
//    void dropTable_ShouldReturnTrueWhenDropped() {
//        // Given
//        String tableName = "issuing_bank";
//        when(HibernateConfig.getSessionFactory().openSession()).thenReturn(session);
//        when(session.beginTransaction()).thenReturn(transaction);
//        when(issuingBankHibernateDao.dropTable(session, tableName)).thenReturn(true);
//
//        // When
//        boolean result = issuingBankHibernateService.dropTable(tableName);
//
//        // Then
//        verify(issuingBankHibernateDao).dropTable(session, tableName);
//        verify(transaction).commit();
//        assert(result);
//    }
}