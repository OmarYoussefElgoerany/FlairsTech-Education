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
                .createdBy(course.getCreatedBy())
                .build();
    }

    public Course toCourse(CourseRequest courseRequest) {
        return Course.builder()
                .category(courseRequest.category())
                .id(courseRequest.id())
                .duration(courseRequest.duration())
                .title(courseRequest.title())
                .startDate(courseRequest.startDate())
                .endDate(courseRequest.endDate())
                .instructor(courseRequest.instructor())
                .createdBy(courseRequest.createdBy())
                .build();
    }
    public Course AddUpdatedCourseToCourse(CourseRequest courseReq, Course courseToUpdate) {
        courseToUpdate.setCapacity(courseReq.capacity());
        courseToUpdate.setDuration(courseReq.duration());
        courseToUpdate.setDescription(courseReq.description());
        courseToUpdate.setCategory(courseReq.category());
        courseToUpdate.setTitle(courseReq.title());
        courseToUpdate.setInstructor(courseReq.instructor());
        courseToUpdate.setStartDate(courseReq.startDate());
        courseToUpdate.setEndDate(courseReq.endDate());
        courseToUpdate.setCreatedBy(courseReq.createdBy());
        return courseToUpdate;
    }
}
