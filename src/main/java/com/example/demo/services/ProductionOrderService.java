package com.example.demo.services;

import com.example.demo.dto.ProductionOrderDto;
import com.example.demo.dto.ProductionOrderItemDto;
import com.example.demo.dto.ProductionOrderSummaryDto;
import com.example.demo.model.ProductionOrder;
import com.example.demo.model.ProductionOrderItem;
import com.example.demo.repositories.ProductionOrderItemRepository;
import com.example.demo.repositories.ProductionOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductionOrderService {

    private final ProductionOrderRepository repository;
    private final ProductionOrderItemRepository itemRepository;

    // Estados (Short)
    private static final Short IN_PROGRESS  = (short) 1;
    private static final Short SCHEDULED    = (short) 2;
    private static final Short FOR_DELIVERY = (short) 3;
    private static final Short COMPLETED    = (short) 4;

    // ===============================
    // ========= CONVERSORES =========
    // ===============================

    private ProductionOrderDto toDto(ProductionOrder entity) {
        return ProductionOrderDto.builder()
                .id(entity.getId())
                .orderUUID(entity.getOrderUUID())
                .orderNumber(entity.getOrderNumber())
                .customerId(entity.getCustomerId())
                .teamId(entity.getTeamId())
                .statusId(entity.getStatusId())
                .startDate(entity.getStartDate() != null ? entity.getStartDate().toString() : null)
                .endDate(entity.getEndDate() != null ? entity.getEndDate().toString() : null)
                .notes(entity.getNotes())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private ProductionOrder toEntity(ProductionOrderDto dto) {
        return ProductionOrder.builder()
                .id(dto.getId())
                .orderUUID(dto.getOrderUUID() != null ? dto.getOrderUUID() : UUID.randomUUID())
                .orderNumber(dto.getOrderNumber())
                .customerId(dto.getCustomerId())
                .teamId(dto.getTeamId())
                // CAMBIO: si no viene estado, default IN_PROGRESS (1)
                .statusId(dto.getStatusId() != null ? dto.getStatusId() : IN_PROGRESS)
                .startDate(dto.getStartDate() != null ? LocalDate.parse(dto.getStartDate()) : null)
                .endDate(dto.getEndDate() != null ? LocalDate.parse(dto.getEndDate()) : null)
                .notes(dto.getNotes())
                .build();
    }

    // ===============================
    // ========= CRUD PRINCIPAL ======
    // ===============================

    public ProductionOrderDto create(ProductionOrderDto dto) {
        ProductionOrder saved = repository.save(toEntity(dto));
        return toDto(saved);
    }

    public List<ProductionOrderDto> getAll() {
        return repository.findAll().stream().map(this::toDto).collect(toList());
    }

    public ProductionOrderDto getById(Long id) {
        return repository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Production order not found"));
    }

    public ProductionOrderDto update(Long id, ProductionOrderDto dto) {
        ProductionOrder existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Production order not found"));

        existing.setOrderNumber(dto.getOrderNumber());
        existing.setCustomerId(dto.getCustomerId());
        existing.setTeamId(dto.getTeamId());
        // si viene null no forzamos nada: puede mantenerse el actual
        existing.setStatusId(dto.getStatusId() != null ? dto.getStatusId() : existing.getStatusId());
        existing.setStartDate(dto.getStartDate() != null ? LocalDate.parse(dto.getStartDate()) : null);
        existing.setEndDate(dto.getEndDate() != null ? LocalDate.parse(dto.getEndDate()) : null);
        existing.setNotes(dto.getNotes());

        ProductionOrder updated = repository.save(existing);
        return toDto(updated);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    // ===============================
    // ===== ÍTEMS DE PRODUCCIÓN =====
    // ===============================

    public void addItems(Long orderId, List<ProductionOrderItemDto> items) {
        ProductionOrder order = repository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        itemRepository.deleteByOrderId(order.getId());

        for (ProductionOrderItemDto dto : items) {
            ProductionOrderItem item = new ProductionOrderItem();
            item.setOrderId(order.getId());
            item.setProductType(dto.getProductType());
            item.setWidthMm(dto.getWidthMm());
            item.setHeightMm(dto.getHeightMm());
            item.setQuantity(dto.getQuantity());
            item.setProfileId(dto.getProfileId());
            item.setGlassTypeId(dto.getGlassTypeId());
            item.setHardwareKitId(dto.getHardwareKitId());
            item.setNotes(dto.getNotes());
            itemRepository.save(item);
        }
    }

    public List<ProductionOrderItem> getItemsByOrderId(Long orderId) {
        return itemRepository.findByOrderId(orderId);
    }

    // ===============================
    // =========== SUMMARY ===========
    // ===============================

    public ProductionOrderSummaryDto buildSummary(Long orderId) {
        var order = repository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        var items = itemRepository.findByOrderId(order.getId());

        var itemSummaries = items.stream().map(it -> {
            double profile = 2.0 * (it.getWidthMm() + it.getHeightMm()) / 1000.0 * it.getQuantity();
            double glass   = (it.getWidthMm() * it.getHeightMm()) / 1_000_000.0 * it.getQuantity();
            int hardware   = it.getQuantity();

            return ProductionOrderSummaryDto.ItemSummary.builder()
                    .id(it.getId())
                    .productType(it.getProductType())
                    .widthMm(it.getWidthMm())
                    .heightMm(it.getHeightMm())
                    .quantity(it.getQuantity())
                    .profileMeters(round(profile))
                    .glassSquareMeters(round(glass))
                    .hardwareUnits(hardware)
                    .build();
        }).collect(toList());

        double totalProfile = itemSummaries.stream()
                .mapToDouble(ProductionOrderSummaryDto.ItemSummary::getProfileMeters).sum();
        double totalGlass = itemSummaries.stream()
                .mapToDouble(ProductionOrderSummaryDto.ItemSummary::getGlassSquareMeters).sum();
        int totalHardware = itemSummaries.stream()
                .mapToInt(ProductionOrderSummaryDto.ItemSummary::getHardwareUnits).sum();

        return ProductionOrderSummaryDto.builder()
                .orderId(order.getId())
                .items(itemSummaries)
                .requirements(ProductionOrderSummaryDto.Requirements.builder()
                        .totalProfileMeters(round(totalProfile))
                        .totalGlassSquareMeters(round(totalGlass))
                        .totalHardwareUnits(totalHardware)
                        .build())
                .build();
    }

    private static double round(double v) {
        return BigDecimal.valueOf(v).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }

    // ===============================
    // ======== FLUJO ESTADOS ========
    // ===============================

    private void assertStatus(ProductionOrder po, Short expected) {
        if (po.getStatusId() == null || !po.getStatusId().equals(expected)) {
            throw new IllegalStateException("Estado inválido. Esperado=" + expected + " actual=" + po.getStatusId());
        }
    }

    private void ensureOrderNumber(ProductionOrder po) {
        if (po.getOrderNumber() != null && !po.getOrderNumber().isBlank()) return;
        String num = "ORD-" + Year.now() + "-" + po.getId();
        po.setOrderNumber(num);
    }

    /** Confirmar: IN_PROGRESS -> SCHEDULED (y asegura orderNumber). */
    public ProductionOrderDto confirm(Long id) {
        var po = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // CAMBIO: confirmar sólo desde IN_PROGRESS
        assertStatus(po, IN_PROGRESS);
        ensureOrderNumber(po);
        po.setStatusId(SCHEDULED);
        repository.save(po);
        return toDto(po);
    }

    /** Iniciar producción: SCHEDULED -> IN_PROGRESS */
    public ProductionOrderDto start(Long id) {
        var po = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        assertStatus(po, SCHEDULED);
        po.setStatusId(IN_PROGRESS);
        repository.save(po);
        return toDto(po);
    }

    /** Terminar producción: IN_PROGRESS -> FOR_DELIVERY */
    public ProductionOrderDto finish(Long id) {
        var po = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        assertStatus(po, IN_PROGRESS);
        po.setStatusId(FOR_DELIVERY);
        repository.save(po);
        return toDto(po);
    }

    /** Entregar: FOR_DELIVERY -> COMPLETED */
    public ProductionOrderDto deliver(Long id) {
        var po = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        assertStatus(po, FOR_DELIVERY);
        po.setStatusId(COMPLETED);
        repository.save(po);
        return toDto(po);
    }
}