package com.flairstech_education.course;

import com.flairstech_education.common.BaseEntity;
import com.flairstech_education.userCourse.UserCourse;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class Course extends BaseEntity {
    private String title;
    private String description;
    private String instructor;
    private int duration;
    private LocalDate startDate;
    private LocalDate endDate;
    private String category;
    private int capacity;
    private String photo;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserCourse> userCourses = new HashSet<>();
//
//    @ManyToMany(mappedBy = "courses") // Non-owning side
//    private Set<User> users = new HashSet<>();
}
