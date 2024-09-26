package com.example.back_end.repository;

import com.example.back_end.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WardRepository extends JpaRepository<Ward, String> {
    Ward findByCode(String code);
}