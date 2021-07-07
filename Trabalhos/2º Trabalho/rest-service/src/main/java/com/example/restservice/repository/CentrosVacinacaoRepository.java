package com.example.restservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.restservice.model.CentroVac;
import com.example.restservice.model.Utentes;

@Repository
public interface CentrosVacinacaoRepository extends JpaRepository<CentroVac, Long>{

	CentroVac findById(long id);
	
	@Modifying
	@Query(
	  value = 
	    "insert into lista_centro (idCentro, idUtente) values (:idCentro, :idUtente)",
	  nativeQuery = true)
	
	Utentes insertUser(@Param("idCentro") long idCentro, @Param("idUtente") long idUtente);

}
