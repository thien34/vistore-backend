package com.example.back_end.service.product.impl;

import com.example.back_end.core.client.product.mapper.ProductClientMapper;
import com.example.back_end.core.client.product.payload.reponse.ProductResponse;
import com.example.back_end.entity.Category;
import com.example.back_end.entity.Product;
import com.example.back_end.infrastructure.constant.OrderStatusType;
import com.example.back_end.repository.CategoryRepository;
import com.example.back_end.repository.OrderItemRepository;
import com.example.back_end.repository.ProductRepository;
import com.example.back_end.service.product.ProductClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductClientServiceImpl implements ProductClientService {

    private final ProductRepository productRepository;
    private final ProductClientMapper productClientMapper;
    private final OrderItemRepository orderItemRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<ProductResponse> getRootProducts() {
        List<Product> products = productRepository.findByParentProductIdIsNull();

        List<ProductResponse> responseList = products.stream().map(productClientMapper::toDto).toList();

        responseList.forEach(product -> {
            List<Product> childProducts = productRepository.findByParentProductId(product.getId());
            if (!childProducts.isEmpty()) {
                Product cheapestProduct = childProducts.stream().min(Comparator.comparing(Product::getUnitPrice)).orElse(null);
                product.setUnitPrice(cheapestProduct.getUnitPrice());
                product.setDiscountPrice(cheapestProduct.getDiscountPrice() != null ? cheapestProduct.getDiscountPrice() : BigDecimal.valueOf(0));
                product.setImage(cheapestProduct.getImage());
            }
            Integer sold = orderItemRepository.sumQuantityByParentProductIdAndOrderStatus(product.getId(), OrderStatusType.COMPLETED);
            product.setSold(sold != null ? sold : 0);
        });
        return responseList;
    }

    @Override
    public List<ProductResponse> getRootProductsByCategorySlug(String categorySlug) {

        Category category = categoryRepository.findBySlug(categorySlug);
        List<Category> allCategories = getAllSubCategories(category);

        List<Product> products = productRepository.findByParentProductIdIsNullAndCategoryIn(allCategories);

        List<ProductResponse> responseList = products.stream().map(productClientMapper::toDto).toList();

        responseList.forEach(product -> {
            List<Product> childProducts = productRepository.findByParentProductId(product.getId());
            if (!childProducts.isEmpty()) {
                Product cheapestProduct = childProducts.stream()
                        .min(Comparator.comparing(Product::getUnitPrice))
                        .orElse(null);
                product.setUnitPrice(cheapestProduct.getUnitPrice());
                product.setDiscountPrice(cheapestProduct.getDiscountPrice());
                product.setImage(cheapestProduct.getImage());
            }
            Integer sold = orderItemRepository.sumQuantityByParentProductIdAndOrderStatus(
                    product.getId(), OrderStatusType.COMPLETED);
            product.setSold(sold != null ? sold : 0);
        });

        return responseList;
    }

    private List<Category> getAllSubCategories(Category category) {
        List<Category> subCategories = categoryRepository.findByCategoryParent(category);
        List<Category> allCategories = new ArrayList<>(subCategories);
        for (Category subCategory : subCategories) {
            allCategories.addAll(getAllSubCategories(subCategory));
        }
        allCategories.add(category);
        return allCategories;
    }

}
