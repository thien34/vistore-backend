package com.example.back_end.core.admin.manufacturer.services;

import com.example.back_end.core.admin.manufacturer.payload.request.ManufacturerRequest;
import com.example.back_end.core.admin.manufacturer.payload.response.ManufacturerResponse;
import com.example.back_end.core.common.PageResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ManufactureServices {
    ManufacturerResponse addManufacturer(MultipartFile file, ManufacturerRequest manufacturer) ;
    ManufacturerResponse updateManufacturer(MultipartFile file, ManufacturerRequest manufacturerRequest);

    PageResponse<?> getAll(String name, Boolean published, int page, int size);

    ManufacturerResponse getManufacturer(Long id);
    @Transactional
    Boolean deleteManufacturer(Long id);
}
