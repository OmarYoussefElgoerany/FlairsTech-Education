package com.flairstech_education.course;

import com.flairstech_education.common.PageResponse;
import com.flairstech_education.user.User;
import com.flairstech_education.userCourse.UserCourse;
import com.flairstech_education.userCourse.UserCourseId;
import com.flairstech_education.userCourse.UserCourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final UserCourseRepository userCourseRepository;
    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper,
                         UserCourseRepository userCourseRepository){
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.userCourseRepository = userCourseRepository;
    }

//    public List<CourseResponse> getAll(){
//       return courseRepository.findAll().stream().map(courseMapper::toCourseResponse).toList();
//    }
    //PAGINATION
    public PageResponse<CourseResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Course> pg = courseRepository.findAll(pageable);
        List<CourseResponse> courseRespList = pg.stream().map(courseMapper::toCourseResponse)
                .toList();
        return PageResponse.<CourseResponse>builder()
                .content(courseRespList)
                .number(pg.getNumber())
                .size(pg.getSize())
                .totalElements(pg.getTotalElements())
                .totalPages(pg.getTotalPages())
                .first(pg.isFirst())
                .last(pg.isLast())
                .build();
//        return new PageResponse<>(
//                courseList,
//                pg.getNumber(),
//                pg.getSize(),
//                pg.getTotalElements(),
//                pg.getTotalPages(),
//                pg.isFirst(),
//                pg.isLast()
//        );
    }



    public CourseResponse getById(Integer id){
        return courseRepository.findById(id)
                .map(courseMapper::toCourseResponse)
                .orElseThrow(() ->  new EntityNotFoundException("Course not found with id: " + id));
    }
    public int create(CourseRequest courseRequest, Authentication connectedUser ){
        User usr = (User) connectedUser.getPrincipal();

        Course course = courseMapper.toCourse(courseRequest);

        int courseId = courseRepository.save(course).getId();
        var compositeId = new UserCourseId(usr.getId(),courseId);
        UserCourse userCourse = new UserCourse();
        userCourse.setId(compositeId);
        userCourse.setUser(usr);
        userCourse.setCourse(course);
        userCourse.setCreatedBy(courseRequest.createdBy());
        userCourseRepository.save(userCourse);
        return courseId;
    }
    public int saveFileToCourse(String filePath, Integer courseId){
        var course =  courseRepository.findById(courseId)
                .orElseThrow(() ->  new EntityNotFoundException("Course not found with id: " + courseId));
        course.setPhoto(filePath);
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
    public int patch(UpdateCourseRequest courseRequest){
        var id = courseRequest.id;
        Course getCourse = courseRepository.findById(id).orElseThrow(
                () ->  new EntityNotFoundException("Course not found with id: " + id)
        );
        getCourse.setPhoto(courseRequest.photo);
         var x = courseRepository.save(getCourse).getId();
        Course getCourse2 = courseRepository.findById(id).orElseThrow(
                () ->  new EntityNotFoundException("Course not found with id: " + id)
        );
         return  x;
    }
    public void delete(Integer id) {
        if (!courseRepository.existsById(id)) {
            throw new EntityNotFoundException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }
    public List<CourseResponse> searchByTitle(String title) {
        List<Course> courses = this.courseRepository.findByTitleContainingIgnoreCase(title);

        if (courses.isEmpty()) {
            throw new EntityNotFoundException("Course with title " + title + " is not found");
        }

        return courses.stream()
                .map(courseMapper::toCourseResponse)
                .collect(Collectors.toList());
    }
}
