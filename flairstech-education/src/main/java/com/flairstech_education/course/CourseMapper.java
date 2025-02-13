package com.flairstech_education.course;

import org.springframework.stereotype.Service;

@Service
public class CourseMapper {
    public CourseResponse toCourseResponse(Course course){
        return CourseResponse.builder()
                .title(course.getTitle())
                .description(course.getDescription())
                .instructor(course.getInstructor())
                .duration(course.getDuration())
                .category(course.getCategory())
                .build();
    }
}
