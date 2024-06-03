package com.example.back_end.repository;

import com.example.back_end.entity.CustomerAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAttributeValueRepository extends JpaRepository<CustomerAttributeValue, Long> {
}