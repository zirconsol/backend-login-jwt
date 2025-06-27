package com.example.demo.services;


import com.example.demo.model.OrderStatus;
import com.example.demo.repositories.OrderStatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    public OrderStatusService(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    public List<OrderStatus> getAllStatuses() {
        return orderStatusRepository.findAll();
    }
}
