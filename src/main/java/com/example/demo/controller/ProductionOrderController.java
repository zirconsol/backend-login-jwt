package com.example.demo.controller;

import com.example.demo.dto.ProductionOrderDto;
import com.example.demo.dto.ProductionOrderItemDto;
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
    public ResponseEntity<ProductionOrderDto> update(@PathVariable Long id, @Valid @RequestBody ProductionOrderDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // =======================
    // ÍTEMS de la ProductionOrder
    // =======================
    @PostMapping("/{id}/items")
    public ResponseEntity<String> addItems(
            @PathVariable Long id,
            @Valid @RequestBody List<ProductionOrderItemDto> items) {
        service.addItems(id, items);
        return ResponseEntity.ok("Items added successfully");
    }

    /**
     * Devuelve los ítems actuales de la orden.
     */
    @GetMapping("/{id}/items")
    public ResponseEntity<List<ProductionOrderItem>> getItems(@PathVariable Long id) {
        return ResponseEntity.ok(service.getItemsByOrderId(id));
    }

    /**
     * Elimina todos los ítems de la orden (opcional).
     */
    @DeleteMapping("/{id}/items")
    public ResponseEntity<Void> clearItems(@PathVariable Long id) {
        // Reutilizamos addItems con lista vacía para dejar la orden sin ítems
        service.addItems(id, List.of());
        return ResponseEntity.noContent().build();
    }
}