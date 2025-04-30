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
        return paymentSystemService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
