package com.example.back_end.core.chat.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String name;
    // Thêm các trường cần thiết khác của Customer
}
