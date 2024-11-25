package com.example.back_end.core.client.cart.controller;

import com.example.back_end.core.client.cart.payload.request.CartRequest;
import com.example.back_end.core.client.cart.payload.request.CartUpdateRequest;
import com.example.back_end.core.client.cart.payload.response.CartResponse;
import com.example.back_end.core.common.ResponseData;
import com.example.back_end.service.cart.ShoppingCartClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/client/carts")
public class CartClientController {

    private final ShoppingCartClientService shoppingCartClientService;

    @GetMapping("/{idCustomer}")
    public ResponseData<List<CartResponse>> getAllCartByCustomerId(@PathVariable Long idCustomer) {

        List<CartResponse> cartResponses = shoppingCartClientService.getAllCartByCustomerId(idCustomer);

        return new ResponseData<>(HttpStatus.OK.value(), "Get all cart by customer ID successfully", cartResponses);
    }

    @PostMapping
    public ResponseData<Void> addToCart(@RequestBody CartRequest cartRequest) {

        shoppingCartClientService.addCart(cartRequest);

        return new ResponseData<>(HttpStatus.OK.value(), "Add to cart successfully");
    }

    @PutMapping("/{id}")
    public ResponseData<Void> updateQuantityProduct(@PathVariable Long id, @Valid @RequestBody CartUpdateRequest cartUpdateRequest) {

        shoppingCartClientService.updateQuantityProduct(id, cartUpdateRequest.getQuantity());

        return new ResponseData<>(HttpStatus.OK.value(), "Update quantity product successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseData<Void> deleteProduct(@PathVariable Long id) {

        shoppingCartClientService.deleteProduct(id);

        return new ResponseData<>(HttpStatus.OK.value(), "Delete product successfully");
    }

}
