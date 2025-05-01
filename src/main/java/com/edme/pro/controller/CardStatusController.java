package com.edme.pro.controller;

import com.edme.pro.dto.CardStatusDto;
import com.edme.pro.model.CardStatus;
import com.edme.pro.service.CardStatusDtoSpringService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/cardStatuses")
public class CardStatusController {
    private final CardStatusDtoSpringService cardStatusService;

    @Autowired
    public CardStatusController(CardStatusDtoSpringService cardStatusService) {
        this.cardStatusService = cardStatusService;
    }

    @GetMapping
    public ResponseEntity<List<CardStatus>> getAll() {
        return ResponseEntity.ok(cardStatusService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getCardStatusById(@PathVariable ("id") Long id) {
        return cardStatusService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PostMapping
//    public ResponseEntity<CardStatus> createCardStatus(@RequestBody CardStatusDto cardStatusDto) {
//        return cardStatusService.save(cardStatusDto)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.badRequest().build());
//    }
@PostMapping
public ResponseEntity<?> createCardStatus(@RequestBody @Valid CardStatusDto cardStatusDto) {
        Optional<CardStatus> cardStatus = cardStatusService.save(cardStatusDto);
        if (cardStatus.isPresent()) {
            return ResponseEntity.ok(cardStatus.get());
        } else {
            return ResponseEntity.badRequest().body("Банк с таким названием уже существует");
        }
}

    @PutMapping("/{id}")
    public ResponseEntity<CardStatus> updateCardStatus(@PathVariable("id") Long id, @RequestBody @Valid CardStatusDto cardStatusDto) {
      //  cardStatusDto.setId(id);
        log.info("cardStatus: {} updated", cardStatusDto);
        return cardStatusService.update(id, cardStatusDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardStatus(@PathVariable Long id) {
        return cardStatusService.delete(id)
                ? ResponseEntity.noContent().build()//ok, удалено, пустое тело с ответом 204
                : ResponseEntity.notFound().build();// нет статуса, 404 -  Not Found

    }

    @PostMapping("/createTable")
    public ResponseEntity<String> createCardStatusTable() {
        log.info("Creating CardStatus table");
        boolean result = cardStatusService.createTable();
        if (result) {
            return ResponseEntity.ok("Таблица CardStatus успешно создана");
        } else {
            return ResponseEntity.status(500).body("Ошибка при создании таблицы");
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllCardStatuses() {
        log.info("Deleting all CardStatus");
        boolean result = cardStatusService.deleteAll();
        if (result) {
            return ResponseEntity.ok("Все записи удалены");
        } else {
            return ResponseEntity.status(500).body("Ошибка при удалении всех записей");
        }
    }

    @DeleteMapping("/drop")
    public ResponseEntity<String> dropCardStatusTable() {
        log.info("Dropping CardStatus table");
        boolean result = cardStatusService.dropTable();
        if (result) {
            return ResponseEntity.ok("Таблица CardStatus удалена");
        } else {
            return ResponseEntity.status(500).body("Ошибка при удалении таблицы");
        }
    }

    @PostMapping("/fillTable")
    public ResponseEntity<String> fillDefaultCardStatuses() {
        log.info("Inserting default CardStatuses...");
        boolean success = cardStatusService.initializeTable();
        if (success) {
            return ResponseEntity.ok("Значения по умолчанию успешно добавлены.");
        } else {
            return ResponseEntity.status(500).body("Не удалось добавить значения по умолчанию.");
        }
    }
}
