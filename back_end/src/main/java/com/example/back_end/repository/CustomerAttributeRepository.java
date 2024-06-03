package com.example.back_end.repository;

import com.example.back_end.entity.CustomerAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAttributeRepository extends JpaRepository<CustomerAttribute, Long> {
}
