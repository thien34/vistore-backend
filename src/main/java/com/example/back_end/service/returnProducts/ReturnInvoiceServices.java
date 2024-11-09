package com.example.back_end.service.returnProducts;

import com.example.back_end.core.admin.returnProduct.payload.request.ReturnInvoiceRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnInvoiceResponse;

import java.util.List;

public interface ReturnInvoiceServices {
    ReturnInvoiceResponse saveReturnInvoice(ReturnInvoiceRequest returnInvoiceRequest);

    ReturnInvoiceResponse getReturnInvoiceById(Long id);

    ReturnInvoiceResponse getReturnInvoiceByOrderId(Long orderId);

    List<ReturnInvoiceResponse> getAllReturnInvoices();
}
