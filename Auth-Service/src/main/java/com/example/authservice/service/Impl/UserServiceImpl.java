package com.example.authservice.service.Impl;

import com.example.authservice.exception.NotFoundException;
import com.example.authservice.exception.ServiceException;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.request.CreateUserRequest;
import com.example.authservice.request.UpdateUserRequest;
import com.example.authservice.service.UserService;
import com.example.authservice.common.Role;
import com.example.authservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(CreateUserRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new ServiceException("Email exists");
        }
        var user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);
    }

    @Override
    public void update(UpdateUserRequest updateUserRequest) {
        if(!userRepository.findById(updateUserRequest.getId()).isPresent()){
            throw new NotFoundException("Invalid UserId");
        }
        User user=userRepository.findById(updateUserRequest.getId()).get();
        user.setFirstname(updateUserRequest.getFirstName());
        user.setLastname(updateUserRequest.getLastName());
        userRepository.save(user);
    }

    @Override
    public void deleteById(int id) {
        if(!userRepository.findById(id).isPresent()){
            throw new NotFoundException("Invalid UserId");
        }
        userRepository.deleteById(id);
    }
}
