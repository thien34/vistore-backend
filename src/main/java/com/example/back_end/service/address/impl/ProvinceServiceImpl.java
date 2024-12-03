package com.example.back_end.service.address.impl;

import com.example.back_end.core.admin.address.mapper.ProvinceMapper;
import com.example.back_end.core.admin.address.payload.response.ProvinceResponse;
import com.example.back_end.core.client.address.ProvinceApiResponse;
import com.example.back_end.entity.Province;
import com.example.back_end.repository.ProvinceRepository;
import com.example.back_end.service.address.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;
    private final ProvinceMapper provinceMapper;

    @Override
    public List<ProvinceResponse> getAllProvince() {

        List<Province> response = provinceRepository.findAll();
        return provinceMapper.toResponseList(response);
    }

    @Override
    public void syncProvinces(List<ProvinceApiResponse> provinceApiResponses) {
        List<Province> provincesToSave = provinceApiResponses.stream()
                .map(this::convertToProvince)
                .toList();
        provinceRepository.saveAll(provincesToSave);
    }

    private Province convertToProvince(ProvinceApiResponse apiProvince) {
        Province province = new Province();
        province.setCode(apiProvince.getProvinceID().toString());
        province.setName(apiProvince.getProvinceName());
        return province;
    }

}
