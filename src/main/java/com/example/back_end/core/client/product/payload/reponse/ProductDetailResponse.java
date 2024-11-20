package com.example.back_end.core.client.product.payload.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailResponse {

    private Long id;

    private String slug;

    private String name;

    private BigDecimal weight;

    private String fullDescription;

    private BigDecimal unitPrice; // Giá gốc nhỏ nhẩt trong list product con

    private BigDecimal discountPrice; // Giá giảm nhất trong list product con

    private List<String> images; // Lấy tất cả ảnh của product con

    private Integer quantitySum; // Tổng số lượng của product con

    private String categoryName;

    private List<ProductVariantResponse> productVariants;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductVariantResponse {

        private Long id; // ID của sản phẩm biến thể

        private List<AttributeValue> attributes; // Danh sách các thuộc tính và giá trị của biến thể

        private Integer quantity; // Số lượng của biến thể

        private BigDecimal unitPrice;

        private BigDecimal discountPrice;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AttributeValue {

        private String attributeName; // Tên thuộc tính, ví dụ: Màu sắc, Kích thước

        private String value; // Giá trị của thuộc tính, ví dụ: Đỏ, Xanh, L, XL
    }

}

