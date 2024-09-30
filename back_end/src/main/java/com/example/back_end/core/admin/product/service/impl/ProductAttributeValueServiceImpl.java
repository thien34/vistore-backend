package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.product.mapper.ProductAttributeValueMapper;
import com.example.back_end.core.admin.product.payload.request.ProductAttributeValuePictureRequest;
import com.example.back_end.core.admin.product.payload.request.ProductAttributeValueRequest;
import com.example.back_end.core.admin.product.service.ProductAttributeValuePictureService;
import com.example.back_end.core.admin.product.service.ProductAttributeValueService;
import com.example.back_end.entity.ProductAttribute;
import com.example.back_end.entity.ProductAttributeCombination;
import com.example.back_end.entity.ProductAttributeValue;
import com.example.back_end.entity.ProductProductAttributeMapping;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import com.example.back_end.repository.ProductAttributeCombinationRepository;
import com.example.back_end.repository.ProductAttributeRepository;
import com.example.back_end.repository.ProductAttributeValueRepository;
import com.example.back_end.repository.ProductProductAttributeMappingRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ProductAttributeValueServiceImpl implements ProductAttributeValueService {

    private final ProductAttributeValueRepository productAttributeValueRepository;
    private final ProductAttributeValueMapper productAttributeValueMapper;
    private final ProductProductAttributeMappingRepository productProductAttributeMappingRepository;
    private final ProductAttributeValuePictureService productAttributeValuePictureService;
    private final ProductAttributeCombinationRepository productAttributeCombinationRepository;
    private final ProductAttributeRepository productAttributeRepository;

    @Override
    @Transactional
    public void createProductAttributeValues(List<ProductAttributeValueRequest> request, Long productAttributeMappingId) {

        ProductProductAttributeMapping productProductAttributeMapping = productProductAttributeMappingRepository
                .findById(productAttributeMappingId)
                .orElseThrow(() -> new ResourceNotFoundException("Product attribute mapping not found: "
                        + productAttributeMappingId));

        List<ProductAttributeValue> productAttributeValues = request.stream()
                .map(productAttributeValueRequest -> {
                    ProductAttributeValue productAttributeValue = productAttributeValueMapper
                            .toEntity(productAttributeValueRequest);
                    productAttributeValue.setProductAttributeMapping(productProductAttributeMapping);
                    return productAttributeValue;
                })
                .toList();

        List<ProductAttributeValue> savedAttributeValues = productAttributeValueRepository.saveAll(productAttributeValues);

        Map<Long, List<ProductAttributeValuePictureRequest>> picturesMap = IntStream.range(0, savedAttributeValues.size())
                .boxed()
                .collect(Collectors.toMap(
                        index -> savedAttributeValues.get(index).getId(),
                        index -> request.get(index).getProductAttributeValuePictureRequests()
                ));



        productAttributeValuePictureService.createProductAttributePictureValue(picturesMap);
    }

    private String updateAttributeValueInJson(String currentXml, String attributeName, String newValue) {

        JsonObject jsonObject = JsonParser.parseString(currentXml).getAsJsonObject();
        JsonArray attributes = jsonObject.getAsJsonArray("attributes");

        String lowerCaseAttributeName = attributeName.toLowerCase();

        for (JsonElement element : attributes) {
            if (element.isJsonObject()) {
                JsonObject attribute = element.getAsJsonObject();

                for (String key : attribute.keySet()) {
                    if (key.toLowerCase().equals(lowerCaseAttributeName)) {
                        attribute.addProperty(key, newValue);
                        break;
                    }
                }
            }
        }

        return jsonObject.toString();
    }

    private boolean containsAttribute(JsonArray attributes, String attributeName) {
        String lowerCaseAttributeName = attributeName.toLowerCase();
        for (JsonElement element : attributes) {
            if (element.isJsonObject()) {
                JsonObject attribute = element.getAsJsonObject();

                for (String key : attribute.keySet()) {
                    if (key.toLowerCase().equals(lowerCaseAttributeName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void createProductAttributeValue(ProductAttributeValueRequest request) {

        ProductProductAttributeMapping productProductAttributeMapping = productProductAttributeMappingRepository
                .findById(request.getProductAttributeMappingId())
                .orElseThrow(() -> new ResourceNotFoundException("Product attribute mapping not found: "
                        + request.getProductAttributeMappingId()));

        ProductAttributeValue productAttributeValue = productAttributeValueMapper.toEntity(request);
        productAttributeValue.setProductAttributeMapping(productProductAttributeMapping);

        ProductAttributeValue savedAttributeValue = productAttributeValueRepository.save(productAttributeValue);

        if (request.getProductAttributeValuePictureRequests() != null) {
            Map<Long, List<ProductAttributeValuePictureRequest>> picturesMap = Map.of(savedAttributeValue.getId(),
                    request.getProductAttributeValuePictureRequests());
            productAttributeValuePictureService.createProductAttributePictureValue(picturesMap);
        }
    }

    @Override
    public void updateProductAttributeValue(Long id, ProductAttributeValueRequest request) {

        ProductAttributeValue productAttributeValue = productAttributeValueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product attribute value not found: " + id));

        productAttributeValueMapper.updateProdAttrValueFromRequest(request, productAttributeValue);

        productAttributeValueRepository.save(productAttributeValue);

        List<ProductAttributeCombination> productAttributeCombination = productAttributeCombinationRepository
                .findByProduct(productAttributeValue.getProductAttributeMapping().getProduct());

        for(ProductAttributeCombination x : productAttributeCombination) {

            String attributesXml = x.getAttributesXml();

            Optional<ProductAttribute> productAttribute = productAttributeRepository.findById(productAttributeValue.getProductAttributeMapping().getProductAttribute().getId());

            String attributeName = productAttribute.get().getName();
            String newValue = request.getName();
            JsonObject jsonObject = JsonParser.parseString(attributesXml).getAsJsonObject();
            JsonArray attributes = jsonObject.getAsJsonArray("attributes");
            if (containsAttribute(attributes, attributeName)) {
                attributesXml = updateAttributeValueInJson(attributesXml, attributeName, newValue);
            }

            x.setAttributesXml(attributesXml);
            productAttributeCombinationRepository.save(x);

        }

        if (request.getProductAttributeValuePictureRequests() != null) {
            Map<Long, List<ProductAttributeValuePictureRequest>> picturesMap = Map.of(productAttributeValue.getId(),
                    request.getProductAttributeValuePictureRequests());
            productAttributeValuePictureService.createProductAttributePictureValue(picturesMap);
        }
    }
}
