package com.uca.capas.modelo.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.uca.capas.modelo.domain.Cliente;
import com.uca.capas.modelo.domain.Vehiculo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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

	/**
	 * Definimos el objeto JdbcTemplate y lo inyectamos con la anotacion Autowired
	 */
	@Autowired
	JdbcTemplate jdbcTemplate;

	private static final String sql1 = "SELECT * FROM store.cliente WHERE c_cliente = :cliente";

	private static final String sql2 = "UPDATE store.cliente SET s_nombres = ?, s_apellidos = ?, f_nacimiento = ?, b_activo = ? WHERE c_cliente = ?";

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

	@Override
	public void insertClienteNoAutoId(Cliente c) {
		// Clase que depende de JdbcTemplate que permite realizar inserts a nivel de
		// JDBC
		// La sentencia INSERT no se especifica como tal, sino que el catalogo/table se
		// definen
		// con el metodo withSchemaName y withTableName de dicho objeto
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withSchemaName("store")
				.withTableName("cliente");

		// Como el usuario no sabe que llave primaria debe llevar el registro lo
		// obtenemos de la secuencia
		Integer secuencia = jdbcTemplate.queryForObject("SELECT NEXTVAL(\'store.cliente_c_cliente_seq\')",
				Integer.class);

		// Defino en un mapa de tipo <String, Object> los nombres de las columnas y los
		// respectivos
		// valores con que se realizara el insert
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("c_cliente", secuencia);
		parametros.put("s_nombres", c.getSnombres());
		parametros.put("s_apellidos", c.getSapellidos());
		parametros.put("f_nacimiento", c.getFnacimiento());
		parametros.put("b_activo", c.getBactivo());

		jdbcInsert.execute(parametros);

	}

	@Override
	public int insertClienteAutoId(Cliente c) {
		// Clase que permite realizar inserts a nivel de JDBC
		// Se le especifica el esquema, la tabla y la columna (pk) que es autogenerada
		// por el DBMS
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withSchemaName("store")
				.withTableName("cliente").usingGeneratedKeyColumns("c_cliente");

		// Defino en un mapa de tipo <String, Object> los nombres de las columnas y los
		// respectivos
		// valores con que se realizara el insert
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("s_nombres", c.getSnombres());
		parametros.put("s_apellidos", c.getSapellidos());
		parametros.put("f_nacimiento", c.getFnacimiento());
		parametros.put("b_activo", c.getBactivo());

		// El metodo executeAndReturnKey devuelve la llave primaria generada en el
		// insert
		Number id_generated = jdbcInsert.executeAndReturnKey(parametros);

		return id_generated.intValue();

	}

	@Override
	public void updateCliente(Cliente c) {
		// Array de objeto que contiene los parametros en el orden en que estan
		// definidos en el statement (variable sql2)
		Object[] parametros = new Object[] { c.getSnombres(), c.getSapellidos(), c.getFnacimiento(), c.getBactivo(),
				c.getCcliente() };

		// Metodo update del jdbcTemplate que recibe de parametros:
		// 1. SQL a ejecutar (update)
		// 2. Parametros como object array (Object[])
		jdbcTemplate.update(sql2, parametros);

	}

	@Override
	public int ejecutarProcedimiento(Integer cliente, Boolean estado) {
		// Esta clase permite ejecutar procedimientos almacenados a partir de un
		// JdbcTemplate
		// Recibe de parametros el objeto JdbcTemplate, el esquema y el procedimiento
		// con los parametros withSchemaName y withProcedureName
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("store")
			.withProcedureName("sp_actualizar_cliente")
			.withoutProcedureColumnMetaDataAccess();

		// Se registran los parametros en el objeto correspondiente
		// Si es parametro de entrada -> new SqlParameter
		// Si es parametro de salida -> new SqlOutParameter
		jdbcCall.addDeclaredParameter(new SqlParameter("p_cliente", Types.INTEGER));
		jdbcCall.addDeclaredParameter(new SqlParameter("p_estado", Types.BOOLEAN));
		jdbcCall.addDeclaredParameter(new SqlOutParameter("p_salida", Types.INTEGER));

		// Creamos un mapa con los nombres de los parametros y sus valores
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("p_cliente", cliente);
		parametros.put("p_estado", estado);

		// Al ejecutar el procedimiento devolvera un Map<String, Object>
		// con los N parametros de salida
		Map<String, Object> out = jdbcCall.execute(parametros);

		return Integer.parseInt(out.get("p_salida").toString());

	}

	public int[][] batchInsertVehiculos(final List<Vehiculo> vehiculos) {
		String sql = "INSERT INTO store.vehiculo "
				+ "(c_vehiculo, s_marca, s_modelo, s_chassis, f_compra, b_estado, c_cliente) VALUES (?,?,?,?,?,?,?)";

		int[][] resultado = jdbcTemplate.batchUpdate(sql, vehiculos, 1000,
				new ParameterizedPreparedStatementSetter<Vehiculo>() {

					@Override
					public void setValues(PreparedStatement ps, Vehiculo v) throws SQLException {
						ps.setInt(1, v.getCvehiculo());
						ps.setString(2, v.getSmarca());
						ps.setString(3, v.getSmodelo());
						ps.setString(4, v.getSchassis());
						java.sql.Date fcompra = new java.sql.Date(v.getFcompra().getTime().getTime());
						ps.setDate(5, fcompra);
						ps.setBoolean(6, v.getBestado());
						ps.setInt(7, v.getCcliente());
					}

				});

		return resultado;
	}

}
