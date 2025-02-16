package com.flairstech_education.user;

import com.flairstech_education.common.PageResponse;
import com.flairstech_education.course.Course;
import com.flairstech_education.course.CourseResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

//    // Get all users
//    public List<UserResponse> getAll() {
//        return userRepository.findAll()
//                .stream()
//                .map(userMapper::toUserResponse)
//                .toList();
//    }
public PageResponse<UserResponse> getAll(int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
    Page<User> pg = userRepository.findAll(pageable);
    List<UserResponse> userRespList = pg.stream().map(userMapper::toUserResponse)
            .toList();
    return PageResponse.<UserResponse>builder()
            .content(userRespList)
            .number(pg.getNumber())
            .size(pg.getSize())
            .totalElements(pg.getTotalElements())
            .totalPages(pg.getTotalPages())
            .first(pg.isFirst())
            .last(pg.isLast())
            .build();
//        return new PageResponse<>(
//                courseList,
//                pg.getNumber(),
//                pg.getSize(),
//                pg.getTotalElements(),
//                pg.getTotalPages(),
//                pg.isFirst(),
//                pg.isLast()
//        );
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
