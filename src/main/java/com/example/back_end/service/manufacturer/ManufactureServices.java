package com.example.back_end.service.manufacturer;

import com.example.back_end.core.admin.manufacturer.payload.request.ManufacturerRequest;
import com.example.back_end.core.admin.manufacturer.payload.request.ManufacturerSearchRequest;
import com.example.back_end.core.admin.manufacturer.payload.response.ManufacturerNameResponse;
import com.example.back_end.core.admin.manufacturer.payload.response.ManufacturerResponse;
import com.example.back_end.core.common.PageResponse1;

import java.util.List;

public interface ManufactureServices {

    void createManufacturer(ManufacturerRequest manufacturerRequest);

    void updateManufacturer(Long id, ManufacturerRequest manufacturerRequest);

    PageResponse1<List<ManufacturerResponse>> getAll(ManufacturerSearchRequest searchRequest);

    ManufacturerResponse getManufacturer(Long id);

    void deleteListManufacturer(List<Long> ids);

    List<ManufacturerNameResponse> getAlManufacturersName();

}
