package com.edme.pro.controller;

import com.edme.pro.dto.PaymentSystemDto;
import com.edme.pro.model.PaymentSystem;
import com.edme.pro.service.PaymentSystemDtoSpringService;
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

class PaymentSystemControllerTest {
    @Mock
    private PaymentSystemDtoSpringService paymentSystemService;

    @InjectMocks
    private PaymentSystemController paymentSystemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        List<PaymentSystem> systems = List.of(new PaymentSystem(1L, "Visa"), new PaymentSystem(2L, "Mastercard"));
        when(paymentSystemService.findAll()).thenReturn(systems);

        ResponseEntity<List<PaymentSystem>> response = paymentSystemController.getAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void testGetById_Found() {
        PaymentSystem system = new PaymentSystem(1L, "Visa");
        when(paymentSystemService.findById(1L)).thenReturn(Optional.of(system));

        ResponseEntity<?> response = paymentSystemController.getById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(system, response.getBody());
    }

    @Test
    void testGetById_NotFound() {
        when(paymentSystemService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = paymentSystemController.getById(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreatePaymentSystem_Success() {
        PaymentSystemDto dto = new PaymentSystemDto();
        PaymentSystem saved = new PaymentSystem(1L, "Visa");

        when(paymentSystemService.save(dto)).thenReturn(Optional.of(saved));

        ResponseEntity<?> response = paymentSystemController.createPaymentSystem(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(saved, response.getBody());
    }

    @Test
    void testCreatePaymentSystem_Conflict() {
        PaymentSystemDto dto = new PaymentSystemDto();

        when(paymentSystemService.save(dto)).thenReturn(Optional.empty());

        ResponseEntity<?> response = paymentSystemController.createPaymentSystem(dto);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("PaymentSystem с таким названием уже существует", response.getBody());
    }

    @Test
    void testUpdatePaymentSystem_Found() {
        PaymentSystemDto dto = new PaymentSystemDto();
        PaymentSystem updated = new PaymentSystem(1L, "Updated");

        when(paymentSystemService.update(1L, dto)).thenReturn(Optional.of(updated));

        ResponseEntity<PaymentSystem> response = paymentSystemController.updatePaymentSystem(1L, dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updated, response.getBody());
    }

    @Test
    void testUpdatePaymentSystem_NotFound() {
        PaymentSystemDto dto = new PaymentSystemDto();

        when(paymentSystemService.update(1L, dto)).thenReturn(Optional.empty());

        ResponseEntity<PaymentSystem> response = paymentSystemController.updatePaymentSystem(1L, dto);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeletePaymentSystem_Success() {
        when(paymentSystemService.delete(1L)).thenReturn(true);

        ResponseEntity<Void> response = paymentSystemController.deletePaymentSystem(1L);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testDeletePaymentSystem_NotFound() {
        when(paymentSystemService.delete(1L)).thenReturn(false);

        ResponseEntity<Void> response = paymentSystemController.deletePaymentSystem(1L);

        assertEquals(404, response.getStatusCodeValue());
    }
}