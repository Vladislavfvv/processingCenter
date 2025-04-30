package com.edme.pro.controller;

import com.edme.pro.dao.DaoInterfaceSpring;
import com.edme.pro.dto.CurrencyDto;
import com.edme.pro.model.Currency;
import com.edme.pro.service.CurrencySpringService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/currency")
public class CurrencyController {
    private final CurrencySpringService currencySpringService;

@Autowired
    public CurrencyController(CurrencySpringService currencySpringService) {
        this.currencySpringService = currencySpringService;
    }

    @GetMapping
    public ResponseEntity<List<Currency>> getAll() {
    return ResponseEntity.ok(currencySpringService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCurrencyById(@PathVariable("id") Long id) {
    return currencySpringService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

//    @PostMapping
//    public ResponseEntity<Currency> createCurrency(@RequestBody CurrencyDto currencyDto) {
//    return currencySpringService.save(currencyDto)
//            .map(ResponseEntity::ok)
//            .orElse(ResponseEntity.badRequest().build());
//    }

    @PostMapping
    public ResponseEntity<?> createCurrency(@RequestBody @Valid CurrencyDto currencyDto) {
        Optional<Currency> optionalCurrency = currencySpringService.save(currencyDto);
        if (optionalCurrency.isPresent()) {
            return ResponseEntity.ok(optionalCurrency.get());
        }else {
            return ResponseEntity.badRequest().body("Currency с таким названием уже существует");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Currency> updateCurrency(@PathVariable("id") Long id, @RequestBody @Valid CurrencyDto currencyDto) {
    log.info("Updating currency with id {}", id);
    return currencySpringService.update(id, currencyDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable Long id) {
    log.info("Deleting currency with id {}", id);
    return currencySpringService.delete(id)
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }
}
