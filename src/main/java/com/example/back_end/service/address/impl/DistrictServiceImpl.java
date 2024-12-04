package com.example.back_end.service.address.impl;

import com.example.back_end.core.admin.address.mapper.DistrictMapper;
import com.example.back_end.core.admin.address.payload.response.DistrictResponse;
import com.example.back_end.core.client.address.DistrictApiResponse;
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

    @Override
    public void syncDistrict(List<DistrictApiResponse> districtApiResponses) {
        List<District> districts = districtApiResponses.stream()
                .map(apiResponse -> {
                    Province province = provinceRepository.findById(apiResponse.getProvinceId().toString())
                            .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tỉnh"));
                    return convertToDistrict(apiResponse, province);
                })
                .toList();
        districtRepository.saveAll(districts);
    }

    private District convertToDistrict(DistrictApiResponse apiDistrict, Province province) {
        District district = new District();
        district.setCode(apiDistrict.getDistrictId().toString());
        district.setName(apiDistrict.getDistrictName());
        district.setProvinceCode(province);

        return district;
    }

}
