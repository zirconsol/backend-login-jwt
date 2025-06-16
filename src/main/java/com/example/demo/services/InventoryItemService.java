package com.example.demo.services;

import com.example.demo.dto.InventoryItemDto;
import com.example.demo.model.InventoryItem;
import com.example.demo.repositories.InventoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryItemService {

    private final InventoryItemRepository inventoryItemRepository;

    public List<InventoryItemDto> getAllItems() {
        return inventoryItemRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public InventoryItemDto addItem(InventoryItemDto dto) {
        InventoryItem item = toEntity(dto);
        InventoryItem savedItem = inventoryItemRepository.save(item);
        return toDto(savedItem);
    }

    private InventoryItemDto toDto(InventoryItem item) {
        InventoryItemDto dto = new InventoryItemDto();
        dto.setId(String.valueOf(item.getId()));
        dto.setProduct_id(item.getProduct_id());
        dto.setName(item.getName());
        dto.setCategory(item.getCategory());
        dto.setCurrentStock(item.getCurrentStock());
        dto.setMinStock(item.getMinStock());
        dto.setUnit(item.getUnit());
        dto.setSupplier(item.getSupplier());
        dto.setLastOrderDate(item.getLastOrderDate().toString());
        return dto;
    }

    private InventoryItem toEntity(InventoryItemDto dto) {
        InventoryItem item = new InventoryItem();
        item.setId(Long.valueOf(dto.getId()));
        item.setProduct_id(dto.getProduct_id());
        item.setName(dto.getName());
        item.setCategory(dto.getCategory());
        item.setCurrentStock(dto.getCurrentStock());
        item.setMinStock(dto.getMinStock());
        item.setUnit(dto.getUnit());
        item.setSupplier(dto.getSupplier());
        item.setLastOrderDate(java.time.LocalDate.parse(dto.getLastOrderDate()));
        return item;
    }
}
