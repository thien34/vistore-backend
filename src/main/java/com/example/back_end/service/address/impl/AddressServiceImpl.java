package com.example.back_end.service.address.impl;

import com.example.back_end.core.admin.address.mapper.AddressMapper;
import com.example.back_end.core.admin.address.payload.request.AddressRequest;
import com.example.back_end.core.admin.address.payload.request.AddressSearchRequest;
import com.example.back_end.core.admin.address.payload.response.AddressResponse;
import com.example.back_end.core.admin.address.payload.response.AddressesResponse;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.entity.Address;
import com.example.back_end.entity.Customer;
import com.example.back_end.entity.District;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public PageResponse1<List<AddressesResponse>> getAllAddressById(AddressSearchRequest searchRequest) {

        Pageable pageable = PageUtils.createPageable(
                searchRequest.getPageNo(),
                searchRequest.getPageSize(),
                searchRequest.getSortBy(),
                searchRequest.getSortDir());

        Customer customer = Optional.ofNullable(searchRequest.getCustomerId())
                .flatMap(customerRepository::findById)
                .orElse(null);

        if (customer == null) {
            return PageResponse1.<List<AddressesResponse>>builder()
                    .totalItems(0L)
                    .totalPages(0)
                    .items(Collections.emptyList())
                    .build();
        }

        Specification<Address> spec = AddressSpecification.hasCustomerId(customer);

        Page<Address> addressPage = addressRepository.findAll(spec, pageable);

        List<AddressesResponse> addressResponsesList = addressPage.getContent().stream()
                .map(address -> {
                    AddressesResponse response = addressMapper.toResponses(address);
                    if (address.getProvince() != null && address.getDistrict() != null && address.getWard() != null) {
                        String addressDetail = address.getProvince().getName() + " " +
                                address.getDistrict().getName() + " " +
                                address.getWard().getName() + " " +
                                address.getAddressName();
                        response.setAddressDetail(addressDetail);
                        return response;
                    }
                    return response;
                })
                .toList();

        if (addressResponsesList.isEmpty()) {
            AddressesResponse addressesResponse = AddressesResponse
                    .builder()
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .build();
            Address address = Address.builder()
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .email(customer.getEmail())
                    .customer(customer)
                    .build();
            address = addressRepository.save(address);
            addressesResponse.setId(address.getId());
            addressResponsesList = List.of(addressesResponse);
        }

        return PageResponse1.<List<AddressesResponse>>builder()
                .totalItems(addressPage.getTotalElements())
                .totalPages(addressPage.getTotalPages())
                .items(addressResponsesList)
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
            throw new NotFoundException("Một hoặc nhiều địa chỉ không được tìm thấy với các id đã cho");

        addressRepository.deleteAll(addresses);
    }

    private void validateLocation(String wardId, String districtId, String provinceId) {

        Ward ward = Optional.ofNullable(wardRepository.findByCode(wardId))
                .orElseThrow(() -> new NotFoundException("Không tìm thấy phường với ID " + wardId));

        District district = Optional.ofNullable(ward.getDistrictCode())
                .filter(d -> d.getCode().equals(districtId))
                .orElseThrow(() -> new NotExistsException("Phường không thuộc Quận đã cung cấp"));

        Optional.ofNullable(district.getProvinceCode())
                .filter(p -> p.getCode().equals(provinceId))
                .orElseThrow(() -> new NotExistsException("Quận không thuộc Tỉnh đã cung cấp"));
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
            throw new NotExistsException("Địa chỉ không thuộc về khách hàng đã chỉ định");
        }
    }

}
