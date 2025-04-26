package service.spring;

import dao.DaoInterfaceSpring;
import dto.AccountDto;
import model.Account;
import model.Currency;
import model.IssuingBank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountDtoSpringServiceTest {
    private DaoInterfaceSpring<Long, Currency> currencyDao;
    private DaoInterfaceSpring<Long, IssuingBank> issuingBankDao;
    private DaoInterfaceSpring<Long, Account> accountDao;
    private AccountDtoSpringService service;

    @BeforeEach
    void setUp() {
        currencyDao = mock(DaoInterfaceSpring.class);
        issuingBankDao = mock(DaoInterfaceSpring.class);
        accountDao = mock(DaoInterfaceSpring.class);
        service = new AccountDtoSpringService(currencyDao, issuingBankDao, accountDao);
    }

    @Test
    void testSave_NewAccount_Success() {
        AccountDto dto = new AccountDto(null, "ACC123", BigDecimal.TEN, 1L, 1L);

        when(accountDao.findByValue("ACC123")).thenReturn(Optional.empty());
        when(currencyDao.findById(1L)).thenReturn(Optional.of(new Currency()));
        when(issuingBankDao.findById(1L)).thenReturn(Optional.of(new IssuingBank()));
        when(accountDao.insert(any(Account.class))).thenAnswer(i -> i.getArguments()[0]);

        Optional<AccountDto> result = service.save(dto);

        assertTrue(result.isPresent());
        assertEquals("ACC123", result.get().getAccountNumber());
        verify(accountDao).insert(any(Account.class));
    }

    @Test
    void testSave_ExistingAccountById() {
        AccountDto dto = new AccountDto(1L, "ACC123", BigDecimal.TEN, 1L, 1L);
        Account existing = new Account();
        existing.setId(1L);
        existing.setAccountNumber("ACC123");

        when(accountDao.findById(1L)).thenReturn(Optional.of(existing));

        Optional<AccountDto> result = service.save(dto);

        assertTrue(result.isPresent());
        assertEquals("ACC123", result.get().getAccountNumber());
        verify(accountDao, never()).insert(any(Account.class));
    }

    @Test
    void testSave_ExistingAccountByValue() {
        AccountDto dto = new AccountDto(null, "ACC123", BigDecimal.TEN, 1L, 1L);
        Account existing = new Account();
        existing.setAccountNumber("ACC123");

        when(accountDao.findByValue("ACC123")).thenReturn(Optional.of(existing));

        Optional<AccountDto> result = service.save(dto);

        assertTrue(result.isPresent());
        verify(accountDao, never()).insert(any(Account.class));
    }

    @Test
    void testFindAll_ReturnsMappedList() {
        Account account = new Account();
        account.setId(1L);
        account.setAccountNumber("A1");

        when(accountDao.findAll()).thenReturn(List.of(account));

        List<AccountDto> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("A1", result.get(0).getAccountNumber());
    }

    @Test
    void testFindAll_ExceptionHandled() {
        when(accountDao.findAll()).thenThrow(RuntimeException.class);

        List<AccountDto> result = service.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindById_Found() {
        Account account = new Account();
        account.setId(1L);
        account.setAccountNumber("A1");

        when(accountDao.findById(1L)).thenReturn(Optional.of(account));

        Optional<AccountDto> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("A1", result.get().getAccountNumber());
    }

    @Test
    void testFindByValue_Found() {
        Account account = new Account();
        account.setId(1L);
        account.setAccountNumber("A1");

        when(accountDao.findByValue("A1")).thenReturn(Optional.of(account));

        Optional<AccountDto> result = service.findByValue("A1");

        assertTrue(result.isPresent());
        assertEquals("A1", result.get().getAccountNumber());
    }

    @Test
    void testUpdate_Success() {
        AccountDto dto = new AccountDto(1L, "ACC_UPDATED", BigDecimal.valueOf(100), 2L, 3L);
        Account existing = new Account();
        existing.setId(1L);
        existing.setAccountNumber("OLD");

        when(accountDao.findById(1L)).thenReturn(Optional.of(existing));
        when(currencyDao.findById(2L)).thenReturn(Optional.of(new Currency()));
        when(issuingBankDao.findById(3L)).thenReturn(Optional.of(new IssuingBank()));

        Optional<AccountDto> result = service.update(dto);

        assertTrue(result.isPresent());
        assertEquals("ACC_UPDATED", result.get().getAccountNumber());
        verify(accountDao).update(existing);
    }

    @Test
    void testUpdate_IdNull() {
        AccountDto dto = new AccountDto(null, "ACC_UPDATED", BigDecimal.valueOf(100), 2L, 3L);

        Optional<AccountDto> result = service.update(dto);

        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdate_NotFound() {
        AccountDto dto = new AccountDto(10L, "ACC_UPDATED", BigDecimal.valueOf(100), 2L, 3L);
        when(accountDao.findById(10L)).thenReturn(Optional.empty());

        Optional<AccountDto> result = service.update(dto);

        assertTrue(result.isEmpty());
    }

    @Test
    void testDelete() {
        when(accountDao.delete(1L)).thenReturn(true);

        assertTrue(service.delete(1L));
        verify(accountDao).delete(1L);
    }

    @Test
    void testDeleteAll() {
        when(accountDao.deleteAll()).thenReturn(true);

        assertTrue(service.deleteAll());
        verify(accountDao).deleteAll();
    }

    @Test
    void testDropTable() {
        when(accountDao.dropTable()).thenReturn(true);

        assertTrue(service.dropTable());
        verify(accountDao).dropTable();
    }
}