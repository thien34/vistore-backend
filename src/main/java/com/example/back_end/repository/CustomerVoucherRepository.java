package com.example.back_end.repository;

import com.example.back_end.entity.Customer;
import com.example.back_end.entity.CustomerVoucher;
import com.example.back_end.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerVoucherRepository extends JpaRepository<CustomerVoucher, Long> {
    Optional<CustomerVoucher> findByCustomerAndDiscount(Customer customer, Discount discount);
    boolean existsByCustomerAndDiscount(Customer customer, Discount discount);

}