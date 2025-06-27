package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductionOrderDto {
    private Long id;
    private UUID orderUUID;

    @NotBlank(message="Order Number is mandatory")
    private String orderNumber;

    @NotNull(message="Customer ID is mandatory")
    private Long customerId;

    private Long teamId;
    private Short statusId;
    private String startDate;
    private String endDate;
    private String notes;
    private Instant createdAt;
}
