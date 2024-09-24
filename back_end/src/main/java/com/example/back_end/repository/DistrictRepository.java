package com.example.back_end.repository;

import com.example.back_end.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<District, String> {
}