package com.edme.pro.controller;

import com.edme.pro.dto.IssuingBankDto;
import com.edme.pro.model.IssuingBank;
import com.edme.pro.service.IssuingBankDtoSpringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class IssuingBankControllerTest {
    @Mock
    private IssuingBankDtoSpringService issuingBankService;

    @InjectMocks
    private IssuingBankController issuingBankController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        List<IssuingBank> banks = List.of(new IssuingBank(1L, "123456789", "12345", "Bank1"), new IssuingBank(2L, "987654321", "54321", "Bank2"));
        when(issuingBankService.findAll()).thenReturn(banks);

        ResponseEntity<List<IssuingBank>> response = issuingBankController.getAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void testGetById_Found() {
        IssuingBank bank = new IssuingBank(1L, "123456789", "12345", "Bank1");
        when(issuingBankService.findById(1L)).thenReturn(Optional.of(bank));

        ResponseEntity<?> response = issuingBankController.getById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(bank, response.getBody());
    }

    @Test
    void testGetById_NotFound() {
        when(issuingBankService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = issuingBankController.getById(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreate_Success() {
        IssuingBankDto dto = new IssuingBankDto(1L, "123456789", "12345", "Bank1");
        IssuingBank saved = new IssuingBank(1L, "123456789", "12345", "Bank1");

        when(issuingBankService.save(dto)).thenReturn(Optional.of(saved));

        ResponseEntity<?> response = issuingBankController.create(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(saved, response.getBody());
    }

    @Test
    void testCreate_Duplicate() {
        IssuingBankDto dto = new IssuingBankDto(1L, "123456789", "12345", "Bank1");

        when(issuingBankService.save(dto)).thenReturn(Optional.empty());

        ResponseEntity<?> response = issuingBankController.create(dto);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Банк с таким названием уже существует", response.getBody());
    }

    @Test
    void testUpdate_Found() {
        IssuingBankDto dto = new IssuingBankDto(1L, "123456789", "12345", "Bank1");
        IssuingBank updated = new IssuingBank(1L, "123456789", "12345", "Bank1");

        when(issuingBankService.update(1L, dto)).thenReturn(Optional.of(updated));

        ResponseEntity<IssuingBank> response = issuingBankController.update(1L, dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updated, response.getBody());
    }

    @Test
    void testUpdate_NotFound() {
        IssuingBankDto dto = new IssuingBankDto(1L, "123456789", "12345", "Bank1");

        when(issuingBankService.update(1L, dto)).thenReturn(Optional.empty());

        ResponseEntity<IssuingBank> response = issuingBankController.update(1L, dto);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDelete_Success() {
        when(issuingBankService.delete(1L)).thenReturn(true);

        ResponseEntity<IssuingBank> response = issuingBankController.delete(1L);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testDelete_NotFound() {
        when(issuingBankService.delete(1L)).thenReturn(false);

        ResponseEntity<IssuingBank> response = issuingBankController.delete(1L);

        assertEquals(404, response.getStatusCodeValue());
    }
}