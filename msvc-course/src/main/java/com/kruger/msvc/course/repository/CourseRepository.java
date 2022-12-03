package com.kruger.msvc.course.repository;

import org.springframework.data.repository.CrudRepository;
import com.kruger.msvc.course.entity.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {

}
