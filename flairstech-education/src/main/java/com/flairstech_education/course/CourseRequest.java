package com.flairstech_education.course;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record CourseRequest(
        Integer id,
        @NotNull(message = "Title cannot be null")
        @Size(min = 3, max = 100, message = "Title must be between 5 and 100 characters")
        @NotEmpty(message = "Title Cannot be empty")
        String title,
        @NotNull(message = "Description cannot be null")
        @Size(min = 5, max = 200, message = "Description must be between 5 and 100 characters")
        @NotEmpty(message = "Description Cannot be empty")
        String description,
        @NotNull(message = "instructor cannot be null")
        @NotEmpty(message = "instructor Cannot be empty")
        String instructor,
        @Length(min = 5, max = 120)
        int duration,
        LocalDate startDate,
        LocalDate endDate,
        String category,
        int capacity,
        String createdBy
) {

}
