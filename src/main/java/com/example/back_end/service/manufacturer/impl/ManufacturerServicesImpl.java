package com.example.back_end.service.manufacturer.impl;

import com.example.back_end.core.admin.manufacturer.mapper.ManufacturerMapper;
import com.example.back_end.core.admin.manufacturer.payload.request.ManufacturerRequest;
import com.example.back_end.core.admin.manufacturer.payload.request.ManufacturerSearchRequest;
import com.example.back_end.core.admin.manufacturer.payload.response.ManufacturerNameResponse;
import com.example.back_end.core.admin.manufacturer.payload.response.ManufacturerResponse;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.entity.Manufacturer;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.ManufacturerRepository;
import com.example.back_end.service.manufacturer.ManufactureServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManufacturerServicesImpl implements ManufactureServices {

    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerMapper manufacturerMapper;

    @Override
    @Transactional
    public void createManufacturer(ManufacturerRequest manufacturerRequest) {

        Manufacturer manufacturer = manufacturerMapper.toEntity(manufacturerRequest);
        manufacturerRepository.save(manufacturer);
    }

    @Override
    public void updateManufacturer(Long id, ManufacturerRequest manufacturerRequest) {

        Manufacturer manufacturer = findManufacturerById(id);

        manufacturerMapper.updateManufacturer(manufacturerRequest, manufacturer);
        manufacturerRepository.save(manufacturer);
    }

    @Override
    public PageResponse1<List<ManufacturerResponse>> getAll(ManufacturerSearchRequest searchRequest) {

        Pageable pageable = PageUtils.createPageable(
                searchRequest.getPageNo(),
                searchRequest.getPageSize(),
                searchRequest.getSortBy(),
                searchRequest.getSortDir());

        Page<Manufacturer> manufacturerPage = manufacturerRepository.findManufacturer(searchRequest.getName(), pageable);

        List<ManufacturerResponse> manufacturerResponses = manufacturerMapper
                .toDtos(manufacturerPage.getContent());

        return PageResponse1.<List<ManufacturerResponse>>builder()
                .totalItems(manufacturerPage.getTotalElements())
                .totalPages(manufacturerPage.getTotalPages())
                .items(manufacturerResponses)
                .build();
    }

    @Override
    public ManufacturerResponse getManufacturer(Long manufacturerId) {

        Manufacturer manufacturer = findManufacturerById(manufacturerId);
        return manufacturerMapper.toDto(manufacturer);
    }

    @Override
    public void deleteListManufacturer(List<Long> manufacturerIds) {
        manufacturerRepository.deleteManufacturers(manufacturerIds);
    }

    @Override
    public List<ManufacturerNameResponse> getAlManufacturersName() {
        return manufacturerRepository.getManufacturerNameResponse();
    }

    private Manufacturer findManufacturerById(Long manufacturerId) {
        return manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà sản xuất có id: " + manufacturerId));
    }

}
