package com.example.demo.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryItemDto {

    private String id;
    private String product_id;
    private String name;
    private String category;
    private int currentStock;
    private int minStock;
    private String unit;
    private String supplier;
    private String lastOrderDate;
}