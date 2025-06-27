package com.example.demo.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderStatus {

    @Id
    @Column(name = "status_id")
    private Short id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column
    private String description;
}
