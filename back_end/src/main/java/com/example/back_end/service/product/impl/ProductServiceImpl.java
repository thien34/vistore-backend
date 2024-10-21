package com.example.back_end.service.product.impl;

import com.example.back_end.core.admin.product.payload.request.ProductRequest;
import com.example.back_end.core.admin.product.payload.response.ProductResponse;
import com.example.back_end.entity.Category;
import com.example.back_end.entity.Manufacturer;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductAttribute;
import com.example.back_end.entity.ProductAttributeValue;
import com.example.back_end.infrastructure.cloudinary.CloudinaryUpload;
import com.example.back_end.infrastructure.constant.CloudinaryTypeFolder;
import com.example.back_end.repository.ProductAttributeValueRepository;
import com.example.back_end.repository.ProductRepository;
import com.example.back_end.service.product.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductAttributeValueRepository productAttributeValueRepository;
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
            products.add(product);
            attributeValues.addAll(mapAttributesToValues(product, request.getAttributes(), uploadImage(request).orElse("")));
        }

        saveProductsAndAttributes(products, attributeValues);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductResponse> productResponses = products.stream()
                .filter(x -> x.getParentProductId() == null)
                .map(ProductResponse::fromProduct)
                .toList();


        return productResponses;
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

        List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::fromProductFull)
                .toList();
        return productResponses;
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
                .collect(Collectors.toList());
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

            if (exists) {
                throw new IllegalArgumentException("Giá trị " + attribute.getValue() + " đã tồn tại cho sản phẩm " + product.getName());
            }
        }
    }
}
