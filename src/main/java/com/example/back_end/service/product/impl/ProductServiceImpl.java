package com.example.back_end.service.product.impl;

import com.example.back_end.core.admin.product.payload.request.ProductRequest;
import com.example.back_end.core.admin.product.payload.request.ProductRequestUpdate;
import com.example.back_end.core.admin.product.payload.response.ProductResponse;
import com.example.back_end.entity.Category;
import com.example.back_end.entity.Discount;
import com.example.back_end.entity.DiscountAppliedToProduct;
import com.example.back_end.entity.Manufacturer;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductAttribute;
import com.example.back_end.entity.ProductAttributeValue;
import com.example.back_end.infrastructure.cloudinary.CloudinaryUpload;
import com.example.back_end.infrastructure.constant.CloudinaryTypeFolder;
import com.example.back_end.repository.DiscountAppliedToProductRepository;
import com.example.back_end.repository.ProductAttributeRepository;
import com.example.back_end.repository.ProductAttributeValueRepository;
import com.example.back_end.repository.ProductRepository;
import com.example.back_end.service.product.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final DiscountAppliedToProductRepository discountAppliedToProductRepository;

    private final ProductRepository productRepository;
    private final ProductAttributeValueRepository productAttributeValueRepository;
    private final ProductAttributeRepository productAttributeRepository;
    private final CloudinaryUpload cloudinaryUpload;

    @Transactional
    public void createProduct(List<ProductRequest> requests, MultipartFile[] images) {
        associateImagesWithRequests(requests, images);

        if (requests.isEmpty()) {
            return;
        }

        Product parentProduct = createParentProduct(requests.get(0));
        List<Product> products = new ArrayList<>();
        List<ProductAttributeValue> attributeValues = new ArrayList<>();

        for (ProductRequest request : requests) {
            Product product = mapRequestToProduct(request, parentProduct);
            String imageUrl = uploadImage(request).orElse("");
            product.setImage(imageUrl);
            String sku = generateSku(request.getName(), request.getCategoryId(), request.getManufacturerId(), product.getId(), request.getAttributes());
            product.setSku(sku);

            String gtin;
            do {
                gtin = String.format("%013d", new Random().nextLong() % 1_000_000_000_0000L);
                product.setGtin(gtin);
            } while (checkIfGtinExists(gtin));
            products.add(product);

            attributeValues.addAll(mapAttributesToValues(product, request.getAttributes(), ""));
        }

        saveProductsAndAttributes(products, attributeValues);

    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .filter(x -> x.getParentProductId() == null)
                .map(this::mapProductToProductResponse)
                .toList();
    }

    private ProductResponse mapProductToProductResponse(Product product) {
        ProductResponse response = ProductResponse.fromProduct(product);
        response.setLargestDiscountPercentage(calculateLargestDiscountPercentage(product));
        return response;
    }

    @Override
    public List<ProductResponse> getAllProductsByParentIds(List<Long> parentIds) {
        List<Product> products = productRepository.findByParentProductIds(parentIds);

        return products.stream()
                .map(product -> {
                    try {
                        ProductResponse response = ProductResponse.fromProductParentId(product, List.of());

                        BigDecimal largestDiscount = calculateLargestDiscountPercentage(product);
                        response.setLargestDiscountPercentage(largestDiscount);

                        return response;
                    } catch (Exception e) {
                        log.error("Error processing product with ID {}: {}", product.getId(), e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private BigDecimal calculateLargestDiscountPercentage(Product product) {
        List<DiscountAppliedToProduct> discountsApplied = discountAppliedToProductRepository.findByProduct(product);

        return discountsApplied.stream()
                .filter(da -> {
                    Discount discount = da.getDiscount();
                    return discount != null
                            && discount.getStatus() != null
                            && discount.getStatus().equalsIgnoreCase("ACTIVE");
                })
                .map(discountApplied -> discountApplied.getDiscount().getDiscountPercentage())
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }



    @Override
    public ProductResponse getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            return ProductResponse.fromProduct(optionalProduct.get());
        } else {
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
    }

    @Override
    public List<ProductResponse> getAllProductByParentId(Long parentId) {
        List<Product> products = productRepository.findAll().stream()
                .filter(product -> product.getParentProductId() != null && product.getParentProductId().equals(parentId))
                .toList();
        return products.stream().map(product -> ProductResponse.fromProductParentId(product, List.of())).toList();
    }

    @Override
    public ProductResponse getProductDetail(Long id) {
        Product product = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        List<ProductResponse.ProductAttribute> attributeResponses = getProductAttributes(product);

        return ProductResponse.fromProductFull(product, attributeResponses);
    }

    @Override
    @Transactional
    public void updateProduct(ProductRequestUpdate request, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);

        if (!product.getSku().equals(request.getSku())
                && !request.getSku().isEmpty() && productRepository.existsBySku(request.getSku()))
            throw new IllegalArgumentException("SKU already exists.");

        productAttributeValueRepository.deleteByProduct(product);

        List<ProductAttributeValue> newAttributeValues = new ArrayList<>();

        for (ProductRequestUpdate.ProductAttribute attribute : request.getAttributes()) {
            ProductAttribute productAttribute = productAttributeRepository.findById(attribute.getAttributeId())
                    .orElseThrow(EntityNotFoundException::new);

            ProductAttributeValue newAttributeValue = new ProductAttributeValue(product, productAttribute, attribute.getValue());
            newAttributeValues.add(newAttributeValue);
        }

        productAttributeValueRepository.saveAll(newAttributeValues);

        String attributeValues = request.getAttributes().stream()
                .map(ProductRequestUpdate.ProductAttribute::getValue)
                .distinct()
                .collect(Collectors.joining("-"));

        String fullName = product.getName() + (attributeValues.isEmpty() ? "" : "-" + attributeValues);
        product.setFullName(fullName);

        request.toEntity(product);
        productRepository.save(product);
    }

    @Override
    public List<ProductResponse> getAllProductDetails() {
        List<Product> products = productRepository.findAll();

        return products
                .stream()
                .filter(product -> product.getParentProductId() != null)
                .map(ProductResponse::new)
                .toList();
    }

    private List<ProductResponse.ProductAttribute> getProductAttributes(Product product) {
        Map<ProductAttribute, List<ProductAttributeValue>> attributeMap = product.getProductAttributeValues().stream()
                .collect(Collectors.groupingBy(ProductAttributeValue::getProductAttribute));

        return attributeMap.entrySet().stream().map(entry -> {
            ProductAttribute attribute = entry.getKey();
            List<ProductAttributeValue> attributeValues = entry.getValue();
            String value = attributeValues.isEmpty() ? null : attributeValues.get(attributeValues.size() - 1).getValue();
            return new ProductResponse.ProductAttribute(attribute.getId(), attribute.getName(), value);
        }).toList();
    }

    private void associateImagesWithRequests(List<ProductRequest> requests, MultipartFile[] images) {
        if (images != null) {
            IntStream.range(0, Math.min(requests.size(), images.length))
                    .forEach(i -> requests.get(i).setImage(images[i]));
        }
    }

    private Product createParentProduct(ProductRequest request) {
        Product parentProduct = new Product();
        parentProduct.setName(request.getName());
        parentProduct.setPublished(true);
        parentProduct.setDeleted(false);
        parentProduct.setWeight(request.getWeight());
        parentProduct.setCategory(new Category(request.getCategoryId()));
        parentProduct.setManufacturer(new Manufacturer(request.getManufacturerId()));
        parentProduct.setFullDescription(request.getFullDescription());
        return productRepository.save(parentProduct);
    }

    private Product mapRequestToProduct(ProductRequest request, Product parentProduct) {
        Product product = new Product();
        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setGtin(request.getGtin());
        product.setFullDescription(request.getFullDescription());
        product.setCategory(new Category(request.getCategoryId()));
        product.setManufacturer(new Manufacturer(request.getManufacturerId()));
        product.setQuantity(request.getQuantity());
        product.setUnitPrice(request.getUnitPrice());
        product.setProductCost(request.getProductCost());
        product.setWeight(request.getWeight());
        product.setPublished(request.getPublished());
        product.setDeleted(request.getDeleted());
        product.setParentProductId(parentProduct.getId());
        product.setFullName(generateFullName(request.getName(), request.getAttributes()));
        return product;
    }

    private List<ProductAttributeValue> mapAttributesToValues(Product product, List<ProductRequest.ProductAttribute> attributes, String imageUrl) {
        if (attributes == null || attributes.isEmpty()) {
            return new ArrayList<>();
        }

        return attributes.stream()
                .map(attribute -> createAttributeValue(product, attribute, imageUrl))
                .toList();
    }

    private ProductAttributeValue createAttributeValue(Product product, ProductRequest.ProductAttribute attribute, String imageUrl) {
        ProductAttributeValue productAttribute = new ProductAttributeValue();
        productAttribute.setProduct(product);
        productAttribute.setValue(attribute.getValue());
        productAttribute.setProductAttribute(new ProductAttribute(attribute.getId()));
        productAttribute.setImageUrl(imageUrl);
        return productAttribute;
    }

    private Optional<String> uploadImage(ProductRequest request) {
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            try {
                return Optional.of(cloudinaryUpload.uploadFile(request.getImage(), CloudinaryTypeFolder.PRODUCTS));
            } catch (Exception e) {
                log.error("Error uploading image: {}", e.getMessage(), e);
            }
        }
        return Optional.empty();
    }

    private String generateFullName(String productName, List<ProductRequest.ProductAttribute> attributes) {
        String attributeValues = attributes.stream()
                .map(ProductRequest.ProductAttribute::getValue)
                .collect(Collectors.joining(" - "));
        return productName + (attributeValues.isEmpty() ? "" : " - " + attributeValues);
    }


    private void saveProductsAndAttributes(List<Product> products, List<ProductAttributeValue> attributeValues) {
        productRepository.saveAll(products);
        productAttributeValueRepository.saveAll(attributeValues);
    }

    private void checkDuplicateAttributeValues(Product product, List<ProductRequest.ProductAttribute> attributes) {
        for (ProductRequest.ProductAttribute attribute : attributes) {
            boolean exists = productAttributeValueRepository.existsByProductAndProductAttributeAndValue(
                    product, new ProductAttribute(attribute.getId()), attribute.getValue());

            if (exists)
                throw new IllegalArgumentException("Giá trị " + attribute.getValue() + " đã tồn tại cho sản phẩm " + product.getName());

        }
    }

    private boolean checkIfGtinExists(String gtin) {
        return productRepository.existsByGtin(gtin);
    }
//    private List<ProductResponse.ProductAttributeValueResponse> getProductAttributeValues(Product product) {
//        List<ProductAttributeValue> attributeValues = productAttributeValueRepository.findAll();
//        return attributeValues.stream()
//                .filter(attributeValue -> attributeValue.getProduct().getId().equals(product.getId()))
//                .map(attributeValue -> {
//                    ProductResponse.ProductAttributeValueResponse attributeResponse =
//                            new ProductResponse.ProductAttributeValueResponse();
//                    attributeResponse.setId(attributeValue.getId());
//                    attributeResponse.setValue(attributeValue.getValue());
//                    attributeResponse.setImageUrl(attributeValue.getImageUrl());
//                    return attributeResponse;
//                }).toList();
//    }

    private String generateSku(String productName, Long categoryId, Long manufacturerId, Long productId, List<ProductRequest.ProductAttribute> attributes) {
        String[] words = productName.split(" ");
        StringBuilder productCodeBuilder = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) productCodeBuilder.append(word.charAt(0));
        }
        List<String> skuParts = new ArrayList<>();
        skuParts.add(productCodeBuilder.toString().toUpperCase());
        if (categoryId != null) skuParts.add(String.valueOf(categoryId));
        if (manufacturerId != null) skuParts.add(String.valueOf(manufacturerId));
        if (productId != null) skuParts.add(String.valueOf(productId));
        for (ProductRequest.ProductAttribute attribute : attributes) {
            if (attribute.getValue() != null) skuParts.add(attribute.getValue());
        }
        return String.join("-", skuParts);
    }

}
