package com.example.back_end.service.cart.impl;

import com.example.back_end.core.admin.cart.payload.request.CartRequest;
import com.example.back_end.core.admin.cart.payload.response.BillCountResponse;
import com.example.back_end.core.admin.cart.payload.response.CartResponse;
import com.example.back_end.core.admin.product.payload.response.ProductResponse;
import com.example.back_end.entity.BillCount;
import com.example.back_end.entity.Discount;
import com.example.back_end.entity.DiscountAppliedToProduct;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ShoppingCartItem;
import com.example.back_end.infrastructure.exception.NotFoundException;
import com.example.back_end.repository.BillCountRepository;
import com.example.back_end.repository.DiscountAppliedToProductRepository;
import com.example.back_end.repository.ProductRepository;
import com.example.back_end.repository.ShoppingCartItemRepository;
import com.example.back_end.service.cart.ShoppingCartService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final BillCountRepository billCountRepository;
    private final DiscountAppliedToProductRepository discountAppliedToProductRepository;

    @Override
    public void addProduct(CartRequest cartRequest, String parentId) {
        Product product = productRepository.findById(cartRequest.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại với ID: " + cartRequest.getProductId()));

        List<ShoppingCartItem> existingCartItems = cartItemRepository.findByParentId(parentId);
        boolean productExistsInCart = false;

        for (ShoppingCartItem existingCartItem : existingCartItems) {
            if (existingCartItem.getProduct().getId().equals(product.getId())) {
                int newQuantity = existingCartItem.getQuantity() + cartRequest.getQuantity();
//                if (newQuantity > product.getQuantity()) {
//                    throw new RuntimeException("Không thể thêm số lượng vượt quá số lượng tồn kho cho sản phẩm: " + product.getName());
//                }
                existingCartItem.setQuantity(newQuantity);
                cartItemRepository.save(existingCartItem);
                productExistsInCart = true;
                break;
            }
        }

        if (!productExistsInCart) {
            if (cartRequest.getQuantity() > product.getQuantity()) {
                throw new RuntimeException("Không thể thêm số lượng vượt quá số lượng tồn kho cho sản phẩm: " + product.getName());
            }
            ShoppingCartItem newCartItem = cartRequest.toShoppingCartItem(product, null);
            newCartItem.setParentId(parentId);
            cartItemRepository.save(newCartItem);
        }

        product.setQuantity(product.getQuantity() - cartRequest.getQuantity());
        productRepository.save(product);
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

        List<ShoppingCartItem> cartItems = cartItemRepository.findByParentId(id);

        cartItems.forEach(cartItem -> {
            deleteItemInBill(cartItem.getCartUUID());
        });

        ShoppingCartItem cartItem = cartItemRepository.findByCartUUID(id);
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void deleteItemInBill(String id) {

        ShoppingCartItem cartItem = cartItemRepository.findByCartUUID(id);
        if (cartItem == null) {
            throw new NotFoundException("Không thể tìm thấy giỏ hàng với ID: " + id);
        }

        Product product = productRepository.findById(cartItem.getProduct().getId())
                .orElseThrow(() -> new EntityNotFoundException("Không thể tìm thấy sản phẩm với ID: " + cartItem.getProduct().getId()));

        product.setQuantity(product.getQuantity() + cartItem.getQuantity());
        productRepository.save(product);
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void updateQuantity(Long id, Integer quantity) {

        ShoppingCartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không thể tìm thấy sản phẩm với ID: " + id));

        Integer oldQuantity = cartItem.getQuantity();
        Product product = productRepository.findById(cartItem.getProduct().getId())
                .orElseThrow(() -> new EntityNotFoundException("Không thể tìm thấy sản phẩm với ID: " + cartItem.getProduct().getId()));

        if (quantity > product.getQuantity() + oldQuantity) {
            throw new RuntimeException("Không đủ hàng cho sản phẩm: " + product.getName()
                    + ". Số lượng có sẵn: " + (product.getQuantity() + oldQuantity));
        }

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        product.setQuantity(product.getQuantity() + oldQuantity - quantity);
        productRepository.save(product);
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
        BigDecimal largestDiscountPercentage = calculateLargestDiscountPercentage(item.getProduct());
        productResponse.setLargestDiscountPercentage(largestDiscountPercentage);
        response.setProductResponse(productResponse);
        return response;
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

}

