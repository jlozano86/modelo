package com.uca.capas.modelo.service;

import java.text.ParseException;
import java.util.List;

import com.uca.capas.modelo.dto.ClienteDTO;
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

	/***************Clase 21 **************************************************/
	public List<ClienteDTO> getClienteEstado(Integer estado);

	public List<ClienteDTO> getClienteFechaEstado(String fecha, Integer estado) throws ParseException;

	public List<ClienteDTO> getClienteFecha(String fecha) throws ParseException;

	public List<ClienteDTO> getClienteMarca(String marca);
	/************************************************************************* */

	public void insertClienteNoAutoId(Cliente c);

	public int insertClienteAutoId(Cliente c);

	public void updateCliente(Cliente c);

	public int cambiarEstadoVehiculos(Integer cliente, Boolean estado);

	public int[][] cargaMasiva() throws ParseException;

	public void delete(Cliente c);

}
