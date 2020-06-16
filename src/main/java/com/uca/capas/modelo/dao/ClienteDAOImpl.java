package com.uca.capas.modelo.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.uca.capas.modelo.domain.Cliente;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

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

	public List<Cliente> getClientesEstado(Boolean estado) {
		// Obtengo el objeto para crear las querys
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		// Obtenemos el objeto que representara a la consulta tipeado a la entidad
		// correspondiente
		CriteriaQuery<Cliente> query = cb.createQuery(Cliente.class);

		// Creo el objeto que me representa el FROM de la consulta, en este caso de la
		// entidad Clientes
		Root<Cliente> clientes = query.from(Cliente.class);

		// Creamos el WHERE, definido por el objeto Predicate y creamos las condiciones
		// auxiliandonos de los metodos
		// provistos por el objeto CriteriaBuilder
		Predicate predicate = cb.equal(clientes.get("bactivo"), estado);

		// Ahora construimos toda la consulta con cada objeto creado
		query.select(clientes).where(predicate);

		// Ejecutamos la consulta
		List<Cliente> resultado = entityManager.createQuery(query).getResultList();

		return resultado;
	}

	public List<Cliente> getClientesFechaNacimiento(Calendar fecha) {
		// Obtengo el objeto para crear las querys
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		// Obtenemos el objeto que representara a la consulta tipeado a la entidad
		// correspondiente
		CriteriaQuery<Cliente> query = cb.createQuery(Cliente.class);

		// Creo el objeto que me representa el FROM de la consulta, en este caso de la
		// entidad Clientes
		Root<Cliente> clientes = query.from(Cliente.class);

		// Creamos el WHERE, definido por el objeto Predicate y creamos las condiciones
		// auxiliandonos de los metodos provistos por el objeto CriteriaBuilder
		// En este caso buscaremos los clientes con fecha de nacimiento mayor o igual a la ingresada por parametro
		Predicate predicate = cb.greaterThan(clientes.get("fnacimiento"), fecha.getTime());

		// Ahora construimos toda la consulta con cada objeto creado
		query.select(clientes).where(predicate);

		// Ejecutamos la consulta
		List<Cliente> resultado = entityManager.createQuery(query).getResultList();

		return resultado;
	}

	public List<Cliente> getClientesFechaEstado(Calendar fecha, Boolean estado) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Cliente> query = cb.createQuery(Cliente.class);

		Root<Cliente> clientes = query.from(Cliente.class);

		// Como ahora tendremos dos condicionales utilizamos el metodo conjunction del
		// CriteriaBuilder
		// para crear un "conjunto" vacio inicialmente, y dentro del cual iremos
		// agregando las condiciones
		// con el metodo "and" del CriteriaBuilder
		Predicate predicate = cb.conjunction();

		// Vamos construyendo condicion por condicion
		predicate = cb.and(predicate, cb.greaterThan(clientes.get("fnacimiento"), fecha.getTime()));
		predicate = cb.and(predicate, cb.equal(clientes.get("bactivo"), estado));

		query.select(clientes).where(predicate);

		List<Cliente> resultado = entityManager.createQuery(query).getResultList();

		return resultado;
	}

	public List<Cliente> getClientesMarcaVehiculo(String marca) {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Cliente> query = cb.createQuery(Cliente.class);

		Root<Cliente> clientes = query.from(Cliente.class);

		// Navego hacia la entidad Vehiculo con el metodo "join" del objeto Root, que
		// recibe de parametro
		// el nombre de la propiedad a realizar el Join, y como segundo parametro
		// (opcional) el tipo de Join
		// definido en la enumeracion JoinType, si no se establece entonces por defecto
		// es INNER JOIN
		// Luego con el metodo get defino la propiedad por la cual se hara la condicion
		// ya en el contexto del vehiculo
		Predicate predicate = cb.equal(clientes.join("vehiculos").get("smarca"), marca);

		// Hago un distinct, ya que si un cliente tiene N vehiculos, vendran N clientes
		query.distinct(true).select(clientes).where(predicate);

		List<Cliente> resultado = entityManager.createQuery(query).getResultList();

		return resultado;
	}

}
