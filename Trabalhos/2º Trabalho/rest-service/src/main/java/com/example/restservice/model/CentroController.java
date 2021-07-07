package com.example.restservice.model;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restservice.repository.CentrosVacinacaoRepository;
import com.example.restservice.repository.UtentesRepository;

@RestController
public class CentroController {
	
	@Autowired  //ponto de injeção para o repositório para que seja possível a conexão bom a base de dados
	private UtentesRepository utentes;
	
	@Autowired
	private CentrosVacinacaoRepository centrosVac;
	
	
	//retorna a lista dos centros de vacinacao existentes
	@GetMapping("/centrosDisponiveis")
	public List<CentroVac> getCentrosDisponiveis() {
		return centrosVac.findAll();
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
	
	//retorna os utentes existentes para vacinação
	@GetMapping("/utentesExistentes")
	public List<Utentes> listaUtentes() { 
		return utentes.findAll();
	}
	
	//retorna apenas o utente com o id selecionado
	@GetMapping("/utente/{id}")
	public Optional<Utentes> utenteSelecionado(@PathVariable(value="id") long id) {
		return utentes.findById(id);
	}
	
	//adicionar um determinado utente, recebe um id para salver
	@PostMapping("/utente")
	public Utentes adicionarUtente(@RequestBody Utentes utente) {
		return utentes.save(utente);
	}
	
	@PostMapping("/listaCentro")
	public Utentes adicionarListaVacinados(@RequestBody Utentes utente, @RequestBody CentroVac centro) {
		return centrosVac.insertUser(utente.getIdUtente(), centro.getIdCentro());
	}
	
	/*@GetMapping("/centrosDisponiveis")
	public List<CentroVac> getCentrosDisponiveis() {
		return centrosDisponiveis;
	}*
	
	@PostMapping("/centrosDisponiveis")
	public CentroVac adicionaCentro(@RequestBody CentroVac centro) {
		return centro;
	}
			
	
	/*private List<Movie> movies = List.of(
            new Movie("1", new Director("John", "Wick"), true),
            new Movie("2", new Director("Mary", "Poppins"), false),
            new Movie("3", new Director("Jack", "Sparrow"), true),
            new Movie("4", null, false));
    
	

    @GetMapping("/movies")
    public List<Movie> getMovies() {
        return movies;
    }

    @GetMapping("/movies/{id}")
    public Movie getMovie(@PathVariable String id) {
        return movies
                .stream()
                .filter(s -> s.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    @PostMapping("/movies")
    public Movie addMovie(@RequestBody Movie movie) {
        return movie;
    }*/

}
