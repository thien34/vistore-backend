package com.example.back_end.core.admin.manufacturer.mapper;

import com.example.back_end.core.admin.manufacturer.payload.request.ManufacturerRequest;
import com.example.back_end.core.admin.manufacturer.payload.response.ManufacturerResponse;
import com.example.back_end.entity.Manufacturer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ManufacturerMapper {

    @Mapping(target = "deleted", constant = "false")
    Manufacturer maptoManufacturer(ManufacturerRequest manufacturerRequest);

    @Mapping(source = "picture.id", target = "pictureId")
    ManufacturerResponse maptoManufacturerResponse(Manufacturer manufacturer);

    void updateManufacturer(ManufacturerRequest manufacturerRequest, @MappingTarget Manufacturer manufacturer);
}
