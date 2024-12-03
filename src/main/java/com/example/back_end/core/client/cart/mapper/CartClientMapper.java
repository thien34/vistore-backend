package com.example.back_end.core.client.cart.mapper;

import com.example.back_end.core.client.cart.payload.request.CartRequest;
import com.example.back_end.core.client.cart.payload.response.CartResponse;
import com.example.back_end.entity.ShoppingCartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartClientMapper {

    @Mapping(target = "idProduct", source = "product.id")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "image", source = "product.image")
    @Mapping(target = "unitPrice", source = "product.unitPrice")
    @Mapping(target = "discountPrice", source = "product.discountPrice", defaultValue = "0")
    CartResponse toDto(ShoppingCartItem cartItem);

    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "customer.id", source = "customerId")
    @Mapping(target = "isAdmin", constant = "false")
    ShoppingCartItem toEntity(CartRequest cartRequest);

}
