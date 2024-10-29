package com.example.back_end.service.address.impl;

import com.example.back_end.core.admin.address.mapper.WardMapper;
import com.example.back_end.core.admin.address.payload.response.WardResponse;
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

}
