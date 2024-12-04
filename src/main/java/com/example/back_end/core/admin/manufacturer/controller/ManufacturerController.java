package com.example.back_end.core.admin.manufacturer.controller;

import com.example.back_end.core.admin.manufacturer.payload.request.ManufacturerRequest;
import com.example.back_end.core.admin.manufacturer.payload.request.ManufacturerSearchRequest;
import com.example.back_end.core.admin.manufacturer.payload.response.ManufacturerNameResponse;
import com.example.back_end.core.admin.manufacturer.payload.response.ManufacturerResponse;
import com.example.back_end.core.common.PageResponse1;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.manufacturer.ManufactureServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/manufacturers")
public class ManufacturerController {

    private final ManufactureServices manufacturerServices;

    @GetMapping()
    public ResponseData<PageResponse1<List<ManufacturerResponse>>> getAllManufacturers(
            @ParameterObject ManufacturerSearchRequest searchRequest) {

        PageResponse1<List<ManufacturerResponse>> response = manufacturerServices.getAll(searchRequest);

        return ResponseData.<PageResponse1<List<ManufacturerResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy toàn bộ nhà sản xuất thành công")
                .data(response)
                .build();
    }

    @PostMapping
    public ResponseData<Void> createManufacturer(@RequestBody @Valid ManufacturerRequest manufacturerRequest) {

        manufacturerServices.createManufacturer(manufacturerRequest);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Thêm nhà sản xuất mới thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<Void> update(
            @PathVariable Long id,
            @RequestBody @Valid ManufacturerRequest manufacturerRequest) {

        manufacturerServices.updateManufacturer(id, manufacturerRequest);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Cập nhật nhà sản xuất thành công")
                .build();
    }

    @GetMapping("/list-name")
    public ResponseData<List<ManufacturerNameResponse>> getAllManufacturersName() {

        List<ManufacturerNameResponse> manufacturerNameResponses = manufacturerServices.getAlManufacturersName();

        return ResponseData.<List<ManufacturerNameResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy tất cả tên nhà sản xuất thành công")
                .data(manufacturerNameResponses)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<ManufacturerResponse> getManufacturerById(@PathVariable Long id) {

        ManufacturerResponse manufacturerResponse = manufacturerServices.getManufacturer(id);

        return ResponseData.<ManufacturerResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy nhà sản xuất thành công")
                .data(manufacturerResponse)
                .build();
    }

    @DeleteMapping
    public ResponseData<Void> deleteManufacturers(@RequestBody List<Long> ids) {

        manufacturerServices.deleteListManufacturer(ids);

        return ResponseData.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Xóa nhà sản xuất thành công")
                .build();
    }

}
