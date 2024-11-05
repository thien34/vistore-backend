package com.example.back_end.core.admin.role.payload.request;

import com.example.back_end.core.common.PageRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleSearchRequest extends PageRequest {

    private String name = "";

    private Boolean active;

}
