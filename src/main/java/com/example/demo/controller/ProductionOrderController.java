package com.example.demo.controller;

import com.example.demo.dto.ProductionOrderDto;
import com.example.demo.dto.ProductionOrderItemDto;
import com.example.demo.dto.ProductionOrderSummaryDto;
import com.example.demo.model.ProductionOrderItem;
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

    // =======================
    // CRUD de ProductionOrder
    // =======================

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
    public ResponseEntity<ProductionOrderDto> update(@PathVariable Long id,
                                                     @Valid @RequestBody ProductionOrderDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // =======================
    // Ítems de la orden
    // =======================

    @PostMapping("/{id}/items")
    public ResponseEntity<String> addItems(@PathVariable Long id,
                                           @Valid @RequestBody List<ProductionOrderItemDto> items) {
        service.addItems(id, items);
        return ResponseEntity.ok("Items added successfully");
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<ProductionOrderItem>> getItems(@PathVariable Long id) {
        return ResponseEntity.ok(service.getItemsByOrderId(id));
    }

    @DeleteMapping("/{id}/items")
    public ResponseEntity<Void> clearItems(@PathVariable Long id) {
        service.addItems(id, List.of());
        return ResponseEntity.noContent().build();
    }

    // =======================
    // Summary
    // =======================

    @GetMapping("/{id}/summary")
    public ResponseEntity<ProductionOrderSummaryDto> getSummary(@PathVariable Long id) {
        return ResponseEntity.ok(service.buildSummary(id));
    }

    // =======================
    // Flujo de estados
    // =======================

    @PostMapping("/{id}/confirm")
    public ResponseEntity<ProductionOrderDto> confirm(@PathVariable Long id) {
        return ResponseEntity.ok(service.confirm(id));
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<ProductionOrderDto> start(@PathVariable Long id) {
        return ResponseEntity.ok(service.start(id));
    }

    @PostMapping("/{id}/finish")
    public ResponseEntity<ProductionOrderDto> finish(@PathVariable Long id) {
        return ResponseEntity.ok(service.finish(id));
    }

    @PostMapping("/{id}/deliver")
    public ResponseEntity<ProductionOrderDto> deliver(@PathVariable Long id) {
        return ResponseEntity.ok(service.deliver(id));
    }
}