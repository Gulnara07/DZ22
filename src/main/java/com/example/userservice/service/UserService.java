package com.example.userservice.service;

import com.example.userservice.dto.UserRequestDto;
import com.example.userservice.dto.UserResponseDto;
import com.example.userservice.entity.Users;
import com.example.userservice.kafka.UserEventProducer;
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
    private final UserEventProducer eventProducer;

    // добавлен eventProducer
    public UserService(UserRepository userRepository, UserMapper userMapper,
                       UserEventProducer eventProducer) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.eventProducer = eventProducer;
    }

    public UserResponseDto createUser(UserRequestDto dto) {
        Users user = userMapper.toEntity(dto);
        Users savedUser = userRepository.save(user);

        // отправка события в Kafka
        eventProducer.sendUserCreatedEvent(savedUser.getId(), savedUser.getEmail());

        return userMapper.toResponseDto(savedUser);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + id));
        return userMapper.toResponseDto(user);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + id));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());

        Users updatedUser = userRepository.save(user);
        return userMapper.toResponseDto(updatedUser);
    }

    public void deleteUser(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден: " + id));

        String email = user.getEmail();
        userRepository.deleteById(id);

        // отправка события в Kafka
        eventProducer.sendUserDeletedEvent(id, email);
    }
}

