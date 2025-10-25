package com.example.demo.repositories;


import com.example.demo.model.ProductionOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductionOrderItemRepository extends JpaRepository<ProductionOrderItem, Long> {
    List<ProductionOrderItem> findByOrderId(Long OrderId);
    void deleteByOrderId(Long OrderId);

}
