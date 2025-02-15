package com.flairstech_education.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // Get all users
    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    // Get user by id
    public UserResponse getById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    // Create a new user
    public int create(UserRequest userRequest) {
        User user = userMapper.toUser(userRequest);
        return userRepository.save(user).getId();
    }

    // TO DOO FIX UPDATE LOGIC
    public int update(UserRequest userRequest) {
        var id = userRequest.id();
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        user = userMapper.toUser(userRequest);
        return userRepository.save(user).getId();
    }

    // Delete a user
    public void delete(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
