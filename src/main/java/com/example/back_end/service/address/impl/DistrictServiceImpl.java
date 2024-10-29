package com.example.back_end.service.address.impl;

import com.example.back_end.core.admin.address.payload.response.DistrictResponse;
import com.example.back_end.service.address.DistrictService;
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
