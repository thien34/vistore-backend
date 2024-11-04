package com.example.back_end.core.admin.cart.payload.request;

import com.example.back_end.entity.Customer;
import com.example.back_end.entity.Product;
import com.example.back_end.entity.ShoppingCartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
public class CartRequest {
    private String cartUUID;
    private Long productId;
    private Integer quantity;
    private Long customerId;
    private Boolean isAdmin = false;

    public ShoppingCartItem toShoppingCartItem(Product product, Customer customer) {
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setCartUUID(cartUUID);
        shoppingCartItem.setProduct(product);
        shoppingCartItem.setQuantity(quantity);
        shoppingCartItem.setCustomer(customer);
        shoppingCartItem.setIsAdmin(isAdmin);
        return shoppingCartItem;
    }
}
