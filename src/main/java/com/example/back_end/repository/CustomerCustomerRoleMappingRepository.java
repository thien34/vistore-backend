package com.example.back_end.repository;

import com.example.back_end.entity.CustomerRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCustomerRoleMappingRepository extends JpaRepository<CustomerRoleMapping, Long> {
}