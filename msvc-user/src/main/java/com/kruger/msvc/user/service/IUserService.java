package com.kruger.msvc.user.service;

import java.util.List;
import java.util.Optional;

import com.kruger.msvc.user.entity.User;

public interface IUserService {

	List<User> findAll();
	Optional<User> findById(Long id);
	User save(User usuario);
	void delete(Long id);
	
	Optional<User> findByEmail(String email);
	
}
