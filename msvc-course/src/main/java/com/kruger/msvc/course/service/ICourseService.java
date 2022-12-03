package com.kruger.msvc.course.service;

import java.util.List;
import java.util.Optional;
import com.kruger.msvc.course.entity.Course;

public interface ICourseService {

	List<Course> findAll();
	Optional<Course> findById(Long id);
	Course save(Course course);
	void delete(Long id);
	
}
