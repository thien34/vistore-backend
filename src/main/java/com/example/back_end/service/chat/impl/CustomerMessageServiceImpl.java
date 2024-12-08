package com.example.back_end.service.chat.impl;

import com.example.back_end.core.chat.payload.ChatHistoryDTO;
import com.example.back_end.core.chat.payload.MessageDTO;
import com.example.back_end.core.chat.payload.UserDTO;
import com.example.back_end.entity.Customer;
import com.example.back_end.entity.CustomerMessage;
import com.example.back_end.repository.CustomerMessageRepository;
import com.example.back_end.repository.CustomerRepository;
import com.example.back_end.service.chat.CustomerMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CustomerMessageServiceImpl implements CustomerMessageService {
    @Autowired
    private CustomerMessageRepository messageRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public MessageDTO sendMessage(Customer sender, Customer receiver, String messageContent) {
        CustomerMessage message = new CustomerMessage();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessage(messageContent);
        message.setTimestamp(LocalDateTime.now());
        message.setIsRead(false);
        CustomerMessage savedMessage = messageRepository.save(message);
        return convertToDTO(savedMessage);
    }


    @Override
    public List<MessageDTO> getConversation(Long senderId, Long receiverId) {
        List<CustomerMessage> messages = messageRepository.findByConversationParticipants(senderId, receiverId);

        messages.stream()
                .filter(msg -> msg.getReceiver().getId().equals(senderId) && !msg.getIsRead())
                .forEach(msg -> {
                    msg.setIsRead(true);
                    messageRepository.save(msg);
                });

        return messages.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatHistoryDTO> getChatHistory(Long userId) {
        List<CustomerMessage> allMessages = messageRepository.findAllUserMessages(userId);

        Map<Long, List<CustomerMessage>> messagesByUser = allMessages.stream()
                .collect(Collectors.groupingBy(message ->
                        message.getSender().getId().equals(userId)
                                ? message.getReceiver().getId()
                                : message.getSender().getId()
                ));

        List<ChatHistoryDTO> chatHistory = new ArrayList<>();

        for (Map.Entry<Long, List<CustomerMessage>> entry : messagesByUser.entrySet()) {
            Long otherUserId = entry.getKey();
            List<CustomerMessage> messages = entry.getValue();

            CustomerMessage lastMessage = messages.stream()
                    .max(Comparator.comparing(CustomerMessage::getTimestamp))
                    .orElse(null);

            if (lastMessage != null) {
                Customer otherUser = customerRepository.findById(otherUserId)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                long unreadCount = messages.stream()
                        .filter(msg -> !msg.getIsRead() && msg.getReceiver().getId().equals(userId))
                        .count();

                ChatHistoryDTO historyDTO = new ChatHistoryDTO();
                historyDTO.setUserId(otherUserId);
                historyDTO.setUserName(otherUser.getFirstName());
                historyDTO.setLastMessage(lastMessage.getMessage());
                historyDTO.setLastMessageTime(lastMessage.getTimestamp());
                historyDTO.setUnreadCount(unreadCount);

                chatHistory.add(historyDTO);
            }
        }

        chatHistory.sort((a, b) -> b.getLastMessageTime().compareTo(a.getLastMessageTime()));

        return chatHistory;
    }


    private MessageDTO convertToDTO(CustomerMessage message) {
        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setMessage(message.getMessage());
        dto.setTimestamp(message.getTimestamp());

        UserDTO senderDTO = new UserDTO();
        senderDTO.setId(message.getSender().getId());
        senderDTO.setName(message.getSender().getFirstName());
        dto.setSender(senderDTO);

        UserDTO receiverDTO = new UserDTO();
        receiverDTO.setId(message.getReceiver().getId());
        receiverDTO.setName(message.getReceiver().getFirstName());
        dto.setReceiver(receiverDTO);

        return dto;
    }

}
