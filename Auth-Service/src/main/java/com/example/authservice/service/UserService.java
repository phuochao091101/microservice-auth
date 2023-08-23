package com.example.authservice.service;

import com.example.authservice.request.CreateUserRequest;
import com.example.authservice.request.UpdateUserRequest;
import com.example.authservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    List<User> findAll();
    void save(CreateUserRequest request);
    void update(UpdateUserRequest updateUserRequest);
    void deleteById(int id);
}
