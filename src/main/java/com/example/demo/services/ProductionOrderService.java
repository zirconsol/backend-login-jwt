package com.example.demo.services;

import com.example.demo.dto.ProductionOrderDto;
import com.example.demo.dto.ProductionOrderItemDto;
import com.example.demo.model.ProductionOrder;
import com.example.demo.model.ProductionOrderItem;
import com.example.demo.repositories.ProductionOrderItemRepository;
import com.example.demo.repositories.ProductionOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductionOrderService {

    private final ProductionOrderRepository repository;
    private final ProductionOrderItemRepository itemRepository;

    // ===============================
    // ======== CONVERSORES ==========
    // ===============================

    private ProductionOrderDto toDto(ProductionOrder entity) {
        return ProductionOrderDto.builder()
                .id(entity.getId())
                .orderUUID(entity.getOrderUUID())
                .orderNumber(entity.getOrderNumber())
                .customerId(entity.getCustomerId())
                .teamId(entity.getTeamId())
                .statusId(entity.getStatusId())
                .startDate(String.valueOf(entity.getStartDate()))
                .endDate(String.valueOf(entity.getEndDate()))
                .notes(entity.getNotes())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private ProductionOrder toEntity(ProductionOrderDto dto) {
        return ProductionOrder.builder()
                .id(dto.getId())
                .orderUUID(dto.getOrderUUID() != null ? dto.getOrderUUID() : UUID.randomUUID())
                .orderNumber(dto.getOrderNumber())
                .customerId(dto.getCustomerId())
                .teamId(dto.getTeamId())
                .statusId(dto.getStatusId())
                .startDate(LocalDate.parse(dto.getStartDate()))
                .endDate(LocalDate.parse(dto.getEndDate()))
                .notes(dto.getNotes())
                .build();
    }

    // ===============================
    // ====== CRUD PRINCIPAL =========
    // ===============================

    public ProductionOrderDto create(ProductionOrderDto dto) {
        ProductionOrder saved = repository.save(toEntity(dto));
        return toDto(saved);
    }

    public List<ProductionOrderDto> getAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ProductionOrderDto getById(Long id) {
        return repository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Production order not found"));
    }

    public ProductionOrderDto update(Long id, ProductionOrderDto dto) {
        ProductionOrder existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Production order not found"));

        existing.setOrderNumber(dto.getOrderNumber());
        existing.setCustomerId(dto.getCustomerId());
        existing.setTeamId(dto.getTeamId());
        existing.setStatusId(dto.getStatusId());
        existing.setStartDate(LocalDate.parse(dto.getStartDate()));
        existing.setEndDate(LocalDate.parse(dto.getEndDate()));
        existing.setNotes(dto.getNotes());

        ProductionOrder updated = repository.save(existing);
        return toDto(updated);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    // ===============================
    // ==== ÍTEMS DE PRODUCCIÓN ======
    // ===============================

    /**
     * Reemplaza los ítems de una orden por los nuevos recibidos.
     * Si la orden ya tenía ítems previos, los elimina antes de guardar los nuevos.
     */
    public void addItems(Long orderId, List<ProductionOrderItemDto> items) {
        ProductionOrder order = repository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // Borra ítems previos (si existen)
        itemRepository.deleteByOrderId(order.getId());

        for (ProductionOrderItemDto dto : items) {
            ProductionOrderItem item = new ProductionOrderItem();
            item.setOrderId(order.getId());
            item.setProductType(dto.getProductType());
            item.setWidthMm(dto.getWidthMm());
            item.setHeightMm(dto.getHeightMm());
            item.setQuantity(dto.getQuantity());
            item.setProfileId(dto.getProfileId());
            item.setGlassTypeId(dto.getGlassTypeId());
            item.setHardwareKitId(dto.getHardwareKitId());
            item.setNotes(dto.getNotes());
            itemRepository.save(item);
        }
    }

    /**
     * Devuelve los ítems asociados a una orden.
     */
    public List<ProductionOrderItem> getItemsByOrderId(Long orderId) {
        return itemRepository.findByOrderId(orderId);
    }
}