package com.example.restservice.repository;

import java.util.List;

/*
 * Repositório com a lista de utentes vacinados
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.restservice.model.ListaVacinados;

public interface ListaVacinadosRepository extends JpaRepository<ListaVacinados, Long>{
	
	//select nome_vacina, data_vacinacao, 
	//count(nome_vacina) as numero_vacinas from lista_vacinacao group by nome_vacina, data_vacinacao;

	@Query(value="select nome_vacina, data_vacinacao, \n"
			+ "	count(nome_vacina) as numero_vacinas from lista_vacinacao group by nome_vacina, data_vacinacao"
			+ "", nativeQuery=true)
	List<String> listaVacinacao();
	
	
	//calcula tamanho da lista de estatisticas
	@Query(value="select count(*) from (select nome_vacina, data_vacinacao, count(nome_vacina) "
			+ "as numero_vacinas from lista_vacinacao group by nome_vacina, data_vacinacao) as numberOfRows;\n"
			+ "", nativeQuery=true)
	int tamanhoListaEstatistica();


}
