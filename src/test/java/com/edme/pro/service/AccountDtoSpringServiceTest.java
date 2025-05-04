package com.edme.pro.service;

import com.edme.pro.dao.DaoInterfaceSpring;
import com.edme.pro.dto.AccountDto;
import com.edme.pro.model.Account;
import com.edme.pro.model.Currency;
import com.edme.pro.model.IssuingBank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountDtoSpringServiceTest {
//private DaoInterfaceSpring<Long, Currency> currencyDao;
//    private DaoInterfaceSpring<Long, IssuingBank> issuingBankDao;
//    private DaoInterfaceSpring<Long, Account> accountDao;
//
//    private AccountDtoSpringService service;
//
//    @BeforeEach
//    void setUp() {
//        currencyDao = mock(DaoInterfaceSpring.class);
//        issuingBankDao = mock(DaoInterfaceSpring.class);
//        accountDao = mock(DaoInterfaceSpring.class);
//
//        service = new AccountDtoSpringService(currencyDao, issuingBankDao, accountDao);
//    }
//
//    @Test
//    void save_shouldInsertNewAccountIfNotExists() {
//        AccountDto dto = AccountDto.builder()
//                .accountNumber("ACC123")
//                .balance(new BigDecimal("100.00"))
//                .currencyId(1L)
//                .issuingBankId(2L)
//                .build();
//
//        when(accountDao.findByValue("ACC123")).thenReturn(Optional.empty());
//        when(currencyDao.findById(1L)).thenReturn(Optional.of(Currency.builder().id(1L).build()));
//        when(issuingBankDao.findById(2L)).thenReturn(Optional.of(IssuingBank.builder().id(2L).build()));
//
//        Account savedAccount = Account.builder().id(1L).accountNumber("ACC123").build();
//        when(accountDao.insert(any(Account.class))).thenReturn(savedAccount);
//
//        Optional<Account> result = service.save(dto);
//
//        assertTrue(result.isPresent());
//        assertEquals("ACC123", result.get().getAccountNumber());
//        verify(accountDao).insert(any(Account.class));
//    }
//
//    @Test
//    void save_shouldReturnEmptyIfAccountExists() {
//        when(accountDao.findByValue("ACC123")).thenReturn(Optional.of(Account.builder().id(1L).accountNumber("ACC123").build()));
//
//        AccountDto dto = AccountDto.builder()
//                .accountNumber("ACC123")
//                .balance(new BigDecimal("100.00"))
//                .currencyId(1L)
//                .issuingBankId(2L)
//                .build();
//
//        Optional<Account> result = service.save(dto);
//
//        assertTrue(result.isEmpty());
//        verify(accountDao, never()).insert(any());
//    }
//
//    @Test
//    void findAll_shouldReturnAllAccounts() {
//        List<Account> mockList = List.of(Account.builder().id(1L).build(), Account.builder().id(2L).build());
//        when(accountDao.findAll()).thenReturn(mockList);
//
//        List<Account> result = service.findAll();
//        assertEquals(2, result.size());
//    }
//
//    @Test
//    void findById_shouldReturnCorrectAccount() {
//        Account account = Account.builder().id(1L).accountNumber("A1").build();
//        when(accountDao.findById(1L)).thenReturn(Optional.of(account));
//
//        Optional<Account> result = service.findById(1L);
//
//        assertTrue(result.isPresent());
//        assertEquals("A1", result.get().getAccountNumber());
//    }
//
//    @Test
//    void findByValue_shouldReturnAccount() {
//        Account account = Account.builder().id(1L).accountNumber("X123").build();
//        when(accountDao.findByValue("X123")).thenReturn(Optional.of(account));
//
//        Optional<Account> result = service.findByValue("X123");
//
//        assertTrue(result.isPresent());
//        assertEquals("X123", result.get().getAccountNumber());
//    }
//
//    @Test
//    void update_shouldUpdateIfExists() {
//        Account existing = Account.builder()
//                .id(1L)
//                .accountNumber("OLD")
//                .balance(new BigDecimal("10"))
//                .currencyId(Currency.builder().id(1L).build())
//                .issuingBankId(IssuingBank.builder().id(2L).build())
//                .build();
//
//        AccountDto dto = AccountDto.builder()
//                .accountNumber("NEW")
//                .balance(new BigDecimal("999"))
//                .currencyId(1L)
//                .issuingBankId(2L)
//                .build();
//
//        when(accountDao.findById(1L)).thenReturn(Optional.of(existing));
//        when(currencyDao.findById(1L)).thenReturn(Optional.of(Currency.builder().id(1L).build()));
//        when(issuingBankDao.findById(2L)).thenReturn(Optional.of(IssuingBank.builder().id(2L).build()));
//
//        Optional<Account> result = service.update(1L, dto);
//
//        assertTrue(result.isPresent());
//        assertEquals("NEW", result.get().getAccountNumber());
//        verify(accountDao).update(existing);
//    }
//
//    @Test
//    void update_shouldReturnEmptyIfAccountDoesNotExist() {
//        when(accountDao.findById(999L)).thenReturn(Optional.empty());
//
//        AccountDto dto = AccountDto.builder().accountNumber("X").balance(BigDecimal.ZERO).currencyId(1L).issuingBankId(1L).build();
//
//        Optional<Account> result = service.update(999L, dto);
//        assertTrue(result.isEmpty());
//        verify(accountDao, never()).update(any());
//    }
//
//    @Test
//    void delete_shouldReturnTrueIfDeleted() {
//        when(accountDao.delete(1L)).thenReturn(true);
//        assertTrue(service.delete(1L));
//    }
//
//    @Test
//    void deleteAll_shouldReturnTrueIfDeleted() {
//        when(accountDao.deleteAll()).thenReturn(true);
//        assertTrue(service.deleteAll());
//    }
//
//    @Test
//    void dropTable_shouldReturnTrueIfDropped() {
//        when(accountDao.dropTable()).thenReturn(true);
//        assertTrue(service.dropTable());
//    }
}