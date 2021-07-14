package com.example.restservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.restservice.model.VacinasDisponiveis;
import com.example.restservice.model.VacinasDisponiveisPrimaryKey;

/*
 * Repositório das vacinas disponiveis para cada centro
 */

@Repository
public interface VacinasDisponiveisRepository extends JpaRepository<VacinasDisponiveis, VacinasDisponiveisPrimaryKey>{
	
	//retorna número de vacinas disponiveis para o idCentro na data especifica
	@Query(value="select doses_vacina from vacinas_disponiveis where id_centro= :idCentro and data_vacina = :dataVacina"
			+ ""
			/*+ ";"*/, nativeQuery=true)
	String getDosesDisponiveisCentro(@Param("idCentro") long idCentro, @Param("dataVacina") String dataVacina);

}
