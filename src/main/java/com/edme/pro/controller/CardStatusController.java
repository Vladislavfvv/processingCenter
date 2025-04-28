package com.edme.pro.controller;

import com.edme.pro.dto.CardStatusDto;
import com.edme.pro.model.CardStatus;
import com.edme.pro.service.CardStatusDtoSpringService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/cardStatus")
public class CardStatusController {
    private final CardStatusDtoSpringService cardStatusService;

    @Autowired
    public CardStatusController(CardStatusDtoSpringService cardStatusService) {
        this.cardStatusService = cardStatusService;
    }

    @GetMapping
    public ResponseEntity<List<CardStatusDto>> getAll() {
        return ResponseEntity.ok(cardStatusService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getCardStatusById(@PathVariable ("id") Long id) {
        return cardStatusService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CardStatusDto> createCardStatus(@RequestBody CardStatusDto cardStatusDto) {
        return cardStatusService.save(cardStatusDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardStatusDto> updateCardStatus(@PathVariable("id") Long id, @RequestBody CardStatusDto cardStatusDto) {
        cardStatusDto.setId(id);
        log.info("cardStatus: {} updated", cardStatusDto);
        return cardStatusService.update(cardStatusDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardStatus(@PathVariable Long id) {
        return cardStatusService.delete(id)
                ? ResponseEntity.noContent().build()//ok, удалено, пустое тело с ответом 204
                : ResponseEntity.notFound().build();// нет статуса, 404 -  Not Found

    }
}
