package com.uca.capas.modelo.dao;

import java.util.Calendar;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.uca.capas.modelo.domain.Cliente;
import com.uca.capas.modelo.domain.Vehiculo;

public interface ClienteDAO {
	
	public List<Cliente> findAll() throws DataAccessException;
	
	public Cliente findOne(Integer codigo) throws DataAccessException;
	
	public void save(Cliente c) throws DataAccessException;

	public List<Cliente> getClientesEstado(Boolean estado);

	public List<Cliente> getClientesFechaNacimiento(Calendar fecha);

	public List<Cliente> getClientesMarcaVehiculo(String marca);

	public List<Cliente> getClientesFechaEstado(Calendar fecha, Boolean estado);

	/************** JdbcTemplate **** ************************/
	public void insertClienteNoAutoId(Cliente c);

	public int insertClienteAutoId(Cliente c);

	public void updateCliente(Cliente c);

	public int ejecutarProcedimiento(Integer cliente, Boolean estado);

	public int[][] batchInsertVehiculos(final List<Vehiculo> vehiculos);

}
