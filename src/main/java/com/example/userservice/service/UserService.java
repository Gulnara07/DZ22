package com.example.userservice.service;

import com.example.userservice.dto.UserRequestDto;
import com.example.userservice.dto.UserResponseDto;
import com.example.userservice.entity.Users;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // Create
    public UserResponseDto createUser(UserRequestDto dto) {
        // Проверка email (как в вашем коде)
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email уже существует: " + dto.getEmail());
        }

        Users user = userMapper.toEntity(dto);
        Users savedUser = userRepository.save(user);
        return userMapper.toResponseDto(savedUser);
    }

    // Read all
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    // Read one
    public UserResponseDto getUserById(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + id));
        return userMapper.toResponseDto(user);
    }

    // Update
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + id));

        // Проверка email если меняется
        if (!user.getEmail().equals(dto.getEmail()) &&
                userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email уже используется: " + dto.getEmail());
        }

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());

        Users updatedUser = userRepository.save(user);
        return userMapper.toResponseDto(updatedUser);
    }

    // Delete
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Пользователь не найден: " + id);
        }
        userRepository.deleteById(id);
    }
}