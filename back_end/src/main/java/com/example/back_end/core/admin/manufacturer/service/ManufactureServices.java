package com.example.back_end.core.admin.manufacturer.service;

import com.example.back_end.core.admin.manufacturer.payload.request.ManufacturerRequest;
import com.example.back_end.core.admin.manufacturer.payload.response.ManufacturerNameResponse;
import com.example.back_end.core.admin.manufacturer.payload.response.ManufacturerResponse;
import com.example.back_end.core.common.PageResponse;

import java.util.List;

public interface ManufactureServices {

    void createManufacturer(ManufacturerRequest manufacturerRequest);

    void updateManufacturer(Long manufacturerId, ManufacturerRequest manufacturerRequest);

    PageResponse<?> getAll(String name, Boolean published, Integer pageNo, Integer pageSize);

    ManufacturerResponse getManufacturer(Long manufacturerId);

    void deleteListManufacturer(List<Long> manufacturerIds);

    List<ManufacturerNameResponse> getAlManufacturersName();
}
