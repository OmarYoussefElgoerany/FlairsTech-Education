package com.flairstech_education.course;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public CourseResponse getById(Integer id){
        return courseRepository.findById(id)
                .map(courseMapper::toCourseResponse)
                .orElseThrow(() ->  new EntityNotFoundException("Course not found with id: " + id));
    }
    public int create(CourseRequest courseRequest){
        Course course = courseMapper.toCourse(courseRequest);
        return courseRepository.save(course).getId();
    }
    public int update(CourseRequest courseRequest){
        var id = courseRequest.id();
        Course getCourse = courseRepository.findById(id).orElseThrow(
                () ->  new EntityNotFoundException("Course not found with id: " + id)
        );

        var course = courseMapper.AddUpdatedCourseToCourse(courseRequest,getCourse);
        return courseRepository.save(course).getId();
    }
    public void delete(Integer id) {
        if (!courseRepository.existsById(id)) {
            throw new EntityNotFoundException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }

}
