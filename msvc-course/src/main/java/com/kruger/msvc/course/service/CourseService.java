package com.kruger.msvc.course.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kruger.msvc.course.entity.Course;
import com.kruger.msvc.course.repository.CourseRepository;

@Service
public class CourseService implements ICourseService{

	@Autowired
	private CourseRepository repositorio;
	
	@Override
	@Transactional(readOnly = true)
	public List<Course> findAll() {
		return (List<Course>) repositorio.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Course> findById(Long id) {		
		return repositorio.findById(id);
	}

	@Override
	@Transactional
	public Course save(Course course) {		
		return repositorio.save(course);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		repositorio.deleteById(id);
		
	}

}
