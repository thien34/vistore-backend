package com.example.back_end.core.admin.manufacturer.mapper;

import com.example.back_end.core.admin.manufacturer.payload.request.ManufacturerRequest;
import com.example.back_end.core.admin.manufacturer.payload.response.ManufacturerResponse;
import com.example.back_end.entity.Manufacturer;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
public interface ManufacturerMapper {
    @Mapping(source = "picture.id", target = "pictureId")
    ManufacturerResponse toDto(Manufacturer manufacturer);

    Manufacturer toEntity(ManufacturerRequest manufacturerRequest);
}
