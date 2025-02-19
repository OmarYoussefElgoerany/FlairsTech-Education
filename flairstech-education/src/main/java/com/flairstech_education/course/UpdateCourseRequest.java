package com.flairstech_education.course;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCourseRequest {
    @NotNull(message = "Id cannot be null")
    @NotEmpty(message = "Id Cannot be empty")
    Integer id;
    String title;
    String description;
    String instructor;
    int duration;
    LocalDate startDate;
    LocalDate endDate;
    String category;
    String photo;
    int capacity;
    String createdBy;
}
