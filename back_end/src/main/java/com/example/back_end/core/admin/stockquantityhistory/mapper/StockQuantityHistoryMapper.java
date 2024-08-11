package com.example.back_end.core.admin.stockquantityhistory.mapper;

import com.example.back_end.core.admin.stockquantityhistory.payload.request.StockQuantityHistoryRequest;
import com.example.back_end.core.admin.stockquantityhistory.payload.response.StockQuantityHistoryResponse;
import com.example.back_end.entity.StockQuantityHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StockQuantityHistoryMapper {

    @Mapping(source = "product.id", target = "productId")
    StockQuantityHistoryResponse mapToDto(StockQuantityHistory stockQuantityHistory);

    List<StockQuantityHistoryResponse> mapToDtoList(List<StockQuantityHistory> stockQuantityHistories);

    StockQuantityHistory mapToEntity(StockQuantityHistoryRequest stockQuantityHistoryRequest);

    void updateStockQuantityHistory(StockQuantityHistoryRequest stockQuantityHistoryRequest, @MappingTarget StockQuantityHistory stockQuantityHistory);

}
