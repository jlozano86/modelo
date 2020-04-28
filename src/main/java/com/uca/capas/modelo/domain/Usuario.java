package com.uca.capas.modelo.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Usuario {
	
	@Size(min=1, max=25, message = "El campo usuario debe tener una longitud entre 1 y 25 caracteres.")
	String usuario;
	
	@NotEmpty(message = "El campo contrasenia no puede estar vacio.")
	String contrasenia;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

}
