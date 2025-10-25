package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data @Builder
public class ProductionOrderSummaryDto {
    private Long orderId;
    private List<ItemSummary> items;
    private Requirements requirements;

    @Data @Builder
    public static class ItemSummary {
        private Long id;
        private String productType;
        private Integer widthMm;
        private Integer heightMm;
        private Integer quantity;
        private double profileMeters;
        private double glassSquareMeters;
        private int hardwareUnits;
    }

    @Data @Builder
    public static class Requirements {
        private double totalProfileMeters;
        private double totalGlassSquareMeters;
        private int totalHardwareUnits;
    }
}
