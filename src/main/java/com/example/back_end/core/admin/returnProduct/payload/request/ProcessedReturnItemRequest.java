package com.example.back_end.core.admin.returnProduct.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessedReturnItemRequest {

    private Long returnRequestId;

    private Long orderItemId;

    private Integer quantity;

    private String status;
}
