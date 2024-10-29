package com.example.back_end.service.address.impl;

import com.example.back_end.core.admin.address.mapper.ProvinceMapper;
import com.example.back_end.core.admin.address.payload.response.ProvinceResponse;
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

}
