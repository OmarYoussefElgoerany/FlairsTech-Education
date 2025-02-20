package com.flairstech_education.course;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseMapper {
    public CourseResponse toCourseResponse(Course course){
        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .instructor(course.getInstructor())
                .photo(course.getPhoto())
                .duration(course.getDuration())
                .category(course.getCategory())
                .createdBy(course.getCreatedBy())
                .build();
    }
//    public List<CourseResponse> toCourseResponseList(Course[] courses){
//        List<CourseResponse> list = new ArrayList<>();
//        for(Course c : courses){
//            list.add(toCourseResponse(c));
//        }
//        return list;
//    }


    public Course toCourse(CourseRequest courseRequest) {
        return Course.builder()
                .category(courseRequest.category())
                .id(courseRequest.id())
                .description(courseRequest.description())
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
