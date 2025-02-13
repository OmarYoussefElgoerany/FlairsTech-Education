package com.flairstech_education.course;

import com.flairstech_education.common.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class course extends BaseEntity {
    private String title;
    private String description;
    private String instructor;
    private int duration;
    private LocalDate startDate;
    private LocalDate endDate;
    private String category;
    private int capacity;
}
