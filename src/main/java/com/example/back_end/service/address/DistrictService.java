package com.example.back_end.service.address;

import com.example.back_end.core.admin.address.payload.response.DistrictResponse;
import com.example.back_end.core.client.address.DistrictApiResponse;

import java.util.List;

public interface DistrictService {

    List<DistrictResponse> getAllDistrictByID(String codeProvince);

    void syncDistrict(List<DistrictApiResponse> districtApiResponses);

}
