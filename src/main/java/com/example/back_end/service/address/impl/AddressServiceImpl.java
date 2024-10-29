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
import org.springframework.data.domain.Pageable;
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
    @Transactional
    public void createAddress(AddressRequest request) {
        validateLocation(request.getWardId(), request.getDistrictId(), request.getProvinceId());

        Address savedAddress = addressRepository.save(addressMapper.toEntity(request));
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_NOT_FOUND.getMessage()));
    }

    @Override
    @Transactional
    public void updateAddress(Long id, AddressRequest request) {
        validateLocation(request.getWardId(), request.getDistrictId(), request.getProvinceId());

        // Check if the address belongs to the customer
        Address address = findAddressById(id);

//        boolean addressBelongsToCustomer = address.getCustomerAddressMappings()
//                .stream()
//                .anyMatch(mapping -> mapping.getCustomer().getId().equals(request.getCustomerId()));
//
//        if (!addressBelongsToCustomer)
//            throw new NotExistsException("Address does not belong to the specified Customer");

        addressMapper.updateAddressFromRequest(request, address);
        addressRepository.save(address);
    }


    private void validateLocation(String wardId, String districtId, String provinceId) {
        // Find commune by wardId
        Ward ward = wardRepository.findByCode(wardId);
        if (ward == null)
            throw new NotFoundException("Ward with ID " + wardId + " not found");

        //Check if the commune's district matches the districtId
        District district = ward.getDistrictCode();
        if (district == null || !district.getCode().equals(districtId))
            throw new NotExistsException("Ward does not belong to the provided District");

        //Check if the province of the district matches the provinceId
        Province province = district.getProvinceCode();
        if (province == null || !province.getCode().equals(provinceId))
            throw new NotExistsException("District does not belong to the provided Province");
    }

    @Override
    public PageResponse1<List<AddressResponse>> getAllAddressById(AddressSearchRequest searchRequest) {

        Pageable pageable = PageUtils.createPageable(
                searchRequest.getPageNo(),
                searchRequest.getPageSize(),
                searchRequest.getSortBy(),
                searchRequest.getSortDir());

//        Page<Address> addressPage = addressRepository.findAllByCustomerId(searchRequest.getCustomerId(), pageable);
//
//        List<AddressResponse> addressResponses = addressMapper.toResponseList(addressPage.getContent());
//        return PageResponse1.<List<AddressResponse>>builder()
//                .totalItems(addressPage.getTotalElements())
//                .totalPages(addressPage.getTotalPages())
//                .items(addressResponses)
//                .build();
        return null;
    }


    @Override
    public AddressResponse getAddressById(Long id) {
        Address address = findAddressById(id);
        return addressMapper.toResponse(address);
    }

    private Address findAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ADDRESS_NOT_FOUND.getMessage()));
    }

    @Override
    @Transactional
    public void deleteAddresses(List<Long> ids) {
        List<Address> addresses = addressRepository.findAllById(ids);

        if (addresses.size() != ids.size())
            throw new NotFoundException("One or more addresses not found for the given ids");

        addressRepository.deleteAll(addresses);
    }

}
