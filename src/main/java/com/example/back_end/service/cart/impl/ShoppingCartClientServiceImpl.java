package com.example.back_end.service.cart.impl;

import com.example.back_end.core.client.cart.mapper.CartClientMapper;
import com.example.back_end.core.client.cart.payload.request.CartPaymentRequest;
import com.example.back_end.core.client.cart.payload.request.CartRequest;
import com.example.back_end.core.client.cart.payload.response.CartResponse;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ProductAttributeValue;
import com.example.back_end.entity.ShoppingCartItem;
import com.example.back_end.repository.ProductAttributeValueRepository;
import com.example.back_end.repository.ProductRepository;
import com.example.back_end.repository.ShoppingCartItemRepository;
import com.example.back_end.service.cart.ShoppingCartClientService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartClientServiceImpl implements ShoppingCartClientService {

    private final ShoppingCartItemRepository cartItemRepository;
    private final CartClientMapper cartClientMapper;
    private final ProductRepository productRepository;
    private final ProductAttributeValueRepository productAttributeValueRepository;

    @Override
    public void addCart(CartRequest cartRequest) {

        Product product = productRepository.findById(cartRequest.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + cartRequest.getProductId()));

        List<ShoppingCartItem> existingCartItems = cartItemRepository.findAllByCustomerId(cartRequest.getCustomerId());
        boolean productExistsInCart = false;

        for (ShoppingCartItem existingCartItem : existingCartItems) {
            if (existingCartItem.getProduct().getId().equals(product.getId())) {
                int newQuantity = existingCartItem.getQuantity() + cartRequest.getQuantity();
                if (newQuantity > product.getQuantity()) {
                    throw new IllegalArgumentException("Cannot add more than available stock for product: " + product.getName());
                }
                cartItemRepository.save(existingCartItem);
                productExistsInCart = true;
                break;
            }
        }

        if (!productExistsInCart) {
            if (cartRequest.getQuantity() > product.getQuantity()) {
                throw new IllegalArgumentException("Cannot add more than available stock for product: " + product.getName());
            }
            ShoppingCartItem newCartItem = cartClientMapper.toEntity(cartRequest);
            cartItemRepository.save(newCartItem);
        }

    }

    @Override
    public List<CartResponse> getAllCartByCustomerId(Long idCustomer) {

        List<ShoppingCartItem> shoppingCartItems = cartItemRepository.findAllByCustomerId(idCustomer);

        return shoppingCartItems.stream()
                .map(cartClientMapper::toDto)
                .peek(cartResponse -> {
                    Product product = productRepository.findById(cartResponse.getIdProduct())
                            .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + cartResponse.getIdProduct()));
                    Product productParent = productRepository.findById(product.getParentProductId())
                            .orElseThrow(() -> new EntityNotFoundException("Product parent not found with ID: " + product.getParentProductId()));
                    cartResponse.setSlug(productParent.getSlug());
                    cartResponse.setAttributeProduct(getAttributeProduct(product));
                    cartResponse.setQuantityProduct(product.getQuantity());
                })
                .sorted(Comparator.comparing(CartResponse::getId).reversed())
                .toList();
    }

    @Override
    public List<CartResponse> getCartsByCustomerId(Long idCustomer, CartPaymentRequest CartPaymentRequest) {

        List<ShoppingCartItem> shoppingCartItems = cartItemRepository.findAllByCustomerIdAndIdIn(idCustomer, CartPaymentRequest.getIdCarts());

        return shoppingCartItems.stream()
                .map(cartClientMapper::toDto)
                .peek(cartResponse -> {
                    Product product = productRepository.findById(cartResponse.getIdProduct())
                            .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + cartResponse.getIdProduct()));
                    Product productParent = productRepository.findById(product.getParentProductId())
                            .orElseThrow(() -> new EntityNotFoundException("Product parent not found with ID: " + product.getParentProductId()));
                    cartResponse.setSlug(productParent.getSlug());
                    cartResponse.setAttributeProduct(getAttributeProduct(product));
                })
                .sorted(Comparator.comparing(CartResponse::getId))
                .toList();
    }

    public String getAttributeProduct(Product product) {
        List<ProductAttributeValue> attributeValues = productAttributeValueRepository.findByProduct(product);

        return attributeValues.stream()
                .map(ProductAttributeValue::getValue)
                .collect(Collectors.joining(" - "));
    }

    @Override
    public void updateQuantityProduct(Long id, int quantity) {

        ShoppingCartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found with ID: " + id));
        Product product = productRepository.findById(cartItem.getProduct().getId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + cartItem.getProduct().getId()));

        cartItem.setQuantity(quantity);

        if (cartItem.getQuantity() > product.getQuantity()) {
            throw new RuntimeException("Insufficient stock for product: " + product.getName() + ". Available quantity: " + product.getQuantity());
        }

        cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteProduct(Long id) {

        if (!cartItemRepository.existsById(id)) {
            throw new EntityNotFoundException("Cart item not found with ID: " + id);
        }

        cartItemRepository.deleteById(id);
    }

}

