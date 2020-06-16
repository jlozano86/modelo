package com.uca.capas.modelo.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.uca.capas.modelo.domain.Cliente;

public interface ClienteDAO {
	
	public List<Cliente> findAll() throws DataAccessException;
	
	public Cliente findOne(Integer codigo) throws DataAccessException;
	
	public void save(Cliente c) throws DataAccessException;

	public List<Cliente> getClientesEstado(Boolean estado);

	public List<Cliente> getClientesFechaNacimiento(Calendar fecha);

	public List<Cliente> getClientesMarcaVehiculo(String marca);

	public List<Cliente> getClientesFechaEstado(Calendar fecha, Boolean estado);

}
