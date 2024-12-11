package com.example.back_end.service.product.impl;

import com.example.back_end.core.admin.order.payload.ReStockQuanityProductRequest;
import com.example.back_end.core.admin.product.payload.request.ProductFilter;
import com.example.back_end.core.admin.product.payload.request.ProductParentRequest;
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
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.infrastructure.utils.CollectionUtil;
import com.example.back_end.infrastructure.utils.StringUtils;
import com.example.back_end.repository.DiscountAppliedToProductRepository;
import com.example.back_end.repository.DiscountRepository;
import com.example.back_end.repository.ProductAttributeRepository;
import com.example.back_end.repository.ProductAttributeValueRepository;
import com.example.back_end.repository.ProductRepository;
import com.example.back_end.service.discount.impl.VoucherServiceImpl;
import com.example.back_end.service.product.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
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
    private final DiscountRepository discountRepository;

    public static void discountStatus(Discount discount, DiscountRepository discountRepository) {
        VoucherServiceImpl.updateStatusVoucher(discount, discountRepository);
    }

    @Transactional
    @Override
    public void createProduct(List<ProductRequest> requests, MultipartFile[] images) {
        if (requests.isEmpty()) {
            return;
        }
        Product parentProduct = createParentProduct(requests.getFirst());
        final AtomicInteger imageIndex = new AtomicInteger(0);

        CollectionUtil.split(requests, 50, batch -> {
            int startIndex = imageIndex.get();
            int endIndex = Math.min(startIndex + batch.size(), images != null ? images.length : 0);
            List<MultipartFile> batchImages = images != null
                    ? Arrays.asList(images).subList(startIndex, endIndex)
                    : Collections.emptyList();

            imageIndex.addAndGet(batch.size());

            associateImagesWithRequests(batch, batchImages.toArray(new MultipartFile[0]));

            List<Product> products = new ArrayList<>();
            List<ProductAttributeValue> attributeValues = new ArrayList<>();

            for (ProductRequest request : batch) {
                Product product = mapRequestToProduct(request, parentProduct);
                String imageUrl = uploadImage(request).orElse("");
                product.setImage(imageUrl);
                String sku = generateSku(
                        request.getName(),
                        request.getCategoryId(),
                        request.getManufacturerId(),
                        product.getId(),
                        request.getAttributes());

                while (checkIfSkuExists(sku)) {
                    sku = appendRandomSuffixToSku(sku);
                }
                product.setSku(sku);

                String gtin;
                do {
                    gtin = String.format("%013d", new Random().nextLong() % 1_000_000_000_0000L);
                    product.setGtin(gtin);
                } while (checkIfGtinExists(gtin));
                products.add(product);

                attributeValues.addAll(mapAttributesToValues(product, request.getAttributes()));
            }

            saveProductsAndAttributes(products, attributeValues);
        });
    }

    @Override
    public List<ProductResponse> getAllProducts(ProductFilter filter) {
        List<Product> products = productRepository.findAll(ProductSpecification.filterBy(filter));
        return products.stream()
                .filter(x -> x.getParentProductId() == null)
                .map(this::mapProductToProductResponse)
                .toList();
    }

    private ProductResponse mapProductToProductResponse(Product product) {
        ProductResponse response = ProductResponse.fromProduct(product);
        List<String> productImages = productRepository.findImagesByProductId(product.getId());
        if(!productImages.isEmpty()){
        String image = productImages.stream()
                .filter(img ->img != null && !img.isEmpty())
                .findFirst().orElse("");
        response.setImageUrl(image);}
        response.setQuantity(Math.toIntExact(productRepository.findTotalQuantityByParentProductId(product.getId())));
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
                        if (largestDiscount.compareTo(BigDecimal.ZERO) > 0) {
                            BigDecimal discountPrice = product.getUnitPrice()
                                    .multiply(BigDecimal.ONE.subtract(largestDiscount.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)));
                            response.setDiscountPrice(discountPrice);
                        } else {
                            response.setDiscountPrice(product.getUnitPrice());
                        }
                        return response;
                    } catch (Exception e) {
                        log.error("Lỗi xử lý sản phẩm có ID {}: {}", product.getId(), e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    @Transactional
    public void updateParentProduct(ProductParentRequest request, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm"));

        request.toEntity(request, product);
        product = productRepository.save(product);

        Product finalProduct = product;
        List<Product> products = productRepository.findByParentProductId(productId)
                .stream().peek(productUpdate -> {
                    List<ProductRequest.ProductAttribute> attributes = productUpdate
                            .getProductAttributeValues().stream().map(productAttributeValue -> {
                                ProductRequest.ProductAttribute productAttribute = new ProductRequest.ProductAttribute();
                                productAttribute.setId(productAttributeValue.getId());
                                productAttribute.setValue(productAttributeValue.getValue());
                                productAttribute.setProductId(productAttributeValue.getProduct().getId());
                                return productAttribute;
                            }).toList();
                    productUpdate.setFullName(generateFullName(request.getName(), attributes));
                    productUpdate.setCategory(finalProduct.getCategory());
                    productUpdate.setManufacturer(finalProduct.getManufacturer());
                }).toList();

        productRepository.saveAll(products);

    }

    @Override
    @Transactional
    public void addChildProduct(ProductRequestUpdate request, Long productId) {
        Product productParent = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);

        List<Product> products = productRepository.findByParentProductId(productParent.getId());

        checkDuplicateAtb(request, products);

        Product product = new Product();
        product.setName(request.getName());

        if (!request.getSku().isEmpty() && productRepository.existsBySku(request.getSku()))
            throw new IllegalArgumentException("SKU đã tồn tại.");

        List<ProductAttributeValue> newAttributeValues = new ArrayList<>();

        for (ProductRequestUpdate.ProductAttribute attribute : request.getAttributes()) {
            ProductAttribute productAttribute = productAttributeRepository.findById(attribute.getAttributeId())
                    .orElseThrow(EntityNotFoundException::new);

            ProductAttributeValue newAttributeValue = ProductAttributeValue.builder()
                    .product(product)
                    .productAttribute(productAttribute)
                    .value(attribute.getValue())
                    .build();
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
        product.setParentProductId(productParent.getId());
        product.setGtin(UUID.randomUUID().toString());
        product.setCategory(productParent.getCategory());
        product.setManufacturer(productParent.getManufacturer());
        productRepository.save(product);
    }


    @Override
    public void reStockQuantityProduct(List<ReStockQuanityProductRequest> requests) {
        if (requests.isEmpty()) {
            throw new IllegalArgumentException("Danh sách Restock trống!");
        }
        List<Product> listProduct = new ArrayList<>();
        requests.forEach(
                request -> {
                    Optional<Product> product = productRepository.findById(request.getProductId());
                    if (product.isPresent()) {
                        Product productRequest = product.get();
                        Integer newQuantity = productRequest.getQuantity() + request.getQuantity();
                        productRequest.setQuantity(newQuantity);
                        listProduct.add(productRequest);
                    }
                });
        if (listProduct.isEmpty()) {
            throw new IllegalArgumentException("Danh sách Sản phẩm trống!");
        } else productRepository.saveAll(listProduct);
    }


    private void updateDiscountStatus(Discount discount) {
        discountStatus(discount, discountRepository);
    }

    private BigDecimal calculateLargestDiscountPercentage(Product product) {
        List<DiscountAppliedToProduct> discountsApplied = discountAppliedToProductRepository.findByProduct(product);
        discountsApplied.stream()
                .map(da -> {
                    Discount discount = da.getDiscount();
                    updateDiscountStatus(discount);
                    return discount;
                }).forEach(discountRepository::save);
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
            throw new EntityNotFoundException("Sản phẩm không được tìm thấy với id: " + id);
        }
    }

    @Override
    public List<ProductResponse> getAllProductByParentId(Long parentId) {
        List<Product> products = productRepository.findAll().stream()
                .filter(product -> product.getParentProductId() != null
                        && product.getParentProductId().equals(parentId))
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
        List<Product> products = productRepository.findByParentProductId(product.getParentProductId());

        checkDuplicateAtb(request, products);

        if (!product.getSku().equals(request.getSku())
                && !request.getSku().isEmpty() && productRepository.existsBySku(request.getSku()))
            throw new IllegalArgumentException("SKU đã tồn tại.");

        productAttributeValueRepository.deleteByProduct(product);

        List<ProductAttributeValue> newAttributeValues = new ArrayList<>();

        for (ProductRequestUpdate.ProductAttribute attribute : request.getAttributes()) {
            ProductAttribute productAttribute = productAttributeRepository.findById(attribute.getAttributeId())
                    .orElseThrow(EntityNotFoundException::new);

            ProductAttributeValue newAttributeValue = ProductAttributeValue.builder()
                    .product(product)
                    .productAttribute(productAttribute)
                    .value(attribute.getValue())
                    .build();
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

    private void checkDuplicateAtb(ProductRequestUpdate request, List<Product> products) {
        boolean isDuplicate = products.stream().anyMatch(productCheck -> {
            if (productCheck.getProductAttributeValues().size() != request.getAttributes().size()) {
                return false;
            }
            return productCheck.getProductAttributeValues().stream().allMatch(attributeValue ->
                    request.getAttributes().stream().anyMatch(requestAttribute ->
                            requestAttribute.getAttributeId().equals(attributeValue.getProductAttribute().getId())
                                    && requestAttribute.getValue().equals(attributeValue.getValue())
                    )
            );
        });
        if (isDuplicate) {
            throw new IllegalArgumentException("Biến thể sản phẩm đã tồn tại.");
        }
    }

    @Override
    public List<ProductResponse> getAllProductDetails() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .sorted(Comparator.comparing(Product::getCreatedDate).reversed())
                .filter(product -> product.getParentProductId() != null)
                .map(product -> {
                    ProductResponse response = ProductResponse.fromProductFull(product, List.of());
                    BigDecimal largestDiscount = calculateLargestDiscountPercentage(product);
                    response.setLargestDiscountPercentage(largestDiscount);
                    return response;
                })
                .collect(Collectors.toList());
    }

    private List<ProductResponse.ProductAttribute> getProductAttributes(Product product) {
        Map<ProductAttribute, List<ProductAttributeValue>> attributeMap = product.getProductAttributeValues().stream()
                .collect(Collectors.groupingBy(ProductAttributeValue::getProductAttribute));

        return attributeMap.entrySet().stream().map(entry -> {
            ProductAttribute attribute = entry.getKey();
            List<ProductAttributeValue> attributeValues = entry.getValue();
            String value = attributeValues.isEmpty() ? null : attributeValues.getLast().getValue();
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
        parentProduct.setSlug(StringUtils.generateSlug(request.getName()));
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

    private List<ProductAttributeValue> mapAttributesToValues(Product product,
                                                              List<ProductRequest.ProductAttribute> attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return new ArrayList<>();
        }

        return attributes.stream()
                .map(attribute -> createAttributeValue(product, attribute))
                .toList();
    }

    private ProductAttributeValue createAttributeValue(Product product, ProductRequest.ProductAttribute attribute) {
        ProductAttributeValue productAttribute = new ProductAttributeValue();
        productAttribute.setProduct(product);
        productAttribute.setParentProductId(product.getParentProductId());
        productAttribute.setValue(attribute.getValue());
        productAttribute.setProductAttribute(new ProductAttribute(attribute.getId()));
        return productAttribute;
    }

    private Optional<String> uploadImage(ProductRequest request) {
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            try {
                return Optional.of(cloudinaryUpload.uploadFile(request.getImage(), CloudinaryTypeFolder.PRODUCTS));
            } catch (Exception e) {
                log.error("Lỗi tải lên hình ảnh: {}", e.getMessage(), e);
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

    private boolean checkIfGtinExists(String gtin) {
        return productRepository.existsByGtin(gtin);
    }

    private String generateSku(String productName, Long categoryId, Long manufacturerId, Long productId,
                               List<ProductRequest.ProductAttribute> attributes) {
        String[] words = productName.split(" ");
        StringBuilder productCodeBuilder = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty())
                productCodeBuilder.append(word.charAt(0));
        }
        List<String> skuParts = new ArrayList<>();
        skuParts.add(productCodeBuilder.toString().toUpperCase());
        if (categoryId != null)
            skuParts.add(String.valueOf(categoryId));
        if (manufacturerId != null)
            skuParts.add(String.valueOf(manufacturerId));
        if (productId != null)
            skuParts.add(String.valueOf(productId));
        for (ProductRequest.ProductAttribute attribute : attributes) {
            if (attribute.getValue() != null)
                skuParts.add(attribute.getValue());
        }
        return String.join("-", skuParts);
    }

    private boolean checkIfSkuExists(String sku) {
        return productRepository.existsBySku(sku);
    }

    private String appendRandomSuffixToSku(String sku) {
        String suffix = "-" + new Random().nextInt(1000);
        return sku + suffix;
    }

}
