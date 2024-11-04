package com.example.back_end.service.cart;

import com.example.back_end.core.admin.cart.payload.request.CartRequest;
import com.example.back_end.core.admin.cart.payload.response.BillCountResponse;
import com.example.back_end.core.admin.cart.payload.response.CartResponse;

import java.util.List;
import java.util.Map;

public interface ShoppingCartService {

    void addProduct(CartRequest cartRequest, String parenId);

    void addBill(String id);

    List<CartResponse> getCartById(String uuid);

    Map<String, Long> getBillIds();

    void deleteBill(String id);
    void updateQuantity(Long id, Integer quantity);

    Map<String, BillCountResponse> getBillIdsCount();
}
