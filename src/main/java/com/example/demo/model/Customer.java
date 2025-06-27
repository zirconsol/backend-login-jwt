package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;


@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_uuid")
    @Builder.Default
    private java.util.UUID customerUUID = java.util.UUID.randomUUID();

    @Column(nullable = false)
    private String name;

    private String address;

    @Column(unique = true)
    private String email;

    private String phone;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();
}
