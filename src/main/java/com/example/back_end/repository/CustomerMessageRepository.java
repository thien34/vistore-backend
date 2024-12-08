package com.example.back_end.repository;

import com.example.back_end.entity.CustomerMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerMessageRepository extends JpaRepository<CustomerMessage, Long> {

    List<CustomerMessage> findBySenderIdAndReceiverIdOrderByTimestampAsc(Long senderId, Long receiverId);

    List<CustomerMessage> findByReceiverIdOrderByTimestampDesc(Long receiverId);
    @Query("SELECT m FROM CustomerMessage m " +
            "WHERE (m.sender.id = :senderId AND m.receiver.id = :receiverId) " +
            "OR (m.sender.id = :receiverId AND m.receiver.id = :senderId) " +
            "ORDER BY m.timestamp ASC")
    List<CustomerMessage> findByConversationParticipants(
            @Param("senderId") Long senderId,
            @Param("receiverId") Long receiverId
    );
    @Query(value = """
            SELECT DISTINCT m.* FROM customer_messages m
            WHERE m.sender_id = :userId OR m.receiver_id = :userId
            ORDER BY m.timestamp DESC
            """, nativeQuery = true)
    List<CustomerMessage> findAllUserMessages(Long userId);
}