package com.flairstech_education.file;

import com.flairstech_education.course.CourseService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("files")
public class FileController {
    public final FileStorageService fileStorageService;
    public final CourseService courseService;
    @PostMapping(value = "/{course-id}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadFile(

            @PathVariable("course-id") int courseId,
            @Parameter()
            @RequestPart("file") MultipartFile file
    ){
        System.out.println("====================");
        System.out.println("====================");
        System.out.println("====================");
        System.out.println("====================");
        var filePath = fileStorageService.saveFile(file, courseId);
        courseService.saveFileToCourse(filePath,courseId);
        return ResponseEntity.accepted().build();
    }
}
