package com.example.userservice.kafka;

import com.example.userservice.dto.UserEventDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class UserEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.user-events:user-events}")
    private String topic;

    public UserEventProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendUserCreatedEvent(Long userId, String email) {
        UserEventDto event = new UserEventDto(
                userId,
                email,
                "CREATED",
                LocalDateTime.now().toString()
        );
        sendEvent(event);
    }

    public void sendUserDeletedEvent(Long userId, String email) {
        UserEventDto event = new UserEventDto(
                userId,
                email,
                "DELETED",
                LocalDateTime.now().toString()
        );
        sendEvent(event);
    }

    private void sendEvent(UserEventDto event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(topic, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}