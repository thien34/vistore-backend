package com.example.back_end.core.admin.customer.service.impl;

import com.example.back_end.core.admin.customer.mapper.AddressMapper;
import com.example.back_end.core.admin.customer.payload.request.AddressRequest;
import com.example.back_end.core.admin.customer.payload.response.AddressResponse;
import com.example.back_end.core.admin.customer.service.AddressService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.Address;
import com.example.back_end.entity.District;
import com.example.back_end.entity.Province;
import com.example.back_end.entity.Ward;
import com.example.back_end.infrastructure.constant.ErrorCode;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.exception.NotExistsException;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.AddressRepository;
import com.example.back_end.repository.WardRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressServiceImpl implements AddressService {

    AddressRepository addressRepository;
    AddressMapper addressMapper;
    WardRepository wardRepository;

    /**
     * Create new address with Ward, District, Province check
     *
     * @param request Address data from request
     */
    @Override
    @Transactional
    public void createAddress(AddressRequest request) {
        validateLocation(request.getWardId(), request.getDistrictId(), request.getProvinceId());

        Address address = addressMapper.toEntity(request);
        addressRepository.save(address);
    }

    /**
     * Update address with Ward, District, Province check
     *
     * @param id      ID of the address needs updating
     * @param request New address data
     */
    @Override
    @Transactional
    public void updateAddress(Long id, AddressRequest request) {
        validateLocation(request.getWardId(), request.getDistrictId(), request.getProvinceId());

        Address address = findAddressById(id);
        addressMapper.updateAddressFromRequest(request, address);
        addressRepository.save(address);
    }

    /**
     * Validate Ward -> District -> Province
     *
     * @param wardId     Ward code
     * @param districtId District code
     * @param provinceId Province code
     */
    private void validateLocation(String wardId, String districtId, String provinceId) {
        // Find commune by wardId
        Ward ward = wardRepository.findByCode(String.valueOf(wardId));
        if (ward == null)
            throw new NotFoundException("Ward with ID " + wardId + " not found");

        //Check if the commune's district matches the districtId
        District district = ward.getDistrictCode();
        if (district == null || !district.getCode().equals(String.valueOf(districtId)))
            throw new NotExistsException("Ward does not belong to the provided District");

        //Check if the province of the district matches the provinceId
        Province province = district.getProvinceCode();
        if (province == null || !province.getCode().equals(provinceId))
            throw new NotExistsException("District does not belong to the provided Province");

    }

    @Override
    public PageResponse<List<AddressResponse>> getAll(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "id", SortType.DESC.getValue());
        Page<Address> addressPage = addressRepository.findAll(pageable);
        List<AddressResponse> addressResponses = addressMapper.toResponseList(addressPage.getContent());
        return PageResponse.<List<AddressResponse>>builder()
                .page(addressPage.getNumber())
                .size(addressPage.getSize())
                .totalPage(addressPage.getTotalPages())
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
    public void deleteAddress(Long id) {
        if (!addressRepository.existsById(id))
            throw new NotFoundException(ErrorCode.ADDRESS_NOT_FOUND.getMessage());

        addressRepository.deleteById(id);

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
