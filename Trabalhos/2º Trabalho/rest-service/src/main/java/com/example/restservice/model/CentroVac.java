package com.example.restservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="centros_vacinacao") 
public class CentroVac {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idCentro;
	private String nomeCentro;
	
	/*@OneToOne(mappedBy = "centrovac", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ListaCentro lista;*/

	public CentroVac() {
		super();
	}

	public CentroVac(String nomeCentro) {
		super();
		this.nomeCentro = nomeCentro;
	}

	public long getIdCentro() {
		return idCentro;
	}

	public String getNomeCentro() {
		return nomeCentro;
	}

}
