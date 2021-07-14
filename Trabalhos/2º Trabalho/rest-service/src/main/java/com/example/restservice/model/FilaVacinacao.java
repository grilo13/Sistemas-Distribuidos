package com.example.restservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="fila_vacinacao") //representa a tabela com os valores das filas de cada centro
public class FilaVacinacao {
	
	@Id
	@Column(name="idUtente")
	private long idUtente;
	
	@Column(name="idCentro")
	private long idCentro;	
	
	@Column(name="dataVacina")
	private String dataVacina;	

	public FilaVacinacao() {
		super();
	}

	public FilaVacinacao(long idUtente, long idCentro, String dataVacina) {
		this.idUtente = idUtente;
		this.idCentro = idCentro;
		this.dataVacina = dataVacina;
	}

	public String getDataVacina() {
		return dataVacina;
	}

	public void setDataVacina(String dataVacina) {
		this.dataVacina = dataVacina;
	}

	public long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(long idUtente) {
		this.idUtente = idUtente;
	}

	public long getIdCentro() {
		return idCentro;
	}

	public void setIdCentro(long idCentro) {
		this.idCentro = idCentro;
	}
	
	
	
	

}
