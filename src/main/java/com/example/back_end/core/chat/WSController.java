package com.example.back_end.core.chat;

import com.example.back_end.core.chat.payload.ChatRequest;
import com.example.back_end.entity.Customer;
import com.example.back_end.repository.CustomerRepository;
import com.example.back_end.service.chat.CustomerMessageService;
import com.example.back_end.core.chat.payload.ChatHistoryDTO;
import com.example.back_end.core.chat.payload.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class WSController {

    @Autowired
    private CustomerMessageService messageService;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/send")
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody ChatRequest chatRequest) {
        Customer sender = customerRepository.findById(chatRequest.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        Customer receiver = customerRepository.findById(chatRequest.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        MessageDTO message = messageService.sendMessage(sender, receiver, chatRequest.getMessage());
        return ResponseEntity.ok(message);
    }

    @GetMapping("/conversation")
    public ResponseEntity<List<MessageDTO>> getConversation(
            @RequestParam Long senderId,
            @RequestParam Long receiverId) {
        List<MessageDTO> messages = messageService.getConversation(senderId, receiverId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/history")
    public ResponseEntity<List<ChatHistoryDTO>> getChatHistory(@RequestParam Long userId) {
        List<ChatHistoryDTO> chatHistory = messageService.getChatHistory(userId);
        return ResponseEntity.ok(chatHistory);
    }

    @GetMapping("/messages/{userId1}/{userId2}")
    public ResponseEntity<List<MessageDTO>> getMessagesBetweenUsers(
            @PathVariable Long userId1,
            @PathVariable Long userId2) {
        List<MessageDTO> messages = messageService.getConversation(userId1, userId2);
        return ResponseEntity.ok(messages);
    }
}
