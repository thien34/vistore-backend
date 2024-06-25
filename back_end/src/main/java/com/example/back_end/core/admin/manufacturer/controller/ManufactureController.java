package com.example.back_end.core.admin.manufacturer.controller;

import com.example.back_end.core.admin.manufacturer.payload.request.ManufacturerRequest;
import com.example.back_end.core.admin.manufacturer.payload.response.ManufacturerResponse;
import com.example.back_end.core.admin.manufacturer.services.impl.ManufacturerServicesImpl;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.core.common.ResponseData;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/manufacturers")
public class ManufactureController {
    @Autowired
    private ManufacturerServicesImpl manufacturerServices;

    @GetMapping("list")
    public ResponseData<?> getAll(@RequestParam(value = "name", defaultValue = "") String name,
                                  @RequestParam(value = "published", defaultValue = "") Boolean published,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            PageResponse<?> response = manufacturerServices.getAll(name, published, page, size);

            return ResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("Get List Manufacturers successfully")
                    .data(response)
                    .build();
        } catch (Exception e) {
            return ResponseData.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
        }
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseData<?> add(
            @ModelAttribute @Valid ManufacturerRequest manufacturerRequest,
            BindingResult bindingResult,
            @RequestParam("picture") MultipartFile picture) {
        if (bindingResult.hasErrors()) {
            return ResponseData.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Validation errors")
                    .data(bindingResult.getAllErrors())
                    .build();
        }

        try {
            ManufacturerResponse manufacturer = manufacturerServices.addManufacturer(picture, manufacturerRequest);
            return ResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("Add Manufacturer successfully")
                    .data(manufacturer)
                    .build();
        } catch (Exception e) {
            return ResponseData.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message(e.getMessage())
                    .build();
        }
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseData<?> update(
            @RequestBody @Valid ManufacturerRequest manufacturer,
            BindingResult bindingResult,
            @RequestParam(value = "picture") MultipartFile file) {

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            return ResponseData.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Validation errors")
                    .data(bindingResult.getAllErrors())
                    .build();
        }

        try {
            // Call service to update manufacturer with the provided file
            ManufacturerResponse manufacturerResponse = manufacturerServices.updateManufacturer(file, manufacturer);

            // Return success response
            return ResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("Update Manufacturer successfully")
                    .data(manufacturerResponse)
                    .build();
        } catch (Exception e) {
            // Handle and return internal server error
            return ResponseData.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/get/{id}")
    public ResponseData<?> getManufacture(@PathVariable(name = "id") Long id) {
        try {
            if (id == null || id <= 0)
                throw new IllegalArgumentException("Can't get Manufacturer because of invalid id");
            ManufacturerResponse manufacturer = manufacturerServices.getManufacturer(id);
            return ResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("GET Manufacturer has id   " + id + " successfully")
                    .data(manufacturer)
                    .build();
        } catch (Exception e) {
            return ResponseData.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseData<?> delete(@PathVariable(name = "id") Long id) {
        try {
            manufacturerServices.deleteManufacturer(id);
            return ResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("Manufacturer with id " + id + " has been deleted successfully")
                    .build();
        } catch (EntityNotFoundException ex) {
            return ResponseData.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Manufacturer with id " + id + " not found")
                    .build();
        } catch (Exception e) {
            return ResponseData.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Failed to delete manufacturer with id " + id + ": " + e.getMessage())
                    .build();
        }
    }
}
