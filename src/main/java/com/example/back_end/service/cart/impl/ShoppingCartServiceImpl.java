package com.example.back_end.service.cart.impl;

import com.example.back_end.core.admin.cart.payload.request.CartRequest;
import com.example.back_end.core.admin.cart.payload.response.BillCountResponse;
import com.example.back_end.core.admin.cart.payload.response.CartResponse;
import com.example.back_end.core.admin.product.payload.response.ProductResponse;
import com.example.back_end.entity.BillCount;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ShoppingCartItem;
import com.example.back_end.repository.BillCountRepository;
import com.example.back_end.repository.CustomerRepository;
import com.example.back_end.repository.ProductRepository;
import com.example.back_end.repository.ShoppingCartItemRepository;
import com.example.back_end.service.cart.ShoppingCartService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final BillCountRepository billCountRepository;

    @Override
    public void addProduct(CartRequest cartRequest, String parenId) {
        Product product = productRepository.findById(cartRequest.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + cartRequest.getProductId()));
        List<ShoppingCartItem> existingCartItems = cartItemRepository.findByParentId(parenId);
        boolean productExistsInCart = false;
        for (ShoppingCartItem existingCartItem : existingCartItems) {
            if (existingCartItem.getProduct().getId().equals(product.getId())) {
                existingCartItem.setQuantity(existingCartItem.getQuantity() + cartRequest.getQuantity());
                cartItemRepository.save(existingCartItem);
                productExistsInCart = true;
                break;
            }
        }

        if (!productExistsInCart) {
            ShoppingCartItem newCartItem = cartRequest.toShoppingCartItem(product, null);
            newCartItem.setParentId(parenId);
            cartItemRepository.save(newCartItem);
        }
    }

    @Override
    public void addBill(String id) {
        BillCount billCount = billCountRepository.findFirstByOrderByIdAsc();
        if (billCount == null) {
            billCount = new BillCount();
            billCount.setCount(1L);

        } else {
            billCount.setCount(billCount.getCount() + 1);
        }
        billCountRepository.save(billCount);
        ShoppingCartItem cartItem = new ShoppingCartItem();
        cartItem.setCartUUID(id);
        cartItem.setIsAdmin(true);
        cartItem.setBillQuantity(billCount.getCount() + 1);

        cartItemRepository.save(cartItem);

    }

    @Override
    public List<CartResponse> getCartById(String uuid) {
        List<ShoppingCartItem> existingCartItems = cartItemRepository.findByParentId(uuid);
        return existingCartItems.stream()
                .filter(item -> item.getProduct() != null)
                .map(this::mapToCartResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Long> getBillIds() {
        List<ShoppingCartItem> cartItems = cartItemRepository.findAll();
        Map<String, Long> billCounts = new HashMap<>();
        cartItems.stream()
                .filter(item -> item.getIsAdmin() && item.getParentId() == null)
                .forEach(item -> {
                    String billId = item.getCartUUID();
                    Long quantity = item.getBillQuantity();
                    billCounts.put(billId, billCounts.getOrDefault(billId, 0L) + quantity);
                });

        return billCounts;
    }

    @Override
    @Transactional
    public void deleteBill(String id) {
        cartItemRepository.deleteByParentId(id);
        cartItemRepository.deleteByCartUUID(id);
    }

    @Override
    public void updateQuantity(Long id, Integer quantity) {
        ShoppingCartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

    @Override
    public Map<String, BillCountResponse> getBillIdsCount() {
        List<ShoppingCartItem> cartItems = cartItemRepository.findAll();
        Map<String, BillCountResponse> billCounts = new HashMap<>();

        cartItems.stream()
                .filter(item -> item.getIsAdmin() && item.getParentId() == null)
                .forEach(item -> {
                    String billId = item.getCartUUID();
                    Long numberBill = item.getBillQuantity();

                    Integer totalItems = (int) cartItems.stream()
                            .filter(innerItem -> billId.equals(innerItem.getParentId()))
                            .count();

                    billCounts.put(billId, new BillCountResponse(numberBill, totalItems));
                });

        return billCounts;
    }

    private CartResponse mapToCartResponse(ShoppingCartItem item) {
        CartResponse response = new CartResponse();
        response.setId(item.getId());
        response.setCartUUID(item.getCartUUID());
        response.setIsAdmin(item.getIsAdmin());
        response.setParentId(item.getParentId());
        response.setQuantity(item.getQuantity());
        ProductResponse productResponse = new ProductResponse(item.getProduct());
        response.setProductResponse(productResponse);
        return response;
    }

}

