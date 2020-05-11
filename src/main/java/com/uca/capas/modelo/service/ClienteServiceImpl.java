package com.uca.capas.modelo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uca.capas.modelo.dao.ClienteDAO;
import com.uca.capas.modelo.domain.Cliente;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
	ClienteDAO clienteDao;

	public List<Cliente> findAll() throws DataAccessException {
		return clienteDao.findAll();
	}

	public Cliente findOne(Integer codigo) throws DataAccessException {
		return clienteDao.findOne(codigo);
	}
	
	@Transactional
	public void save(Cliente c) throws DataAccessException {
		clienteDao.save(c);
	}

}
