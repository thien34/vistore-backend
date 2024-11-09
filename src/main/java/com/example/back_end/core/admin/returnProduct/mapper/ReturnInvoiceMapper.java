package com.example.back_end.core.admin.returnProduct.mapper;

import com.example.back_end.core.admin.returnProduct.payload.request.ReturnInvoiceRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnInvoiceResponse;
import com.example.back_end.entity.ReturnInvoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReturnInvoiceMapper {
    @Mapping(source = "returnRequest.id", target = "returnRequestId")
    @Mapping(source = "order.id", target = "orderId")
    ReturnInvoiceResponse mapReturnInvoiceResponse(ReturnInvoice returnInvoice);

    @Mapping(source = "returnRequest.id", target = "returnRequestId")
    @Mapping(source = "order.id", target = "orderId")
    List<ReturnInvoiceResponse> mapReturnInvoices(List<ReturnInvoice> returnInvoices);

    @Mapping(source = "returnRequestId", target = "returnRequest.id")
    @Mapping(source = "orderId", target = "order.id")
    ReturnInvoice mapReturnInvoice(ReturnInvoiceRequest returnInvoiceRequest);
}
