package com.uca.capas.modelo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.uca.capas.modelo.domain.Cliente;

/*
 * Esta anotacion le dice a Spring que este es un objeto DAO, por lo que sera
 * manejado automaticamente y posteriormente podremos obtener una instancia
 * de este objeto mediante inyeccion de depdencias (@Autowired)
 */
@Repository
public class ClienteDAOImpl implements ClienteDAO {
	
	/*
	 * Definimos el objeto EntityManager con el cual ejecutaremos
	 * consultas a la base de datos, para esto utilizamos la anotacion
	 * @PersistenceContext, al cual le definimos el nombre de la unidad
	 * de persistencia que le fue asignado en la clase JpaConfiguration (linea 21)
	 * con la propiedad unitName, con esto tenemos el objeto EntityManager
	 * de la base de datos definida en nuestra clase de configuracion de Jpa
	 */
	@PersistenceContext(unitName = "modelo-persistence")
	EntityManager entityManager;

	@Override
	public List<Cliente> findAll() throws DataAccessException {
		//Creamos un objeto StringBuffer para definir la consulta a ejecutar
		StringBuffer sb = new StringBuffer();
		//Definimos la consulta con el metodo append
		sb.append("select * from store.cliente");
		/*
		 * Declaramos un objeto de tipo javax.persistence.Query, el cual representa a la consulta
		 * Dicho objeto no lo instanciamos, sino que le asignamos lo que devuelve el metodo
		 * createNativeQuery del entityManager, el cual recibe dos parametros
		 * 1. La consulta de tipo String
		 * 2. La referencia de la clase a la que queremos mapear el resultado (Cliente)
		 */
		Query query = entityManager.createNativeQuery(sb.toString(), Cliente.class);

		/*
		 * Ejecutamos la consulta con el metodo getResultList() de nuestro objeto Query
		 * el cual devolvera una lista del tipo definido anteriormente (Cliente.class)
		 * y lo asignamos a una lista de tipo cliente
		 */
		List<Cliente> res = query.getResultList();
		//Devolvemos la lista con la coleccion de Clientes
		return res;
	}

	public Cliente findOne(Integer codigo) throws DataAccessException {
		/*
		 * Para obtener un cliente en base a su llave primaria nos auxiliaremos
		 * del metodo find del objeto EntityManager, el cual recibe de parametro la
		 * referencia de la clase sobre la cual queremos buscar la entidad, y como 
		 * segundo parametros el valor de la llave primaria, el cual es enviado como
		 * parametro en el metodo. Dicho metodo devolvera el objeto Cliente encontrado
		 * para esa llave primaria, sino lo encuentra devolverá NULL
		 */
		Cliente c = entityManager.find(Cliente.class, codigo);
		return c;
	}

	public void save(Cliente c) throws DataAccessException {
		
		if(c.getCcliente() == null) { //Si la propiedad de la llave primaria viene vacío, entonces es un INSERT
			entityManager.persist(c); //Utilizamos persist ya que es un INSERT
		}
		else { //Caso contrario, se busco al cliente, por lo que la propiedad ccliente viene llena (el input hidden del formulario)
			entityManager.merge(c); //Utilizamos merge ya que es un UPDATE
		}
		
	}

}
