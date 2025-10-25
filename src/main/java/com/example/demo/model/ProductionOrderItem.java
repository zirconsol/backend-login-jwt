package com.example.demo.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;


@Entity
@Table(name = "production_order_items")
public class ProductionOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name="product_type", nullable = false)
    private String productType;

    @Column(name = "width_mm", nullable = false)
    private Integer widthMm;

    @Column(name = "height_mm", nullable = false)
    private Integer heightMm;

    @Column(nullable = false)
    private Integer quantity = 1;

    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "glass_type_id")
    private Long glassTypeId;

    @Column(name = "hardware_kit_id")
    private Long hardwareKitId;

    private String notes;

    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }

    public Integer getWidthMm() { return widthMm; }
    public void setWidthMm(Integer widthMm) { this.widthMm = widthMm; }

    public Integer getHeightMm() { return heightMm; }
    public void setHeightMm(Integer heightMm) { this.heightMm = heightMm; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Long getProfileId() { return profileId; }
    public void setProfileId(Long profileId) { this.profileId = profileId; }

    public Long getGlassTypeId() { return glassTypeId; }
    public void setGlassTypeId(Long glassTypeId) { this.glassTypeId = glassTypeId; }

    public Long getHardwareKitId() { return hardwareKitId; }
    public void setHardwareKitId(Long hardwareKitId) { this.hardwareKitId = hardwareKitId; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
