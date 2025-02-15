package com.flairstech_education.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserRequest (
    Integer id,
    @NotNull(message = "FirstName cannot be null")
    @Size(min = 3, max = 100, message = "Title must be between 5 and 100 characters")
    @NotEmpty(message = "FirstName Cannot be empty")
    String firstname,
    @NotNull(message = "LastName cannot be null")
    @Size(min = 3, max = 100, message = "Title must be between 5 and 100 characters")
    @NotEmpty(message = "LastName Cannot be empty")
    String lastname,
    LocalDate dateOfBirth,
    @NotNull(message = "Email cannot be null")
    @Size(min = 3, max = 100, message = "Title must be between 5 and 100 characters")
    @NotEmpty(message = "Email Cannot be empty")
    @Email
    String email,
    String createdBy){
}
