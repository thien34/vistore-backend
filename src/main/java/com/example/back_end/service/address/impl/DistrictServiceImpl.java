package com.example.back_end.service.address.impl;

import com.example.back_end.core.admin.address.mapper.DistrictMapper;
import com.example.back_end.core.admin.address.payload.response.DistrictResponse;
import com.example.back_end.entity.District;
import com.example.back_end.entity.Province;
import com.example.back_end.repository.DistrictRepository;
import com.example.back_end.repository.ProvinceRepository;
import com.example.back_end.service.address.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;
    private final ProvinceRepository provinceRepository;

    @Override
    public List<DistrictResponse> getAllDistrictByID(String codeProvince) {

        Province province = provinceRepository.findById(codeProvince).orElse(null);
        List<District> districts = districtRepository.findAllByProvinceCode(province);

        return districtMapper.toResponseList(districts);
    }

}
