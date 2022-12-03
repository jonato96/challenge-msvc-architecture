package com.kruger.msvc.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Entity
@Table(name="users")//Es opcional, pero se coloca por convencion en plural
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//la base genera el id de forma automatica e incremental
	private Long id;
	
	//@Column(name = "nombre") cuando tienen el mismo nombre se omite
	@NotEmpty
	private String nombre;
	
	@Column(unique = true)//El correo debe ser unico
	@Email
	@NotBlank
	private String email;
	
	@NotEmpty
	private String password;
}
