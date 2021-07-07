package com.example.restservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="utentes") 
public class Utentes {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idUtente; 
	private String nomeUtente;
	private  int códigoRegisto;
	
	/*@OneToOne(mappedBy = "utentes", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ListaCentro lista;*/
	
	/*@OneToOne(mappedBy = "utentes", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ListaCentro centro;
	 @OneToOne(cascade = CascadeType.ALL)
	 @JoinColumn(name = "idUtente", referencedColumnName = "idUtente")
	 private ListaCentro centro;*/
	

	public Utentes() {
		super();
	}

	public Utentes(String nomeUtente, int códigoRegisto) {
		super();
		this.nomeUtente = nomeUtente;
		this.códigoRegisto = códigoRegisto;
	}

	public int getCódigoRegisto() {
		return códigoRegisto;
	}

	public String getNomeUtente() {
		return nomeUtente;
	}

	public long getIdUtente() {
		return idUtente;
	}

}
