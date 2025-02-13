package com.flairstech_education.course;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper){
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public List<CourseResponse> getAll(){
        return courseRepository.findAll()
                .stream().map(courseMapper::toCourseResponse)
                .toList();
    }
}
