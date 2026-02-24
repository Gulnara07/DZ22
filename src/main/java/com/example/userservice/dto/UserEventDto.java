package com.example.userservice.dto;

public class UserEventDto {
    private Long userId;
    private String email;
    private String operation;
    private String timestamp;

    public UserEventDto() {}

    public UserEventDto(Long userId, String email, String operation, String timestamp) {
        this.userId = userId;
        this.email = email;
        this.operation = operation;
        this.timestamp = timestamp;
    }

    // Геттеры и сеттеры
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}