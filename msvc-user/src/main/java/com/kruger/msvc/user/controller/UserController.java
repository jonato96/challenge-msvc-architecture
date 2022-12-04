package com.kruger.msvc.user.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.core.env.Environment;

import com.kruger.msvc.user.entity.User;
import com.kruger.msvc.user.service.IUserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private IUserService service;
	
	@Autowired
	private Environment env;
	
	@GetMapping("/port")
	public String status()
	{
		return "The mcsv: "+env.getProperty("spring.application.name")+", actual port number is: "+env.getProperty("server.port");
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){
		Optional<User> user = service.findById(id);
		if(user.isPresent()) {
			return ResponseEntity.ok(user);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	//@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
		if(result.hasErrors()) {
			return validate(result);
		}
		if(!user.getEmail().isEmpty() && service.findByEmail(user.getEmail()).isPresent()) {
			return ResponseEntity.badRequest()
					.body(Collections.singletonMap("Error", "Usuario existente con ese correo"));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
	}	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id){		
		if(result.hasErrors()) {
			return validate(result);
		}
		Optional<User> usuarioEncontrado = service.findById(id);
		if(usuarioEncontrado.isPresent()) {
			User usuarioModificar = usuarioEncontrado.get();
			if(!user.getEmail().equalsIgnoreCase(usuarioModificar.getEmail()) && service.findByEmail(user.getEmail()).isPresent()) {
				return ResponseEntity.badRequest()
						.body(Collections.singletonMap("Error", "Correo a modificar debe ser diferente"));
			}
			usuarioModificar.setNombre(user.getNombre());
			usuarioModificar.setEmail(user.getEmail());
			usuarioModificar.setPassword(user.getPassword());
			return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuarioModificar));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remove(@PathVariable Long id) {
		//validar si el usuario existe en la base
		Optional<User> usuarioBuscado = service.findById(id);
		if(usuarioBuscado.isPresent()) {
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
