package com.example.back_end.repository;

import com.example.back_end.entity.Customer;
import com.example.back_end.entity.CustomerPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerPasswordRepository extends JpaRepository<CustomerPassword, Long> {
    Optional<CustomerPassword> findByCustomer(Customer customer);
}
