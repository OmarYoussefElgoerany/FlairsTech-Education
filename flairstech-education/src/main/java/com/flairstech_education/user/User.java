package com.flairstech_education.user;

import com.flairstech_education.common.BaseEntity;
import com.flairstech_education.course.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "_user")
public class User extends BaseEntity {
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    @Column(unique = true)
    private String email;
    private String password;
    @ManyToMany
            @JoinTable(
                    name = "user_courses",
                    joinColumns = @JoinColumn(name = "course_Id"),
                    inverseJoinColumns = @JoinColumn(name = "user_Id")
            )
    private Set<Course> courses = new HashSet<>();
}
