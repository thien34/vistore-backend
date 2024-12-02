package com.example.back_end.service.cart;

import com.example.back_end.core.client.cart.payload.request.CartPaymentRequest;
import com.example.back_end.core.client.cart.payload.request.CartRequest;
import com.example.back_end.core.client.cart.payload.response.CartResponse;

import java.util.List;

public interface ShoppingCartClientService {

    void addCart(CartRequest cartRequest);

    List<CartResponse> getAllCartByCustomerId(Long idCustomer);

    List<CartResponse> getCartsByCustomerId(Long idCustomer, CartPaymentRequest cartPaymentRequest);

    void updateQuantityProduct(Long id, int quantity);

    void deleteProduct(Long id);

}
