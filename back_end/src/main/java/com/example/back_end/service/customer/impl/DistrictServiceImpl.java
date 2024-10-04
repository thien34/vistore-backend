package com.example.back_end.service.customer.impl;

import com.example.back_end.core.admin.customer.payload.response.DistrictResponse;
import com.example.back_end.service.customer.DistrictService;
import com.example.back_end.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;

    @Override
    public List<DistrictResponse> getAllDistrict() {
        return districtRepository.findAll().stream()
                .map(district -> DistrictResponse.builder()
                        .code(district.getCode())
                        .provinceCode(district.getProvinceCode().getCode())
                        .nameEn(district.getNameEn())
                        .build()
                )
                .toList();
    }

}
