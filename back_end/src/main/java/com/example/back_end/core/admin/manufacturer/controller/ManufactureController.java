package com.example.back_end.core.admin.manufacturer.controller;

import com.example.back_end.core.admin.manufacturer.payload.request.ManufacturerRequest;
import com.example.back_end.core.admin.manufacturer.payload.response.ManufacturerNameResponse;
import com.example.back_end.core.admin.manufacturer.payload.response.ManufacturerResponse;
import com.example.back_end.core.admin.manufacturer.service.ManufactureServices;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.core.common.ResponseError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/admin/manufacturers")
@Slf4j
@RequiredArgsConstructor
public class ManufactureController {
    private final ManufactureServices manufacturerServices;

    @GetMapping()
    public ResponseData<?> getAllManufacturers(@RequestParam(value = "name", defaultValue = "") String name,
                                               @RequestParam(value = "published", defaultValue = "") Boolean published,
                                               @RequestParam(value = "page", defaultValue = "1") Integer page,
                                               @RequestParam(value = "size", defaultValue = "6") Integer size) {
        try {
            PageResponse<?> response = manufacturerServices.getAll(name, published, page, size);
            return new ResponseData<>(HttpStatus.OK.value(), "Get Manufacturers successfully", response);
        } catch (Exception e) {
            log.error("Error getting Manufacturers", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PostMapping
    public ResponseData<?> createManufacturer(@RequestBody ManufacturerRequest manufacturerRequest) {
        log.info("Request add new Manufacturer, {}", manufacturerRequest);
        try {
            manufacturerServices.createManufacturer(manufacturerRequest);
            return new ResponseData<>(HttpStatus.OK.value(), "Add new Manufacturer successfully");
        } catch (Exception e) {
            log.error("Error add new Manufacturer", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseData<?> update(@PathVariable Long id, @RequestBody ManufacturerRequest manufacturerRequest) {
        log.info("Request to update the manufacturer with id: {}, {}", id, manufacturerRequest);
        try {
            manufacturerServices.updateManufacturer(id, manufacturerRequest);
            return new ResponseData<>(HttpStatus.OK.value(), "Update manufacturer with id : " + id + " successfully");
        } catch (Exception e) {
            log.error("Error updating manufacturer ", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @GetMapping("/listname")
    public ResponseData<?> getAllManufacturersName() {
        try {
            List<ManufacturerNameResponse> manufacturerNameResponses = manufacturerServices.getAlManufacturersName();
            return new ResponseData<>(HttpStatus.OK.value(), "Get all manufacturers name successfully", manufacturerNameResponses);
        } catch (Exception e) {
            log.error("Error getting all manufacturers name", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @GetMapping("/{manufacturerId}")
    public ResponseData<?> getManufacturerById(@PathVariable Long manufacturerId) {
        try {
            ManufacturerResponse manufacturerResponse = manufacturerServices.getManufacturer(manufacturerId);
            return new ResponseData<>(HttpStatus.OK.value(), "Get Manufacturer successfully", manufacturerResponse);
        } catch (Exception e) {
            log.error("Error getting manufacturer", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseData<?> deleteManufacturers(@RequestBody List<Long> ids) {
        log.info(" Waiting to delete categories with ids: {}", ids);
        try {
            manufacturerServices.deleteListManufacturer(ids);
            return new ResponseData<>(HttpStatus.OK.value(), "Delete Manufacturers  successfully");
        } catch (Exception e) {
            log.error("Error when deleting Manufacturer", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
}
