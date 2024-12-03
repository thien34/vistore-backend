package com.example.back_end.service.address.impl;

import com.example.back_end.core.admin.address.mapper.WardMapper;
import com.example.back_end.core.admin.address.payload.response.WardResponse;
import com.example.back_end.core.client.address.WardApiResponse;
import com.example.back_end.entity.District;
import com.example.back_end.entity.Ward;
import com.example.back_end.repository.DistrictRepository;
import com.example.back_end.repository.WardRepository;
import com.example.back_end.service.address.WardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WardServiceImpl implements WardService {

    private final WardRepository wardRepository;
    private final WardMapper wardMapper;
    private final DistrictRepository districtRepository;

    @Override
    public List<WardResponse> getAllWardByDistrictCode(String districtCode) {

        District district = districtRepository.findById(districtCode).orElse(null);
        List<Ward> wards = wardRepository.findAllByDistrictCode(district);

        return wardMapper.toResponseList(wards);
    }

    @Override
    public void syncWard(List<WardApiResponse> wardApiResponses) {
        List<Ward> wards = wardApiResponses.stream()
                .map(apiResponse -> {
                    District district = districtRepository.findById(apiResponse.getDistrictId())
                            .orElseThrow(() -> new IllegalArgumentException("District not found"));
                    return convertToWard(apiResponse, district);
                })
                .toList();
        wardRepository.saveAll(wards);
    }

    private Ward convertToWard(WardApiResponse wardApiResponse, District district) {
        Ward ward = new Ward();
        ward.setCode(wardApiResponse.getWardCode());
        ward.setName(wardApiResponse.getWardName());
        ward.setDistrictCode(district);
        return ward;
    }
}
