package com.example.demo.controller;

import com.example.demo.dto.InventoryItemDto;
import com.example.demo.services.InventoryItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/inventory")
@RequiredArgsConstructor
public class InventoryItemController {

    private final InventoryItemService inventoryItemService;

    @GetMapping
    public List<InventoryItemDto> getAllItems() {
        return inventoryItemService.getAllItems();
    }

    @PostMapping
    public InventoryItemDto addItem(@RequestBody InventoryItemDto itemDto) {
        return inventoryItemService.addItem(itemDto);
    }
}