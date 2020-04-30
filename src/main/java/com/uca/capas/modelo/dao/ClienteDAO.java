package com.uca.capas.modelo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.uca.capas.modelo.domain.Cliente;

public interface ClienteDAO {
	
	public List<Cliente> findAll() throws DataAccessException;
	
	public Cliente findOne(Integer codigo) throws DataAccessException;

}
