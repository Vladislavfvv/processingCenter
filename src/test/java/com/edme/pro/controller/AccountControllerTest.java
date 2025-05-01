package com.edme.pro.controller;

import com.edme.pro.dao.CurrencySpringDaoImpl;
import com.edme.pro.dto.AccountDto;
import com.edme.pro.model.Account;
import com.edme.pro.model.Currency;
import com.edme.pro.model.IssuingBank;
import com.edme.pro.service.AccountDtoSpringService;
import com.edme.pro.service.IssuingBankDtoSpringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AccountControllerTest {
    @Mock
    private AccountDtoSpringService accountService;

    @Mock
    private CurrencySpringDaoImpl currencySpringDao;

    @Mock
    private IssuingBankDtoSpringService issuingBankService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Account buildSampleAccount() {
        return Account.builder()
                .id(1L)
                .accountNumber("123456789")
                .balance(BigDecimal.valueOf(1000.50))
                .currencyId(new Currency())  // можно мокнуть если нужно
                .issuingBankId(new IssuingBank())
                .build();
    }

    private AccountDto buildSampleDto() {
        return AccountDto.builder()
                .id(null)
                .accountNumber("123456789")
                .balance(BigDecimal.valueOf(1000.50))
                .currencyId(1L)
                .issuingBankId(1L)
                .build();
    }

    @Test
    void testGetAllAccountsAsEntity() {
        List<Account> accounts = List.of(buildSampleAccount());

        when(accountService.findAll()).thenReturn(accounts);

        ResponseEntity<List<Account>> response = accountController.getAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetAllAccountsAsDto() {
        Account account = buildSampleAccount();
        when(accountService.findAll()).thenReturn(List.of(account));

        ResponseEntity<List<AccountDto>> response = accountController.getAllAccounts();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(account.getAccountNumber(), response.getBody().get(0).getAccountNumber());
    }

    @Test
    void testGetById_Found() {
        Account account = buildSampleAccount();

        when(accountService.findById(1L)).thenReturn(Optional.of(account));

        ResponseEntity<?> response = accountController.getById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(account, response.getBody());
    }

    @Test
    void testGetById_NotFound() {
        when(accountService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = accountController.getById(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testSave_Success() {
        AccountDto dto = buildSampleDto();
        Account savedAccount = buildSampleAccount();

        when(accountService.save(dto)).thenReturn(Optional.of(savedAccount));

        ResponseEntity<?> response = accountController.save(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(savedAccount, response.getBody());
    }

    @Test
    void testSave_Duplicate() {
        AccountDto dto = buildSampleDto();

        when(accountService.save(dto)).thenReturn(Optional.empty());

        ResponseEntity<?> response = accountController.save(dto);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Account с таким названием уже существует", response.getBody());
    }

    @Test
    void testUpdate_Found() {
        AccountDto dto = buildSampleDto();
        Account updated = buildSampleAccount();

        when(accountService.update(1L, dto)).thenReturn(Optional.of(updated));

        ResponseEntity<Account> response = accountController.update(1L, dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updated, response.getBody());
    }

    @Test
    void testUpdate_NotFound() {
        AccountDto dto = buildSampleDto();

        when(accountService.update(1L, dto)).thenReturn(Optional.empty());

        ResponseEntity<Account> response = accountController.update(1L, dto);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDelete_Success() {
        when(accountService.delete(1L)).thenReturn(true);

        ResponseEntity<Account> response = accountController.delete(1L);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testDelete_NotFound() {
        when(accountService.delete(1L)).thenReturn(false);

        ResponseEntity<Account> response = accountController.delete(1L);

        assertEquals(404, response.getStatusCodeValue());
    }
}