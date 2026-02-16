package com.example.userservice.service;

import com.example.userservice.dto.UserRequestDto;
import com.example.userservice.entity.Users;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_WithNewEmail_Success() {
        UserRequestDto dto = new UserRequestDto();
        dto.setEmail("test@mail.com");

        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userMapper.toEntity(any())).thenReturn(new Users());
        when(userRepository.save(any())).thenReturn(new Users());

        userService.createUser(dto);

        verify(userRepository).save(any());
    }

    @Test
    void createUser_WithExistingEmail_ThrowsException() {
        UserRequestDto dto = new UserRequestDto();
        dto.setEmail("exists@mail.com");

        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.createUser(dto));
        verify(userRepository, never()).save(any());
    }
}