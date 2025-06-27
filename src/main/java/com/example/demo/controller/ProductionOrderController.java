package com.example.demo.controller;

import com.example.demo.dto.ProductionOrderDto;
import com.example.demo.services.ProductionOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/auth/orders")
@RequiredArgsConstructor
public class ProductionOrderController {

    private final ProductionOrderService service;

    @PostMapping
    public ResponseEntity<ProductionOrderDto> create(@Valid @RequestBody ProductionOrderDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProductionOrderDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductionOrderDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductionOrderDto> update(@PathVariable Long id, @Valid @RequestBody ProductionOrderDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
