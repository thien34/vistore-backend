package com.example.back_end.core.admin.product.service.impl;

import com.example.back_end.core.admin.category.mapper.CategoryMapper;
import com.example.back_end.core.admin.product.mapper.ProductMapper;
import com.example.back_end.core.admin.product.payload.request.ProductFilter;
import com.example.back_end.core.admin.product.mapper.ProductTagMapper;
import com.example.back_end.core.admin.product.payload.request.ProductRequest;
import com.example.back_end.core.admin.product.payload.request.ProductTagRequest;
import com.example.back_end.core.admin.product.payload.response.ProductFakeResponse;
import com.example.back_end.core.admin.product.payload.response.ProductNameResponse;
import com.example.back_end.core.admin.product.payload.response.ProductResponse;
import com.example.back_end.core.admin.product.service.ProductService;
import com.example.back_end.core.admin.product.specification.ProductSpecification;
import com.example.back_end.core.admin.product.service.ProductTagService;
import com.example.back_end.core.common.PageResponse;
import com.example.back_end.entity.Category;
import com.example.back_end.entity.Discount;
import com.example.back_end.entity.DiscountAppliedToProduct;
import com.example.back_end.entity.Manufacturer;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductCategoryMapping;
import com.example.back_end.entity.ProductManufacturerMapping;
import com.example.back_end.entity.ProductTag;
import com.example.back_end.infrastructure.constant.SortType;
import com.example.back_end.infrastructure.exception.ResourceNotFoundException;
import com.example.back_end.infrastructure.utils.PageUtils;
import com.example.back_end.repository.DiscountAppliedToProductRepository;
import com.example.back_end.repository.ProductCategoryMappingRepository;
import com.example.back_end.repository.ProductManufacturerMappingRepository;
import com.example.back_end.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductTagService productTagService;
    private final ProductTagMapper productTagMapper;
    private final CategoryMapper categoryMapper;
    private final ProductCategoryMappingRepository productCategoryMappingRepository;
    private final ProductManufacturerMappingRepository productManufacturerMappingRepository;
    private final DiscountAppliedToProductRepository discountAppliedToProductRepository;

    @Override
    public List<ProductNameResponse> getAllProductsName() {
        return productRepository.findAll().stream()
                .map(product -> new ProductNameResponse(product.getId(), product.getName()))
                .toList();
    }

    @Override
    public PageResponse<List<ProductFakeResponse>> getAllProducts(int pageNo, int pageSize) {

        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "id", SortType.DESC.getValue());
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductFakeResponse> responseList = productMapper.toDtoList(productPage.getContent());

        return PageResponse.<List<ProductFakeResponse>>builder()
                .page(productPage.getNumber())
                .size(productPage.getSize())
                .totalPage(productPage.getTotalPages())
                .items(responseList)
                .build();
    }

    @Override
    public PageResponse<List<ProductResponse>> getAllProducts(int pageNo, int pageSize, ProductFilter productFilter) {

        Pageable pageable = PageUtils.createPageable(pageNo, pageSize, "id", SortType.DESC.getValue());

        Specification<Product> buildWhere = ProductSpecification.buildWhere(productFilter);
        // Fetch products using specification and pageable
        Page<Product> productPage = productRepository.findAll(buildWhere, pageable);

        List<ProductResponse> responseList = productMapper.toResponseList(productPage.getContent());

        return PageResponse.<List<ProductResponse>>builder()
                .page(productPage.getNumber())
                .size(productPage.getSize())
                .totalPage(productPage.getTotalPages())
                .items(responseList)
                .build();
    }

    @Override
    public Long getIdProductBySku(String sku) {

        Long productId = productRepository.findIdBySku(sku);
        if (productId == null) {
            return -1L;
        }

        return productId;
    }

    @Override
    @Transactional
    public Long createOrUpdateProduct(ProductRequest request) {

        Product product;

        if (request.getId() != null && request.getId() != -1) {
            // Updating existing product
            product = productRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + request.getId()));

            // Check if SKU is changed and if the new SKU already exists
            if (!product.getSku().equals(request.getSku()) && productRepository.existsBySku(request.getSku())) {
                throw new InvalidDataAccessApiUsageException("SKU already exists in the database");
            }
        } else {
            // Creating new product
            if (StringUtils.hasText(request.getSku()) && productRepository.existsBySku(request.getSku())) {
                throw new InvalidDataAccessApiUsageException("SKU already exists in the database");
            }
        }


        product = productMapper.toEntity(request);
        Product productSave = productRepository.save(product);

        // Save categories
        List<Category> categories = mapCategories(request);
        saveProductCategoryMappings(categories, productSave);

        // Save manufacturers
        List<Manufacturer> manufacturers = mapManufacturers(request);
        saveProductManufacturerMappings(manufacturers, productSave);

        // Save disocunts
        List<Discount> discounts = mapDiscounts(request);
        saveProductDiscountsToProduct(discounts, productSave);

        // Save product tags
        saveProductTags(request, productSave);

        return productSave.getId();
    }

    @Override
    public ProductResponse getProductById(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        ProductResponse response = productMapper.toResponse(product);

        List<Long> categoryIds = productCategoryMappingRepository.findByProductId(product.getId())
                .stream()
                .map(mapping -> ((ProductCategoryMapping) mapping).getCategory())
                .filter(Objects::nonNull)
                .map(Category::getId)
                .collect(Collectors.toList());
        response.setCategoryIds(categoryIds);

        List<Long> manufacturerIds = productManufacturerMappingRepository.findByProductId(product.getId())
                .stream()
                .map(mapping -> ((ProductManufacturerMapping) mapping).getManufacturer())
                .filter(Objects::nonNull)
                .map(Manufacturer::getId)
                .collect(Collectors.toList());
        response.setManufacturerIds(manufacturerIds);


        List<String> productTags = productTagService.getProductTagsByProductId(product.getId())
                .stream()
                .filter(Objects::nonNull)
                .map(ProductTag::getName)
                .collect(Collectors.toList());
        response.setProductTags(productTags);

        List<Long> discountIds = discountAppliedToProductRepository.findByProductId(product.getId())
                .stream()
                .map(mapping -> ((DiscountAppliedToProduct) mapping).getDiscount())
                .filter(Objects::nonNull)
                .map(Discount::getId)
                .toList();
        response.setDiscountIds(discountIds);

        return response;
    }

    @Override
    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }

    private List<Category> mapCategories(ProductRequest request) {
        return request.getCategoryIds().stream()
                .map(categoryId -> Category.builder()
                        .id(categoryId)
                        .build())
                .toList();
    }

    private void saveProductCategoryMappings(List<Category> categories, Product product) {
        productCategoryMappingRepository.deleteByProductId(product.getId());

        List<ProductCategoryMapping> categoryMappings = categories.stream()
                .map(category -> ProductCategoryMapping.builder()
                        .category(category)
                        .product(product)
                        .build())
                .toList();

        productCategoryMappingRepository.saveAll(categoryMappings);
    }

    private List<Manufacturer> mapManufacturers(ProductRequest request) {
        return request.getManufacturerIds().stream()
                .map(manufacturerId -> Manufacturer.builder()
                        .id(manufacturerId)
                        .build())
                .toList();
    }

    private List<Discount> mapDiscounts(ProductRequest request) {
        return request.getDiscountIds().stream()
                .map(discountId -> Discount.builder()
                        .id(discountId)
                        .build())
                .toList();
    }

    private void saveProductManufacturerMappings(List<Manufacturer> manufacturers, Product product) {
        productManufacturerMappingRepository.deleteByProductId(product.getId());

        List<ProductManufacturerMapping> manufacturerMappings = manufacturers.stream()
                .map(manufacturer -> ProductManufacturerMapping.builder()
                        .manufacturer(manufacturer)
                        .product(product)
                        .build())
                .toList();

        productManufacturerMappingRepository.saveAll(manufacturerMappings);

    }

    private void saveProductDiscountsToProduct(List<Discount> discounts, Product product) {
        discountAppliedToProductRepository.deleteByProductId(product.getId());

        List<DiscountAppliedToProduct> discountList = discounts.stream()
                .map(discount -> DiscountAppliedToProduct.builder()
                        .discount(discount)
                        .product(product)
                        .build())
                .toList();

        discountAppliedToProductRepository.saveAll(discountList);
    }

    private void saveProductTags(ProductRequest request, Product product) {
        List<ProductTag> productTags = request.getProductTags().stream()
                .map(tagName -> {
                    ProductTagRequest productTagRequest = ProductTagRequest
                            .builder()
                            .name(tagName)
                            .productId(product.getId())
                            .build();
                    return productTagMapper.toEntity(productTagRequest);
                })
                .toList();

        productTagService.createProductTags(productTags, product);
    }

}
