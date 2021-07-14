package com.example.restservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.restservice.model.NotificacaoUtente;

/*
 * Repositório com as notificações dos utentes
 */

@Repository
public interface NotificacaoUtenteRepository extends JpaRepository<NotificacaoUtente, Long>{
	
	//retorna a notificacao do utente
		@Query(value="select notificacao from notificacao_utente where id_utente = :idUtente"
				+ ""
				/*+ ";"*/, nativeQuery=true)
		String getNotificacaoUtente(@Param("idUtente") long idUtente);
		
	//retorna o count das linhas onde idutente
		@Query(value="select count(*) from notificacao_utente where id_utente= :idUtente"
				+ ""
				/*+ ";"*/, nativeQuery=true)
		String getTamanhoNotificacao(@Param("idUtente") long idUtente);


}
