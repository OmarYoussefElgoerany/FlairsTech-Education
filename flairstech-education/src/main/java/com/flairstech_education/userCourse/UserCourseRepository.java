package com.flairstech_education.userCourse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCourseRepository  extends JpaRepository<UserCourse,UserCourseId>{

}
