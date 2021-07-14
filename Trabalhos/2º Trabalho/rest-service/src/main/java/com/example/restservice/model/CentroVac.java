package com.example.restservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="centros_vacinacao") //representa os centros de vacinação existentes
public class CentroVac {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idCentro;
	private String nomeCentro;
	private int vacinasDiarias;
	
	
	public CentroVac() {
		super();
	}

	public CentroVac(String nomeCentro, int vacinasDiarias) {
		super();
		this.nomeCentro = nomeCentro;
		this.vacinasDiarias = vacinasDiarias;
	}

	public long getIdCentro() {
		return idCentro;
	}

	public String getNomeCentro() {
		return nomeCentro;
	}
	

	public void setIdCentro(long idCentro) {
		this.idCentro = idCentro;
	}

	public void setNomeCentro(String nomeCentro) {
		this.nomeCentro = nomeCentro;
	}

	public int getVacinasDiarias() {
		return vacinasDiarias;
	}

	public void setVacinasDiarias(int vacinasDiarias) {
		this.vacinasDiarias = vacinasDiarias;
	}
	
	

}
