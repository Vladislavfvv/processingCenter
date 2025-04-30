package com.edme.pro.controller;
import com.edme.pro.dto.IssuingBankDto;
import com.edme.pro.model.IssuingBank;
import com.edme.pro.service.IssuingBankDtoSpringService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/issuingBanks")
public class IssuingBankController {
    private final IssuingBankDtoSpringService issuingBankService;

    @Autowired
    public IssuingBankController(IssuingBankDtoSpringService issuingBankService) {
        this.issuingBankService = issuingBankService;
    }

    @GetMapping
    public ResponseEntity<List<IssuingBank>> getAll() {
        return ResponseEntity.ok(issuingBankService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return issuingBankService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PostMapping
//    public ResponseEntity<?> create(@RequestBody @Valid IssuingBankDto issuingBankDto) {
//        return issuingBankService.save(issuingBankDto)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.badRequest().body("Банк с таким названием уже существует"));
//    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid IssuingBankDto issuingBankDto) {
        Optional<IssuingBank> savedBank = issuingBankService.save(issuingBankDto);
        if (savedBank.isPresent()) {
            return ResponseEntity.ok(savedBank.get());
        } else {
            return ResponseEntity.badRequest().body("Банк с таким названием уже существует");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<IssuingBank> update(@PathVariable("id") Long id, @RequestBody @Valid IssuingBankDto issuingBankDto) {
        log.info("Updating IssuingBank with id {}", id);
        return issuingBankService.update(id, issuingBankDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<IssuingBank> delete(@PathVariable Long id) {
        log.info("Deleting IssuingBank with id {}", id);
        return issuingBankService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
