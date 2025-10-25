package com.example.demo.dto;

public class ProductionOrderItemDto {
    private String productType;
    private Integer widthMm;
    private Integer heightMm;
    private Integer quantity;
    private Long profileId;
    private Long glassTypeId;
    private Long hardwareKitId;
    private String notes;


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
}
