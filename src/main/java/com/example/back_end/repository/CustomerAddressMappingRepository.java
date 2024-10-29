package com.example.back_end.repository;

import com.example.back_end.entity.CustomerAddressMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAddressMappingRepository extends JpaRepository<CustomerAddressMapping, Long> {
}