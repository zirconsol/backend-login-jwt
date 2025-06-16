package com.example.demo.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "inventory_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItem {

    @Id
    private Long id;

    @Column(name = "product_id")
    private String product_id;

    private String name;
    private String category;
    private int currentStock;
    private int minStock;
    private String unit;
    private String supplier;

    @Column(name = "last_order_date")
    private LocalDate lastOrderDate;
}
