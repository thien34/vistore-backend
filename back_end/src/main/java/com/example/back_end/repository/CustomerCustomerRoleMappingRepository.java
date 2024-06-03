package com.example.back_end.repository;

import com.example.back_end.entity.CustomerCustomerRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCustomerRoleMappingRepository extends JpaRepository<CustomerCustomerRoleMapping, Long> {
}