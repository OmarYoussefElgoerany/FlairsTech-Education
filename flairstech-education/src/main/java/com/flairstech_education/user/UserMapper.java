package com.flairstech_education.user;

import jakarta.persistence.Column;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserMapper {

    // Convert UserRequest DTO to User entity
    public User toUser(UserRequest userRequest) {
        return User.builder()
                .id(userRequest.id())  // Optional, if the id is provided
                .firstname(userRequest.firstname())
                .lastname(userRequest.lastname())
                .dateOfBirth(userRequest.dateOfBirth())
                .email(userRequest.email())
                .createdBy(userRequest.createdBy())
                .build();
    }

    // Convert UserRequest DTO to UserResponse DTO
    public UserResponse toUserResponse(UserRequest userRequest) {
        return UserResponse.builder()
                .id(userRequest.id())  // Optional, if the id is provided
                .firstname(userRequest.firstname())
                .lastname(userRequest.lastname())
                .dateOfBirth(userRequest.dateOfBirth())
                .email(userRequest.email())
                .build();
    }

    // Convert User entity to UserResponse DTO
    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .dateOfBirth(user.getDateOfBirth())
                .email(user.getEmail())
                .build();
    }
}
