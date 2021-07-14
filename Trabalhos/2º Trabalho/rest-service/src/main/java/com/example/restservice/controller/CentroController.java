package com.example.restservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restservice.model.CentroVac;
import com.example.restservice.model.FilaVacinacao;
import com.example.restservice.repository.CentrosVacinacaoRepository;
import com.example.restservice.repository.FilaVacinacaoRepository;
import com.example.restservice.repository.UtentesRegistadosRepository;

@RestController
public class CentroController {
	
	@Autowired  //ponto de injeção para o repositório para que seja possível a conexão bom a base de dados
	private UtentesRegistadosRepository utentesRegistados;
	
	@Autowired
	private CentrosVacinacaoRepository centrosVac;
	
	@Autowired
	private FilaVacinacaoRepository filaVac;

	
	//retorna todos os valores da tabela fila_vacinacao, que representa os utentes na fila de espera de um centro
	@GetMapping("/filasDisponiveis")
	public List<FilaVacinacao> getFilaCentro() {
		return filaVac.findAll();
	}
	
	//retorna a lista dos centros de vacinacao existentes
	@GetMapping("/centrosDisponiveis")
	public List<CentroVac> getCentrosDisponiveis() {
		return centrosVac.findAll();
	}
	
	//número de centros existentes
	@GetMapping("/numeroCentros")
	public long getNumeroCentrosCentro() {
		return centrosVac.count();
	}
	
	//retorna apenas um centro de vacinacao pelo id
	@GetMapping("/centro/{id}")
	public CentroVac getCentrosDisponiveis(@PathVariable(value="id") long id){
		return centrosVac.findById(id);
	}
	
	//adicionar um novo centro de vacinação disponivel para começar a vacinação
	@PostMapping("/centro")
	public CentroVac adicionarUtente(@RequestBody CentroVac centro) {
		return centrosVac.save(centro);
	}
	
	//todos os utentes registados nas filas de vacinacao
	@GetMapping("/utente/fila/")
	public void adicionarUtente() {
		utentesRegistados.getAllFromFila_Vacinacao();
	}
	
	//nome de todos os utentes na fila
	@GetMapping("/utente/nafila")
	public Object getNomeUtente() {
		return utentesRegistados.getNomeUtente();
	}	
 
}
