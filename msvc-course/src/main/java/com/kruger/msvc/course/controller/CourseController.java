package com.kruger.msvc.course.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kruger.msvc.course.entity.Course;
import com.kruger.msvc.course.service.ICourseService;

@RestController
@RequestMapping("/api/course")
public class CourseController {
	
	@Autowired
	private ICourseService service;
	
	@Autowired
	private Environment env;
	
	@GetMapping("/port")
	public String status()
	{
		return "The mcsv: "+env.getProperty("spring.application.name")+", actual port number is: "+env.getProperty("server.port");
	}
	
	@GetMapping
	public ResponseEntity<?> listar(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> listarPorId(@PathVariable Long id){
		Optional<Course> course = service.findById(id);
		if(course.isPresent()) {
			return ResponseEntity.ok(course);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	//@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid  @RequestBody Course course, BindingResult result) {
		if(result.hasErrors()) {
			return validate(result);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(course));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@Valid @RequestBody Course course, BindingResult result, @PathVariable Long id){
		if(result.hasErrors()) {
			return validate(result);
		}
		Optional<Course> cursoEncontrado = service.findById(id);
		if(cursoEncontrado.isPresent()) {
			Course cursoModificar = cursoEncontrado.get();
			cursoModificar.setName(course.getName());			
			return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoModificar));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remove(@PathVariable Long id) {
		//validar si el curso existe en la base
		Optional<Course> cursoBuscado = service.findById(id);
		if(cursoBuscado.isPresent()) {
			service.delete(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();		
	}
	
	private ResponseEntity<?> validate(BindingResult result) {
		Map<String, String> errores = new HashMap<>();
		result.getFieldErrors().forEach(error->{
			errores.put(error.getField(), "El campo: "+error.getField() +" "+error.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}


}
