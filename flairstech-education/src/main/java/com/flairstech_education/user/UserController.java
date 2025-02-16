package com.flairstech_education.user;

import com.flairstech_education.common.GenericResponse;
import com.flairstech_education.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    @GetMapping
//    public ResponseEntity<List<UserResponse>> getAll() {
//        return ResponseEntity.ok(userService.getAll());
//    }
    @GetMapping
    public ResponseEntity<PageResponse<UserResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        return ResponseEntity.ok(userService.getAll(page,size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<UserResponse>> getById(@PathVariable("id") Integer id) {
        UserResponse userResponse = userService.getById(id);
        if (userResponse != null) {
            return ResponseEntity.ok(GenericResponse.success(userResponse));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GenericResponse.error());
        }
    }

    @PostMapping
    public ResponseEntity<GenericResponse<Integer>> create(@RequestBody @Valid UserRequest userRequest) {
        int userId = userService.create(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(GenericResponse.success(userId));
    }

    @PutMapping()
    public ResponseEntity<GenericResponse<UserRequest>> update(@RequestBody  @Valid UserRequest userRequest) {
        int updatedUserId = userService.update(userRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(GenericResponse.response(userRequest, "Updated", true));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Integer>> delete(@PathVariable("id") Integer id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(GenericResponse.response(id, "Deleted", true));
    }
}
