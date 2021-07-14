package com.example.restservice.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="vacinas_disponiveis") //representa as doses que o centro vai receber para um dia especifico
public class VacinasDisponiveis {  //chave primária com 2 valores -> vacDpkey -> idCentro e dataVacina
	
	
	//composite key---- idCentro and dataVacina
	@EmbeddedId
	private VacinasDisponiveisPrimaryKey vacDpkey;
	
	private int dosesVacina;
	
	public VacinasDisponiveis() {
		super();
	}

	public VacinasDisponiveis(VacinasDisponiveisPrimaryKey vacDpkey, int dosesVacina) {
		super();
		this.vacDpkey = vacDpkey;
		this.dosesVacina = dosesVacina;
	}

	public VacinasDisponiveisPrimaryKey getVacDpkey() {
		return vacDpkey;
	}

	public void setVacDpkey(VacinasDisponiveisPrimaryKey vacDpkey) {
		this.vacDpkey = vacDpkey;
	}

	public int getDosesVacina() {
		return dosesVacina;
	}

	public void setDosesVacina(int dosesVacina) {
		this.dosesVacina = dosesVacina;
	}
	
	
	
}
	