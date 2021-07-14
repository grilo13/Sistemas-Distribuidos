package com.example.restservice.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class VacinasDisponiveisPrimaryKey implements Serializable{  
	
	//chave primária com dois valores para ser possivel representar as diversas doses para os 
	//diferentes dias de cada centro
	
	private long idCentro;
	private String dataVacina;
	
	public VacinasDisponiveisPrimaryKey() {
		super();
	}

	public VacinasDisponiveisPrimaryKey(long idCentro, String dataVacina) {
		super();
		this.idCentro = idCentro;
		this.dataVacina = dataVacina;
	}
	
	public long getIdCentro() {
		return idCentro;
	}
	public void setIdCentro(long idCentro) {
		this.idCentro = idCentro;
	}
	public String getDataVacina() {
		return dataVacina;
	}
	public void setDataVacina(String dataVacina) {
		this.dataVacina = dataVacina;
	}
	
	

}
