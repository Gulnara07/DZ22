package com.example.userservice.repository;

import com.example.userservice.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByEmail(String email);
}
