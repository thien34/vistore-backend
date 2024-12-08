package com.example.back_end.core.chat.payload;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class MessageDTO {
    private Long id;
    private UserDTO sender;
    private UserDTO receiver;
    private String message;
    private LocalDateTime timestamp;

}
