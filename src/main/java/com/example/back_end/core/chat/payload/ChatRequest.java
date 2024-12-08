package com.example.back_end.core.chat.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequest {
    private Long senderId;
    private Long receiverId;
    private String message;
}