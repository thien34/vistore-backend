package com.example.back_end.core.admin.returnProduct.mapper;

import com.example.back_end.core.admin.returnProduct.payload.request.ReturnTimeLineRequest;
import com.example.back_end.core.admin.returnProduct.payload.response.ReturnTimeLineResponse;
import com.example.back_end.entity.ReturnRequest;
import com.example.back_end.entity.ReturnTimeLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReturnTimeLineMapper {
    @Mapping(source = "returnRequest.id",target = "returnRequestId")
    ReturnTimeLineResponse mapToReturnTimeLineResponse(ReturnTimeLine returnTimeLine);

    List<ReturnTimeLineResponse> mapToReturnTimeLineResponses(List<ReturnTimeLine> returnTimeLines);

    @Mapping(source = "returnRequestId",target = "returnRequest.id")
    ReturnTimeLine mapToReturnTimeLine(ReturnTimeLineRequest request);

    List<ReturnTimeLine> mapToReturnTimeLines(List<ReturnTimeLineRequest> requests);

    @Mapping(source = "returnRequestStatusId", target = "status")
    @Mapping(source = "id",target = "returnRequestId")
    ReturnTimeLineRequest mapToReturnTimeLineRequest(ReturnRequest returnRequest);


}
