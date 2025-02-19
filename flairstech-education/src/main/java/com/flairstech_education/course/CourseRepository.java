package com.flairstech_education.course;

import com.flairstech_education.common.GenericResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
    Optional<CourseResponse> findByTitle(String title);
}
