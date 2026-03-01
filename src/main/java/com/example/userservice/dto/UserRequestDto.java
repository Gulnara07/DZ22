package com.example.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User request data for creating/updating a user")
public class UserRequestDto {
    @Schema(description = "User's full name", example = "Ivan Petrov")
    private String name;

    @Schema(description = "User's email address", example = "Ivan.petrov@example.com")
    private String email;

    @Schema(description = "User's age", example = "35")
    private Integer age;

    // Геттеры и сеттеры

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
