package com.example.restservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="utentesRegistados") //representa a tabela com os utentes que se registaram para vacinação
public class UtenteRegistado {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idUtente; 
	private String nomeUtente;
	private String email;
	private String dataPreferida;

	public UtenteRegistado() {
		super();
	}

	public UtenteRegistado(String nomeUtente, String email, String dataPreferida) {
		super();
		this.nomeUtente = nomeUtente;
		this.email = email;
		this.dataPreferida = dataPreferida;
	}


	public long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(long idUtente) {
		this.idUtente = idUtente;
	}

	public String getNomeUtente() {
		return nomeUtente;
	}

	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDataPreferida() {
		return dataPreferida;
	}

	public void setDataPreferida(String dataPreferida) {
		this.dataPreferida = dataPreferida;
	}


}
