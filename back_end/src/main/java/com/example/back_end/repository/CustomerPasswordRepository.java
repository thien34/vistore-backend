package com.example.back_end.repository;

import com.example.back_end.entity.CustomerPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerPasswordRepository extends JpaRepository<CustomerPassword, Long> {
}
