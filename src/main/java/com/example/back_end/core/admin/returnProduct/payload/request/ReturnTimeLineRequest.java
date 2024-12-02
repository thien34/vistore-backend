package com.example.back_end.core.admin.returnProduct.payload.request;

import com.example.back_end.infrastructure.constant.ReturnRequestStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnTimeLineRequest {
    private  Long returnRequestId;
    private ReturnRequestStatusType status;
    private String description;
}
