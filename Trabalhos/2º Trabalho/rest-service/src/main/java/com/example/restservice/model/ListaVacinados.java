package com.example.restservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lista_vacinacao") //representa a tabela com os utentes já vacinados
public class ListaVacinados {
	
	@Id
	@Column(name="idUtente")
	private long idUtente;
	
	@Column(name="idCentro")
	private long idCentro;
	
	@Column(name="dataVacinacao")
	private String dataVacinacao;
	
	@Column(name="nomeVacina")
	private String nomeVacina;
	
	public ListaVacinados() {
		super();
	}

	public ListaVacinados(long idUtente, long idCentro, String dataVacinacao, String nomeVacina) {
		super();
		this.idUtente = idUtente;
		this.idCentro = idCentro;
		this.dataVacinacao = dataVacinacao;
		this.nomeVacina = nomeVacina;
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

	public String getDataVacinacao() {
		return dataVacinacao;
	}

	public void setDataVacinacao(String dataVacinacao) {
		this.dataVacinacao = dataVacinacao;
	}

	public String getNomeVacina() {
		return nomeVacina;
	}

	public void setNomeVacina(String nomeVacina) {
		this.nomeVacina = nomeVacina;
	}
}	