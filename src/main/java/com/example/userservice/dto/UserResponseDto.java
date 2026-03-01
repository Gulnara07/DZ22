package com.example.userservice.dto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "User response data returned from the API")
public class UserResponseDto {

    @Schema(description = "Unique identifier of the user",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "User's full name",
            example = "Ivan Petrov")
    private String name;

    @Schema(description = "User's email address",
            example = "ivan.petrov@example.com")
    private String email;

    @Schema(description = "User's age",
            example = "35")
    private Integer age;

    @Schema(description = "Timestamp when the user was created",
            example = "2024-01-15T10:30:00",
            accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

