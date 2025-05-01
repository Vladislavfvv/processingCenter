package com.edme.pro.controller;

import com.edme.pro.dto.CurrencyDto;
import com.edme.pro.model.Currency;
import com.edme.pro.service.CurrencySpringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CurrencyControllerTest {
    @Mock
    private CurrencySpringService currencySpringService;

    @InjectMocks
    private CurrencyController currencyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        List<Currency> currencies = List.of(
                new Currency(1L, "840", "USD", "US Dollar"),
                new Currency(2L, "978", "EUR", "Euro")
        );
        when(currencySpringService.findAll()).thenReturn(currencies);

        ResponseEntity<List<Currency>> response = currencyController.getAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals("USD", response.getBody().get(0).getCurrencyLetterCode());
    }

    @Test
    void testGetCurrencyById_Found() {
        Currency currency = new Currency(1L, "840", "USD", "US Dollar");
        when(currencySpringService.findById(1L)).thenReturn(Optional.of(currency));

        ResponseEntity<?> response = currencyController.getCurrencyById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(currency, response.getBody());
    }

    @Test
    void testGetCurrencyById_NotFound() {
        when(currencySpringService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = currencyController.getCurrencyById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testCreateCurrency_Success() {
        CurrencyDto dto = new CurrencyDto(1L, "840", "USD", "US Dollar");
        Currency saved = new Currency(1L, "840", "USD", "US Dollar");

        when(currencySpringService.save(dto)).thenReturn(Optional.of(saved));

        ResponseEntity<?> response = currencyController.createCurrency(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(saved, response.getBody());
    }

    @Test
    void testCreateCurrency_Duplicate() {
        CurrencyDto dto = new CurrencyDto(1L, "840", "USD", "US Dollar");

        when(currencySpringService.save(dto)).thenReturn(Optional.empty());

        ResponseEntity<?> response = currencyController.createCurrency(dto);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Currency с таким названием уже существует", response.getBody());
    }

    @Test
    void testUpdateCurrency_Found() {
        CurrencyDto dto = new CurrencyDto(1L, "840", "USD", "US Dollar");
        Currency updated = new Currency(1L, "840", "USD", "US Dollar");

        when(currencySpringService.update(1L, dto)).thenReturn(Optional.of(updated));

        ResponseEntity<Currency> response = currencyController.updateCurrency(1L, dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updated, response.getBody());
    }

    @Test
    void testUpdateCurrency_NotFound() {
        CurrencyDto dto = new CurrencyDto(1L, "840", "USD", "US Dollar");

        when(currencySpringService.update(1L, dto)).thenReturn(Optional.empty());

        ResponseEntity<Currency> response = currencyController.updateCurrency(1L, dto);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteCurrency_Success() {
        when(currencySpringService.delete(1L)).thenReturn(true);

        ResponseEntity<Void> response = currencyController.deleteCurrency(1L);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteCurrency_NotFound() {
        when(currencySpringService.delete(1L)).thenReturn(false);

        ResponseEntity<Void> response = currencyController.deleteCurrency(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}