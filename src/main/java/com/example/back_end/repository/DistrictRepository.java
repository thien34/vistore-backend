package com.example.back_end.repository;

import com.example.back_end.entity.District;
import com.example.back_end.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, String> {

    List<District> findAllByProvinceCode(Province idProvince);

}