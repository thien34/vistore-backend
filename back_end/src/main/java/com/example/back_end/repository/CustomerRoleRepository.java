package com.example.back_end.repository;

import com.example.back_end.entity.CustomerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerRoleRepository extends JpaRepository<CustomerRole, Long>, JpaSpecificationExecutor<CustomerRole> {
}