package com.example.back_end.core.chat.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatHistoryDTO {
    private Long userId;
    private String userName;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
    private Long unreadCount;
}
