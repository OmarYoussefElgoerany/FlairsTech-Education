package com.flairstech_education.course;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponse {
    private String title;
    private String description;
    private String instructor;
    private int duration;
    private String category;
    private String createdBy;
}
