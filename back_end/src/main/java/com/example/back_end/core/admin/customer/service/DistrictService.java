package com.example.back_end.core.admin.customer.service;

import com.example.back_end.core.admin.customer.payload.response.DistrictResponse;

import java.util.List;

public interface DistrictService {
    List<DistrictResponse> getAllDistrict();
}
