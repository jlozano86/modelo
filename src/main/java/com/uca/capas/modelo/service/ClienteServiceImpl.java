package com.uca.capas.modelo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uca.capas.modelo.dao.ClienteDAO;
import com.uca.capas.modelo.domain.Cliente;
import com.uca.capas.modelo.repositories.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	ClienteDAO clienteDao;

	@Autowired
	ClienteRepository clienteRepository;

	public List<Cliente> findAll() throws DataAccessException {
		return clienteRepository.findAll();
	}

	public Cliente findOne(Integer codigo) throws DataAccessException {
		return clienteRepository.getOne(codigo);
	}

	@Transactional
	public void save(Cliente c) throws DataAccessException {
		clienteDao.save(c);
	}

	@Override
	public List<Cliente> getClientesQueryMethod(Integer tipo, String valor1, String valor2) throws Exception {
		List<Cliente> resultado = null;

		// Este objeto me servira para convertir los Strings en Date para los casos
		// correspondientes
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		switch (tipo) {
			case 1:
				resultado = clienteRepository.findBySnombres(valor1);
				break;

			case 2:
				resultado = clienteRepository.findBySnombresAndSapellidos(valor1, valor2);
				break;

			// Para este caso buscaremos aquellos clientes que cumplan con WHERE S_NOMBRES
			// LIKE '%' + valor1 + '%'
			case 3:
				resultado = clienteRepository.findBySnombresLike("%" + valor1 + "%");
				break;

			case 4:
				resultado = clienteRepository.findByBactivoTrue();
				break;

			case 5:
				resultado = clienteRepository.findByBactivoFalse();
				break;

			// Aca tengo que convertir el String que viene en formato dd/mm/yyyy en Date, ya
			// que eso es lo que recibe el método
			case 6:
				Date fecha1 = sdf.parse(valor1);
				resultado = clienteRepository.findByFnacimientoAfter(fecha1);
				break;

			case 7:
				Date fecha2 = sdf.parse(valor1);
				Date fecha3 = sdf.parse(valor2);
				resultado = clienteRepository.findByFnacimientoBetween(fecha2, fecha3);
				break;

			// Para este caso, crearemos una lista quemada con nombres que son enviados al
			// metodo
			case 8:
				List<String> nombres = new ArrayList<String>();
				nombres.add("Bryan");
				nombres.add("Del");
				nombres.add("Cordula");
				resultado = clienteRepository.findBySnombresIn(nombres);
				break;

			default:
				resultado = new ArrayList<>();
				break;

		}
		return resultado;
	}

	@Override
	public List<Cliente> findAllClientes() {
		return clienteRepository.findAllClientes();
	}

	@Override
	public List<Cliente> findClientesNombreApel(String nombres, String apellidos) {
		return clienteRepository.findClientesNombreApel(nombres, apellidos);
	}

	public Page<Cliente> findAll(Pageable page) throws DataAccessException {
		return clienteRepository.findAll(page);
	}

	@Override
	public Long countAll() {
		return clienteRepository.count();
	}

}
