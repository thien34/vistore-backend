package com.example.back_end.core.admin.discount.payload.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponseDetails {
    Long id;
    String name;
    String categoryName;
    String manufacturerName;
    String sku;
    Long productParentId;
    String imageUrl;
    String fullName;
}
