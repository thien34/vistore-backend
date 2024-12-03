package com.example.back_end.service.address;

import com.example.back_end.core.admin.address.payload.response.ProvinceResponse;
import com.example.back_end.core.client.address.ProvinceApiResponse;

import java.util.List;

public interface ProvinceService {

    List<ProvinceResponse> getAllProvince();

    void syncProvinces(List<ProvinceApiResponse> provinceApiResponses);

}
