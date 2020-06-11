package com.uca.capas.modelo.service;

import java.util.List;

import com.uca.capas.modelo.domain.Cliente;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface ClienteService {
	
	public List<Cliente> findAll() throws DataAccessException;

	public List<Cliente> findAll(Sort sort);

	public Page<Cliente> findAll(Pageable page) throws DataAccessException;

	public Long countAll();
	
	public Cliente findOne(Integer codigo) throws DataAccessException;

	public List<Cliente> getClientesQueryMethod(Integer tipo, String valor1, String valor2) throws Exception;
	
	public void save(Cliente c) throws DataAccessException;

	public List<Cliente> findAllClientes();

	public List<Cliente> findClientesNombreApel(String nombres, String apellidos);

	public Integer actualizarClientes(Integer cliente, Boolean estado);

}
