package com.example.demo.services;

import com.example.demo.dto.CustomerDto;
import com.example.demo.model.Customer;
import com.example.demo.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;

    private CustomerDto toDto(Customer entity) {
        return CustomerDto.builder()
                .id(entity.getId())
                .customerUUID(entity.getCustomerUUID())
                .name(entity.getName())
                .address(entity.getAddress())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private Customer toEntity(CustomerDto dto) {
        return Customer.builder()
                .id(dto.getId())
                .customerUUID(dto.getCustomerUUID() != null ? dto.getCustomerUUID() : UUID.randomUUID())
                .name(dto.getName())
                .address(dto.getAddress())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();
    }

    public CustomerDto createCustomer(CustomerDto dto) {
        Customer saved = customerRepository.save(toEntity(dto));
        return toDto(saved);
    }

    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public CustomerDto getCustomerById(Long id) {
        Customer entity = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return toDto(entity);
    }

    public CustomerDto updateCustomer(Long id, CustomerDto dto) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        existing.setName(dto.getName());
        existing.setAddress(dto.getAddress());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        Customer updated = customerRepository.save(existing);
        return toDto(updated);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
