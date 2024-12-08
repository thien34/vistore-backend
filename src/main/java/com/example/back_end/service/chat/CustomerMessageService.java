package com.example.back_end.service.chat;

import com.example.back_end.entity.Customer;
import com.example.back_end.core.chat.payload.ChatHistoryDTO;
import com.example.back_end.core.chat.payload.MessageDTO;

import java.util.List;

public interface CustomerMessageService {
    MessageDTO sendMessage(Customer sender, Customer receiver, String messageContent);
    List<MessageDTO> getConversation(Long senderId, Long receiverId);

    List<ChatHistoryDTO> getChatHistory(Long userId);
}
