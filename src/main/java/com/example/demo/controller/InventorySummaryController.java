package com.example.demo.controller;

import com.example.demo.dto.InventorySummaryDto;
import com.example.demo.services.InventorySummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth/inventory/summary")
@RequiredArgsConstructor
public class InventorySummaryController {

    private final InventorySummaryService summaryService;

    @GetMapping
    public List<InventorySummaryDto> getSummary() {
        return summaryService.getCategorySummaries();
    }
}