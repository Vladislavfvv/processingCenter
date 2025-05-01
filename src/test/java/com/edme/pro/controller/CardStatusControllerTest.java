package com.edme.pro.controller;

import com.edme.pro.dto.CardStatusDto;
import com.edme.pro.model.CardStatus;
import com.edme.pro.service.CardStatusDtoSpringService;
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

class CardStatusControllerTest {
    @Mock
    private CardStatusDtoSpringService cardStatusService;

    @InjectMocks
    private CardStatusController cardStatusController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        List<CardStatus> statuses = List.of(
                new CardStatus(1L, "ACTIVE"),
                new CardStatus(2L, "BLOCKED")
        );

        when(cardStatusService.findAll()).thenReturn(statuses);

        ResponseEntity<List<CardStatus>> response = cardStatusController.getAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetById_Found() {
        CardStatus status = new CardStatus(1L, "ACTIVE");

        when(cardStatusService.findById(1L)).thenReturn(Optional.of(status));

        ResponseEntity<?> response = cardStatusController.getCardStatusById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(status, response.getBody());
    }

    @Test
    void testGetById_NotFound() {
        when(cardStatusService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = cardStatusController.getCardStatusById(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreate_Success() {
        CardStatusDto dto = new CardStatusDto(null, "ACTIVE");
        CardStatus saved = new CardStatus(1L, "ACTIVE");

        when(cardStatusService.save(dto)).thenReturn(Optional.of(saved));

        ResponseEntity<?> response = cardStatusController.createCardStatus(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(saved, response.getBody());
    }

    @Test
    void testCreate_Duplicate() {
        CardStatusDto dto = new CardStatusDto(null, "DUPLICATE");

        when(cardStatusService.save(dto)).thenReturn(Optional.empty());

        ResponseEntity<?> response = cardStatusController.createCardStatus(dto);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Банк с таким названием уже существует", response.getBody());
    }

    @Test
    void testUpdate_Found() {
        CardStatusDto dto = new CardStatusDto(null, "UPDATED");
        CardStatus updated = new CardStatus(1L, "UPDATED");

        when(cardStatusService.update(1L, dto)).thenReturn(Optional.of(updated));

        ResponseEntity<CardStatus> response = cardStatusController.updateCardStatus(1L, dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updated, response.getBody());
    }

    @Test
    void testUpdate_NotFound() {
        CardStatusDto dto = new CardStatusDto(null, "UPDATED");

        when(cardStatusService.update(1L, dto)).thenReturn(Optional.empty());

        ResponseEntity<CardStatus> response = cardStatusController.updateCardStatus(1L, dto);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDelete_Success() {
        when(cardStatusService.delete(1L)).thenReturn(true);

        ResponseEntity<Void> response = cardStatusController.deleteCardStatus(1L);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testDelete_NotFound() {
        when(cardStatusService.delete(1L)).thenReturn(false);

        ResponseEntity<Void> response = cardStatusController.deleteCardStatus(1L);

        assertEquals(404, response.getStatusCodeValue());
    }
}