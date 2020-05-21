package com.uca.capas.modelo.domain;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(schema = "store", name = "cliente")
public class Cliente {

	@Id
	@GeneratedValue(generator="cliente_c_cliente_seq", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "cliente_c_cliente_seq", sequenceName = "store.cliente_c_cliente_seq", allocationSize = 1)
	@Column(name = "c_cliente")
	private Integer ccliente;
	
	@Column(name = "s_nombres")
	private String snombres;
	
	@Column(name = "s_apellidos")
	private String sapellidos;
	
	@NotNull(message = "El campo Fecha no puede quedar vacio")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "f_nacimiento")
	private Date fnacimiento;
	
	@Column(name = "b_activo")
	private Boolean bactivo;
	
	@JsonIgnore
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
	private List<Vehiculo> vehiculos;

	public Integer getCcliente() {
		return ccliente;
	}

	public void setCcliente(Integer ccliente) {
		this.ccliente = ccliente;
	}

	public String getSnombres() {
		return snombres;
	}

	public void setSnombres(String snombres) {
		this.snombres = snombres;
	}

	public String getSapellidos() {
		return sapellidos;
	}

	public void setSapellidos(String sapellidos) {
		this.sapellidos = sapellidos;
	}

	public Date getFnacimiento() {
		return fnacimiento;
	}

	public void setFnacimiento(Date fnacimiento) {
		this.fnacimiento = fnacimiento;
	}
	
	public Boolean getBactivo() {
		return bactivo;
	}

	public void setBactivo(Boolean bactivo) {
		this.bactivo = bactivo;
	}
	
	public List<Vehiculo> getVehiculos() {
		return vehiculos;
	}

	public void setVehiculoList(List<Vehiculo> vehiculoList) {
		this.vehiculos = vehiculoList;
	}

	//Delegate para conversion de fecha
	public String getFechaDelegate(){
		if(this.fnacimiento == null){
			return "";
		}
		else{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String shortdate = sdf.format(this.fnacimiento.getTime());
			return shortdate;
		}
	}
	
	//Delegate para activo o inactivo
	public String getBactivoDelegate(){
		if(this.bactivo == null){
			return "";
		}
		else{
			if(this.bactivo) return "ACTIVO";
			else return "INACTIVO";
		}
	}
	
	public String getNombreCompleto() {
		return snombres.concat(" ").concat(sapellidos);
	}
	
	public String getEdadDelegate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.fnacimiento);
		if (this.fnacimiento == null) return "";
		else {
			LocalDate localFnacimiento = LocalDateTime.ofInstant(cal.toInstant(), cal.getTimeZone().toZoneId()).toLocalDate();
			int edad = Period.between(localFnacimiento, LocalDate.now()).getYears();
			return new Integer(edad).toString();
		}
	}
	
	
	
}
