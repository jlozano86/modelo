package com.uca.capas.modelo.repositories;

import java.util.Date;
import java.util.List;

import com.uca.capas.modelo.domain.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    //Este metodo devolvera una lista de clientes cuyo nombre sea IGUAL al enviado como parametro
    public List<Cliente> findBySnombres(String nombre);

    //Devuelve una lista de clientes cuyo nombre Y apellido sea IGUAL a los enviados como parametro
    public List<Cliente> findBySnombresAndSapellidos(String nombre, String apellido);

    //Devuelve una lista de clientes cuyo nombre cumpla con el criterio dado (desde el controlador)
    public List<Cliente> findBySnombresLike(String valor);

    //Devuelve los clientes que sean activos (propiedad booleana bactivo = True)
    //No recibe ningún parametro
    public List<Cliente> findByBactivoTrue();

    //Devuelve los clientes que no esten activos (propiedad boolean bactivo = False)
    //No recibe ningún parametro
    public List<Cliente> findByBactivoFalse();

    //Devuelve los clientes cuya fecha de nacimiento sea posterior a la dada como parametro
    public List<Cliente> findByFnacimientoAfter(Date fecha);

    //Devuelve los clientes cuya fecha de nacimiento se encuentre entre los rangos enviados como parametro
    //f_nacimiento >= fecha1 AND f_nacimiento <= fecha2
    //o tambien como: f_nacimiento BETWEEN fecha1 AND fecha2
    public List<Cliente> findByFnacimientoBetween(Date fecha1, Date fecha2);

    //Devuelve los clientes que tengan la coleccion de nombres enviados como parametro
    public List<Cliente> findBySnombresIn(List<String> nombres);

}
