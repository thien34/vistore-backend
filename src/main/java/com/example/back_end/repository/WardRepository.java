package com.example.back_end.repository;

import com.example.back_end.entity.District;
import com.example.back_end.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WardRepository extends JpaRepository<Ward, String> {

    List<Ward> findAllByDistrictCode(District districtCode);

    Ward findByCode(String code);

}