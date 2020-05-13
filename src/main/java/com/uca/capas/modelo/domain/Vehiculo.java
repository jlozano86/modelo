package com.uca.capas.modelo.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(schema = "store", name = "vehiculo")
public class Vehiculo {
	
	@Id
	@GeneratedValue(generator="vehiculo_c_vehiculo_seq", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "vehiculo_c_vehiculo_seq", sequenceName = "store.vehiculo_c_vehiculo_seq")
	@Column(name = "c_vehiculo")
	private Integer cvehiculo;
	
	@Column(name = "s_marca")
	private String smarca;
	
	@Column(name = "s_modelo")
	private String smodelo;
	
	@Column(name = "s_chassis")
	private String schassis;
	
	@Column(name = "f_compra")
	@DateTimeFormat(pattern = "dd/mm/yyyy")
	private Calendar fcompra;
	
	@Column(name = "b_estado")
	private Boolean bestado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_cliente")
	private Cliente cliente;
	
	@Transient
	private Integer ccliente;

	public Integer getCvehiculo() {
		return cvehiculo;
	}

	public void setCvehiculo(Integer cvehiculo) {
		this.cvehiculo = cvehiculo;
	}

	public String getSmarca() {
		return smarca;
	}

	public void setSmarca(String smarca) {
		this.smarca = smarca;
	}

	public String getSmodelo() {
		return smodelo;
	}

	public void setSmodelo(String smodelo) {
		this.smodelo = smodelo;
	}

	public String getSchassis() {
		return schassis;
	}

	public void setSchassis(String schassis) {
		this.schassis = schassis;
	}

	public Calendar getFcompra() {
		return fcompra;
	}

	public void setFcompra(Calendar fcompra) {
		this.fcompra = fcompra;
	}

	public Boolean getBestado() {
		return bestado;
	}

	public void setBestado(Boolean bestado) {
		this.bestado = bestado;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public String getFechaDelegate(){
		if(this.fcompra == null){
			return "";
		}
		else{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String shortdate = sdf.format(this.fcompra.getTime());
			return shortdate;
		}
	}
	
	public Integer getCclienteDelegate() {
		return this.cliente == null ? null : this.cliente.getCcliente();
	}

	public Integer getCcliente() {
		return ccliente;
	}

	public void setCcliente(Integer ccliente) {
		this.ccliente = ccliente;
	}
	
	public String getEstadoDelegate() {
		if(this.bestado == null) return "";
		else return this.bestado == true ? "MATRICULADO" : "NO MATRICULADO";
	}
	
	
}
