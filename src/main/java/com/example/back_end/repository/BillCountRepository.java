package com.example.back_end.repository;

import com.example.back_end.entity.BillCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillCountRepository extends JpaRepository<BillCount, Integer> {
    BillCount findFirstByOrderByIdAsc();
}
