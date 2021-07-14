package com.example.restservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.restservice.model.UtenteRegistado;

/*
 * Repositório para os utentes registados
 */

@Repository
public interface UtentesRegistadosRepository extends JpaRepository<UtenteRegistado, Long>{
	
	//query que retorna todos os valores da fila de vacinação
	@Query(value="select * from fila_vacinacao", nativeQuery=true)
	Object getAllFromFila_Vacinacao();
	
	//query que retorna o nome do utente com o id = 1
	@Query(value="select nome_utente from utentes_registados natural join fila_vacinacao where id_utente=1"
			+ "", nativeQuery=true)
	Object getNomeUtente();
	
	@Query(value = "insert into fila_vacinacao (idCentro, idUtente) values (:idCentro, :idUtente)",
			nativeQuery = true)
	void insertFilaVacinacao(@Param("idCentro") long idCentro, @Param("idUtente") long idUtente);
	
	
	//retorna nome do utente com o id dado
	@Query(value="select nome_utente from utentes_registados where id_utente=:idUtente"
			+ "", nativeQuery=true)
	String retornaNomeUtente(@Param("idUtente") long idUtente);
	
	//retorna email do utente com o id dado
	@Query(value="select email from utentes_registados where id_utente=:idUtente"
			+ "", nativeQuery=true)
	String retornaEmailUtente(@Param("idUtente") long idUtente);
}
