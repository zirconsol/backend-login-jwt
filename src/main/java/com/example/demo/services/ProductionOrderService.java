package com.example.demo.services;

import com.example.demo.dto.ProductionOrderDto;
import com.example.demo.model.ProductionOrder;
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
}