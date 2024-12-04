package com.example.back_end.core.admin.product.controller;

import com.example.back_end.core.admin.product.payload.request.SpecificationAttributeGroupRequest;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeGroupNameResponse;
import com.example.back_end.core.admin.product.payload.response.SpecificationAttributeGroupResponse;
import com.example.back_end.service.product.SpecificationAttributeGroupService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/specification-attribute-groups")
public class SpecificationAttributeGroupController {

    private final SpecificationAttributeGroupService specificationAttributeGroupService;

    @Operation(method = "POST", summary = "Thêm nhóm thuộc tính đặc tả mới",
            description = "Gửi yêu cầu qua API này để tạo nhóm thuộc tính đặc tả mới")
    @PostMapping
    public ResponseData<SpecificationAttributeGroupResponse> createSpecificationAttributeOption(
            @Valid @RequestBody SpecificationAttributeGroupRequest dto) {

        SpecificationAttributeGroupResponse response = specificationAttributeGroupService
                .createSpecificationAttributeGroup(dto);

        return ResponseData.<SpecificationAttributeGroupResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Đặc điểm kỹ thuật được tạo thành công")
                .data(response)
                .build();
    }

    @Operation(method = "GET", summary = "Nhận tất cả các nhóm thuộc tính đặc tả",
            description = "Gửi yêu cầu qua API này để lấy tất cả các nhóm thuộc tính đặc tả")
    @GetMapping
    public ResponseData<PageResponse<List<SpecificationAttributeGroupResponse>>> getAll(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6") int pageSize) {

        PageResponse<List<SpecificationAttributeGroupResponse>> response = specificationAttributeGroupService
                .getAllSpecificationAttributeGroup(name, pageNo, pageSize);

        return ResponseData.<PageResponse<List<SpecificationAttributeGroupResponse>>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận thành công của nhóm thuộc tính đặc tả")
                .data(response)
                .build();
    }

    @Operation(method = "GET", summary = "Nhận nhóm thuộc tính đặc tả theo ID",
            description = "Gửi yêu cầu qua API này để lấy nhóm thuộc tính đặc tả theo ID")
    @GetMapping("/{id}")
    public ResponseData<SpecificationAttributeGroupResponse> getSpecificationAttributeGroupById(@PathVariable Long id) {

        SpecificationAttributeGroupResponse response = specificationAttributeGroupService
                .getSpecificationAttributeGroupById(id);

        return ResponseData.<SpecificationAttributeGroupResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận thành công của nhóm thuộc tính đặc tả")
                .data(response)
                .build();
    }

    @Operation(method = "PUT", summary = "Cập nhật nhóm thuộc tính đặc tả",
            description = "Gửi yêu cầu qua API này để cập nhật nhóm thuộc tính đặc tả hiện có")
    @PutMapping("/{id}")
    public ResponseData<SpecificationAttributeGroupResponse> updateSpecificationAttributeGroup(
            @PathVariable Long id,
            @Valid @RequestBody SpecificationAttributeGroupRequest request
    ) {

        SpecificationAttributeGroupResponse response = specificationAttributeGroupService
                .updateSpecificationAttributeGroup(id, request);

        return ResponseData.<SpecificationAttributeGroupResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Thuộc tính thông số kỹ thuật được cập nhật thành công")
                .data(response)
                .build();
    }

    @Operation(method = "DELETE", summary = "Xóa các nhóm thuộc tính đặc tả",
            description = "Gửi yêu cầu qua API này để xóa các nhóm thuộc tính đặc tả")
    @DeleteMapping
    public ResponseData<Void> deleteSpecificationAttributeGroups(@RequestBody List<Long> ids) {

        specificationAttributeGroupService.deleteSpecificationAttributeGroup(ids);

        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Xóa thành công các nhóm thuộc tính đặc tả")
                .build();
    }

    @Operation(method = "DELETE", summary = "Xóa nhóm thuộc tính đặc tả theo ID",
            description = "Gửi yêu cầu qua API này để xóa nhóm thuộc tính đặc tả theo ID")
    @DeleteMapping("/{id}")
    public ResponseData<Void> deleteSpecificationAttributeGroupById(@PathVariable Long id) {

        specificationAttributeGroupService.deleteSpecificationAttributeGroupById(id);

        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Xóa thành công nhóm thuộc tính đặc tả")
                .build();
    }

    @GetMapping("/list-name")
    public ResponseData<List<SpecificationAttributeGroupNameResponse>> getAllGroupName() {

        List<SpecificationAttributeGroupNameResponse> response = specificationAttributeGroupService.getAllGroupName();

        return ResponseData.<List<SpecificationAttributeGroupNameResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận tên tất cả các nhóm thành công")
                .data(response)
                .build();
    }

    @Operation(method = "GET", summary = "Nhận tất cả các nhóm thuộc tính đặc tả mà không cần phân trang",
            description = "Gửi yêu cầu qua API này để lấy tất cả các nhóm thuộc tính đặc tả mà không cần phân trang")
    @GetMapping("/all")
    public ResponseData<List<SpecificationAttributeGroupResponse>> getAllSpecificationAttributeGroups() {

        List<SpecificationAttributeGroupResponse> response = specificationAttributeGroupService
                .getAllSpecificationAttributeGroups();

        return ResponseData.<List<SpecificationAttributeGroupResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Nhận thành công tất cả các nhóm thuộc tính đặc tả")
                .data(response)
                .build();
    }

}
