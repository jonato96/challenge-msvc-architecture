package com.kruger.msvc.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kruger.msvc.user.entity.User;
import com.kruger.msvc.user.repository.UserRepository;

//Anotacion como un componente de servicio
@Service
public class UserService implements IUserService{

	//Inyectamos la interface con todos los metodos JPA
	@Autowired
	private UserRepository repository;
	
	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {		
		return (List<User>)repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findById(Long id) {		
		return repository.findById(id);
	}

	@Override
	@Transactional
	public User save(User usuario) {
		return repository.save(usuario);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public Optional<User> findByEmail(String email) {
		// TODO Auto-generated method stub
		return repository.findByEmail(email);
	}

}
