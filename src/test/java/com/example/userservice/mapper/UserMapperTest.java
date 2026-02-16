package com.example.userservice.mapper;

import com.example.userservice.dto.UserRequestDto;
import com.example.userservice.dto.UserResponseDto;
import com.example.userservice.entity.Users;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserMapperTest {

    private final UserMapper mapper = new UserMapper();

    @Test
    void toEntity_ShouldReturnUser() {
        UserRequestDto dto = new UserRequestDto();
        dto.setName("John");
        dto.setEmail("john@mail.com");
        dto.setAge(25);

        Users user = mapper.toEntity(dto);

        assertNotNull(user);
    }

    @Test
    void toResponseDto_ShouldReturnDto() {
        Users user = new Users("John", "john@mail.com", 25);
        user.setId(1L);

        UserResponseDto dto = mapper.toResponseDto(user);

        assertNotNull(dto);
    }
}