package com.flairstech_education.course;

import com.flairstech_education.common.GenericResponse;
import com.flairstech_education.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("courses")
public class CourseController {
    private final CourseService courseService;
    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

//    @GetMapping()
//    public ResponseEntity<List<CourseResponse>> getAll(){
//        return ResponseEntity.ok(courseService.getAll());
//    }
    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<CourseResponse>> getById(@PathVariable("id") Integer id) {
            CourseResponse courseResponse = courseService.getById(id);

        if (courseResponse != null) {
            return ResponseEntity.ok(GenericResponse.success(courseResponse));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GenericResponse.error());
        }
    }
    //Pagination
    @GetMapping()
    public ResponseEntity<PageResponse<CourseResponse>> getAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(courseService.getAll(page,size));
    }
    @PostMapping
    public ResponseEntity<GenericResponse<Integer>> create(@RequestBody  @Valid CourseRequest courseRequest) {
        int courseId = courseService.create(courseRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(GenericResponse.success(courseId));
    }

    @PutMapping()
    public ResponseEntity<GenericResponse<Integer>> update(
            @RequestBody  @Valid CourseRequest courseRequest) {
        int updatedCourseId = courseService.update(courseRequest);
        return ResponseEntity.ok(GenericResponse.response(updatedCourseId,
                "updated!",true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Integer>> delete(@PathVariable("id") Integer id) {
        courseService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                GenericResponse.response(id,"Deleted", true)
        ); // Returning null as there's no data
    }
}
