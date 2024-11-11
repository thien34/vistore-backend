package com.example.back_end.service.returnProducts;

import com.example.back_end.core.admin.returnProduct.payload.request.ReturnInvoiceRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnInvoiceResponse;
import com.example.back_end.core.common.PageRequest;
import com.example.back_end.core.common.PageResponse1;

import java.util.List;

public interface ReturnInvoiceServices {
    ReturnInvoiceResponse saveReturnInvoice(ReturnInvoiceRequest returnInvoiceRequest);

    ReturnInvoiceResponse getReturnInvoiceById(Long id);

    ReturnInvoiceResponse getReturnInvoiceByOrderId(Long orderId);

    PageResponse1<List<ReturnInvoiceResponse>> getAllReturnInvoices(PageRequest pageRequest);
}
