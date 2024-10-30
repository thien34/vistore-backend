package com.example.back_end.service.address.impl;

import com.example.back_end.core.admin.address.mapper.AddressMapper;
import com.example.back_end.core.admin.address.payload.request.AddressRequest;
import com.example.back_end.core.admin.address.payload.request.AddressSearchRequest;
import com.example.back_end.core.admin.address.payload.response.AddressResponse;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.entity.Address;
import com.example.back_end.entity.Customer;
import com.example.back_end.entity.District;
import com.example.back_end.entity.Province;
import com.example.back_end.entity.Ward;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.exception.NotExistsException;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.AddressRepository;
import com.example.back_end.repository.CustomerRepository;
import com.example.back_end.repository.WardRepository;
import com.example.back_end.service.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final WardRepository wardRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void createAddress(AddressRequest request) {

        validateCustomerExists(request.getCustomerId());
        validateLocation(request.getWardId(), request.getDistrictId(), request.getProvinceId());

        addressRepository.save(addressMapper.toEntity(request));
    }

    @Override
    public void updateAddress(Long id, AddressRequest request) {

        validateCustomerExists(request.getCustomerId());
        validateLocation(request.getWardId(), request.getDistrictId(), request.getProvinceId());

        Address address = findAddressById(id);
        validateAddressOwnership(address, request.getCustomerId());

        addressMapper.updateAddressFromRequest(request, address);
        addressRepository.save(address);
    }

    @Override
    public PageResponse1<List<AddressResponse>> getAllAddressById(AddressSearchRequest searchRequest) {

        Pageable pageable = PageUtils.createPageable(
                searchRequest.getPageNo(),
                searchRequest.getPageSize(),
                searchRequest.getSortBy(),
                searchRequest.getSortDir());

        Customer customer = searchRequest.getCustomerId() != null
                ? customerRepository.findById(searchRequest.getCustomerId()).orElse(null)
                : null;
        Specification<Address> spec = AddressSpecification.hasCustomerId(customer);

        Page<Address> addressPage = addressRepository.findAll(spec, pageable);
        List<AddressResponse> addressResponses = addressMapper.toResponseList(addressPage.getContent());

        return PageResponse1.<List<AddressResponse>>builder()
                .totalItems(addressPage.getTotalElements())
                .totalPages(addressPage.getTotalPages())
                .items(addressResponses)
                .build();
    }

    @Override
    public AddressResponse getAddressById(Long id) {

        Address address = findAddressById(id);
        return addressMapper.toResponse(address);
    }

    @Override
    @Transactional
    public void deleteAddresses(List<Long> ids) {
        List<Address> addresses = addressRepository.findAllById(ids);

        if (addresses.size() != ids.size())
            throw new NotFoundException("One or more addresses not found for the given ids");

        addressRepository.deleteAll(addresses);
    }

    private void validateLocation(String wardId, String districtId, String provinceId) {

        Ward ward = wardRepository.findByCode(wardId);
        if (ward == null) {
            throw new NotFoundException("Ward with ID " + wardId + " not found");
        }

        District district = ward.getDistrictCode();
        if (district == null || !district.getCode().equals(districtId)) {
            throw new NotExistsException("Ward does not belong to the provided District");
        }

        Province province = district.getProvinceCode();
        if (province == null || !province.getCode().equals(provinceId)) {
            throw new NotExistsException("District does not belong to the provided Province");
        }
    }

    private Address findAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ADDRESS_NOT_FOUND.getMessage()));
    }

    private void validateCustomerExists(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new NotFoundException(ErrorCode.CUSTOMER_NOT_FOUND.getMessage());
        }
    }

    private void validateAddressOwnership(Address address, Long customerId) {
        if (!address.getCustomer().getId().equals(customerId)) {
            throw new NotExistsException("Address does not belong to the specified Customer");
        }
    }

}
