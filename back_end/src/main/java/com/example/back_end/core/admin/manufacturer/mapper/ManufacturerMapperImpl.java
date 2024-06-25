package com.example.back_end.core.admin.manufacturer.mapper;

import com.example.back_end.core.admin.manufacturer.payload.request.ManufacturerRequest;
import com.example.back_end.core.admin.manufacturer.payload.response.ManufacturerResponse;
import com.example.back_end.entity.Manufacturer;
import org.springframework.stereotype.Service;

@Service
public class ManufacturerMapperImpl implements ManufacturerMapper {
    @Override
    public ManufacturerResponse toDto(Manufacturer manufacturer) {
        ManufacturerResponse manufacturerResponse = new ManufacturerResponse();
        manufacturerResponse.setId(manufacturer.getId());
        manufacturerResponse.setName(manufacturer.getName());
        manufacturerResponse.setDescription(manufacturer.getDescription());
        manufacturerResponse.setLinkImg(manufacturer.getPicture().getLinkImg());
        manufacturerResponse.setPageSize(manufacturer.getPageSize());
        manufacturerResponse.setPriceRangeFiltering(manufacturer.getPriceRangeFiltering());
        manufacturerResponse.setPublished(manufacturer.getPublished());
        manufacturerResponse.setDeleted(manufacturer.getDeleted());
        manufacturerResponse.setDisplayOrder(manufacturer.getDisplayOrder());
        return manufacturerResponse;
    }

    @Override
    public Manufacturer toEntity(ManufacturerRequest manufacturerRequest){
        Manufacturer manufacturer= new Manufacturer();
        manufacturer.setName(manufacturerRequest.getName());
        manufacturer.setDescription(manufacturerRequest.getDescription());
        manufacturer.setPageSize(manufacturerRequest.getPageSize());
        manufacturer.setPublished(manufacturerRequest.getPublished());
        manufacturer.setDeleted(manufacturerRequest.getDeleted());
        manufacturer.setDisplayOrder(manufacturerRequest.getDisplayOrder());
        manufacturer.setDeleted(false);
        manufacturer.setPublished(true);
        manufacturer.setDisplayOrder(1);
        manufacturer.setPriceRangeFiltering(true);
        return manufacturer;
    }
}
