package com.example.back_end.core.admin.product.payload.response;

public interface ProductTagCountProjection {
    
    Long getProductTagId();

    Integer getTaggedProductCount();
}
