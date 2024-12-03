package com.example.back_end.service.address;

import com.example.back_end.core.admin.address.payload.response.WardResponse;
import com.example.back_end.core.client.address.WardApiResponse;

import java.util.List;

public interface WardService {

    List<WardResponse> getAllWardByDistrictCode(String districtCode);

    void syncWard(List<WardApiResponse> wardApiResponses);

}
