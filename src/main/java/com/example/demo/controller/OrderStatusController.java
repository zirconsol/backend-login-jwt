package com.example.demo.controller;

import com.example.demo.model.OrderStatus;
import com.example.demo.services.OrderStatusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth/order-statuses")
public class OrderStatusController {
    private final OrderStatusService service;

    public OrderStatusController(OrderStatusService service) {
        this.service = service;
    }

    @GetMapping
    public List<OrderStatus> getAllStatuses() {
        return service.getAllStatuses();
    }
}
