package com.example.back_end.core.admin.manufacturer.service.impl;

import com.example.back_end.core.admin.manufacturer.payload.request.ManufacturerRequest;
import com.example.back_end.core.admin.manufacturer.payload.response.ManufacturerNameResponse;
import com.example.back_end.core.admin.manufacturer.payload.response.ManufacturerResponse;
import com.example.back_end.core.admin.manufacturer.service.ManufactureServices;
import com.example.back_end.core.admin.manufacturer.mapper.ManufacturerMapper;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.Manufacturer;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import com.example.back_end.repository.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManufacturerServicesImpl implements ManufactureServices {
    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerMapper manufacturerMapper;

    @Transactional
    @Override
    public void createManufacturer(ManufacturerRequest manufacturerRequest) {
        Manufacturer manufacturer = manufacturerMapper.maptoManufacturer(manufacturerRequest);
        manufacturerRepository.save(manufacturer);
    }

    @Override
    @Transactional
    public void updateManufacturer(Long manufacturerId, ManufacturerRequest manufacturerRequest) {
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ResourceNotFoundException("Manufacturer with id not found: " + manufacturerId));
        manufacturerMapper.updateManufacturer(manufacturerRequest, manufacturer);
        manufacturerRepository.save(manufacturer);
    }

    @Override
    public PageResponse<?> getAll(String name, Boolean published, Integer pageNo, Integer pageSize) {
        if (pageNo - 1 < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Invalid page number or page size");
        }

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("id").descending());
        Page<Manufacturer> manufacturerPage = manufacturerRepository.findManufacturer(name, published, pageable);

        List<ManufacturerResponse> manufacturerResponses = manufacturerPage.getContent()
                .stream()
                .map(manufacturerMapper::maptoManufacturerResponse)
                .toList();

        return PageResponse.builder()
                .page(manufacturerPage.getNumber())
                .size(manufacturerPage.getSize())
                .totalPage(manufacturerPage.getTotalPages())
                .items(manufacturerResponses)
                .build();
    }

    @Override
    public ManufacturerResponse getManufacturer(Long manufacturerId) {
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ResourceNotFoundException("Manufacturer with id not found: " + manufacturerId));
        return manufacturerMapper.maptoManufacturerResponse(manufacturer);
    }

    @Override
    public void deleteListManufacturer(List<Long> manufacturerIds) {
        manufacturerRepository.deleteManufacturers(manufacturerIds);
    }


    @Override
    public List<ManufacturerNameResponse> getAlManufacturersName() {
        return manufacturerRepository.getManufacturerNameResponse();
    }
}
