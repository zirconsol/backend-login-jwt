package com.example.demo.services;

import com.example.demo.dto.InventorySummaryDto;
import com.example.demo.model.InventoryItem;
import com.example.demo.repositories.InventoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventorySummaryService {

    private final InventoryItemRepository inventoryItemRepository;

    public List<InventorySummaryDto> getCategorySummaries() {
        List<InventoryItem> allItems = inventoryItemRepository.findAll();

        Map<String, List<InventoryItem>> groupedByCategory = allItems.stream()
                .collect(Collectors.groupingBy(InventoryItem::getCategory));

        return groupedByCategory.entrySet().stream()
                .map(entry -> {
                    String category = entry.getKey();
                    List<InventoryItem> items = entry.getValue();
                    double total = items.stream().mapToDouble(InventoryItem::getCurrentStock).sum();
                    String unit = items.get(0).getUnit(); // se asume mismo unit por categoría
                    long belowMinStock = items.stream().filter(i -> i.getCurrentStock() < i.getMinStock()).count();
                    return new InventorySummaryDto(category, total, unit, belowMinStock);
                })
                .collect(Collectors.toList());
    }
}