package com.edme.pro.controller;

import com.edme.pro.dto.PaymentSystemDto;
import com.edme.pro.model.PaymentSystem;
import com.edme.pro.service.PaymentSystemDtoSpringService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/paymentSystems")
public class PaymentSystemController {
    private final PaymentSystemDtoSpringService paymentSystemService;

    @Autowired
    public PaymentSystemController(PaymentSystemDtoSpringService paymentSystemService) {
        this.paymentSystemService = paymentSystemService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentSystem>> getAll() {
        return ResponseEntity.ok(paymentSystemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return paymentSystemService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PostMapping
//    public ResponseEntity<PaymentSystem> createPaymentSystem(@RequestBody @Valid PaymentSystemDto paymentSystemDto) {
//        return paymentSystemService.save(paymentSystemDto)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.badRequest().build());
//    }

    @PostMapping
    public ResponseEntity<?> createPaymentSystem(@RequestBody @Valid PaymentSystemDto paymentSystemDto) {
        Optional<PaymentSystem> optionalPaymentSystem = paymentSystemService.save(paymentSystemDto);
        if (optionalPaymentSystem.isPresent()) {
            return ResponseEntity.ok(optionalPaymentSystem.get());
        } else {
            return ResponseEntity.badRequest().body("PaymentSystem с таким названием уже существует");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<PaymentSystem> updatePaymentSystem(@PathVariable("id") Long id, @RequestBody @Valid PaymentSystemDto paymentSystemDto) {
        log.info("Updating payment system with id {}", id);
        return paymentSystemService.update(id, paymentSystemDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentSystem(@PathVariable Long id) {
        log.info("Deleting payment system with id {}", id);
        boolean returned =  paymentSystemService.delete(id);

        return  returned ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }


    @PostMapping("/createTable")
    public ResponseEntity<String> createPaymentSystemTable() {
        log.info("Creating PaymentSystem table");
        boolean result = paymentSystemService.createTable();
        if (result) {
            return ResponseEntity.ok("Таблица PaymentSystem успешно создана");
        } else {
            return ResponseEntity.status(500).body("Ошибка при создании таблицы");
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllPaymentSystems() {
        log.info("Deleting all payment systems");
        boolean result = paymentSystemService.deleteAll();
        if (result) {
            return ResponseEntity.ok("Все записи удалены");
        } else {
            return ResponseEntity.status(500).body("Ошибка при удалении всех записей");
        }
    }

    @DeleteMapping("/drop")
    public ResponseEntity<String> dropPaymentSystemTable() {
        log.info("Dropping PaymentSystem table");
        boolean result = paymentSystemService.dropTable();
        if (result) {
            return ResponseEntity.ok("Таблица PaymentSystem удалена");
        } else {
            return ResponseEntity.status(500).body("Ошибка при удалении таблицы");
        }
    }

    @PostMapping("/fillTable")
    public ResponseEntity<String> fillDefaultPaymentSystems() {
        log.info("Inserting default payment systems...");
        boolean success = paymentSystemService.initializeTable();
        if (success) {
            return ResponseEntity.ok("Значения по умолчанию успешно добавлены.");
        } else {
            return ResponseEntity.status(500).body("Не удалось добавить значения по умолчанию.");
        }
    }
}
