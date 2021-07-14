package com.example.restservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.restservice.model.CentroVac;

/*
 * Repositório dos centros de vacinção 
 */

@Repository
public interface CentrosVacinacaoRepository extends JpaRepository<CentroVac, Long>{

	CentroVac findById(long id);
	
	//retorna o id do centro onde o utente vai ser vacinado
	@Query(value="select id_centro from fila_vacinacao where id_utente=:idUtente"
			+ "", nativeQuery=true)
	String retornaIdCentro(@Param("idUtente") long idUtente);

}
