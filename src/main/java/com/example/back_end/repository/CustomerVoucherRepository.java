package com.example.back_end.repository;

import com.example.back_end.entity.CustomerVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerVoucherRepository extends JpaRepository<CustomerVoucher, Long> {
}