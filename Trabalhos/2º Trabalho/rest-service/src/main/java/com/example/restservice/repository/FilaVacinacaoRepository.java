package com.example.restservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.restservice.model.FilaVacinacao;

/*
 * Repositório das filas de vacinação
 */

@Repository
public interface FilaVacinacaoRepository extends JpaRepository<FilaVacinacao, Long>{
	
	//retorna número de pessoas que estão agendadas para o dia data_vacina no centro idCentro
	@Query(value="select count(*) from fila_vacinacao where data_vacina = :dataVacina and id_centro = :idCentro "
			+ ""
			+ ";", nativeQuery=true)
	String getVacinasParaDia(@Param("idCentro") long idCentro, @Param("dataVacina") String dataVacina);
	
	@Query(value="select vacinas_diarias from centros_vacinacao where id_centro = :idCentro"
			+ ""
			/*+ ";"*/, nativeQuery=true)
	String getVacinasCentro(@Param("idCentro") long idCentro);
	
	
	//retorna o id centro onde o utente está na fila de espera
	@Query(value="select id_centro from fila_vacinacao where id_utente = :idUtente"
			+ ""
			/*+ ";"*/, nativeQuery=true)
	String getFilaUtente(@Param("idUtente") long idUtente);
	
	//retorna a data da vacinação do utente (neste caso a pretendida)
	@Query(value="select data_vacina from fila_vacinacao where id_utente = :idUtente"
			+ ""
			/*+ ";"*/, nativeQuery=true)
	String getDataVacinaUtente(@Param("idUtente") long idUtente);
	
	
	//retorna lista de utentes que terão que reagendar vacina
	@Query(value="select id_utente  from fila_vacinacao where id_centro = :idCentro "
			+ "and data_vacina= :dataVacina order by id_utente desc limit :doses"
			+ ""
			/*+ ";"*/, nativeQuery=true)
	List<String> getUtentesReagendamento(@Param("idCentro") long idCentro, 
							   @Param("dataVacina") String dataVacina,
							   @Param("doses") long doses);
	
	//retorna os utentes em fila de espera (Nome)
	@Query(value="select id_Utente from fila_vacinacao natural join utentes_registados"
				+ ""
				/*+ ";"*/, nativeQuery=true)
	List<String> getIdUtentesListaEspera();


	//retorna os utentes em fila de espera (Nome)
	@Query(value="select nome_Utente from fila_vacinacao natural join utentes_registados"
			+ ""
			/*+ ";"*/, nativeQuery=true)
	List<String> getNomeUtentesListaEspera();
	
	//retorna os utentes em fila de espera (Nome)
	@Query(value="select count(*) from fila_vacinacao natural join utentes_registados"
			+ ""
			+ ""
			/*+ ";"*/, nativeQuery=true)
	String getTamanhoListaEspera();


}
