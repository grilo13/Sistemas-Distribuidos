package com.example.restservice.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restservice.model.FilaVacinacao;
import com.example.restservice.model.ListaVacinados;
import com.example.restservice.model.NotificacaoUtente;
import com.example.restservice.model.UtenteRegistado;
import com.example.restservice.repository.FilaVacinacaoRepository;
import com.example.restservice.repository.ListaVacinadosRepository;
import com.example.restservice.repository.NotificacaoUtenteRepository;
import com.example.restservice.repository.UtentesRegistadosRepository;

@RestController
public class UtenteController {
	
	@Autowired  //ponto de injeção para o repositório para que seja possível a conexão bom a base de dados
	private UtentesRegistadosRepository utentesRegistados;
	
	@Autowired
	private NotificacaoUtenteRepository notificacaoUtente;
	
	@Autowired
	private FilaVacinacaoRepository filaVac;
	
	@Autowired
	private ListaVacinadosRepository listaVac;
	
	
	//insere utente com idutente no centro com idcentro na data pretendida
	@PostMapping("/utente/filaVacinacao")
	public FilaVacinacao adicionarFila(@RequestBody FilaVacinacao filavacinacao) {
		return filaVac.save(filavacinacao);
	}
		
	//retorna o utente registado com o id dado
	@GetMapping("/utenteRegistado/{idUtente}")
	public Optional<UtenteRegistado> utenteRegistadoSelecionado(@PathVariable(value="idUtente") long idUtente) {
		return utentesRegistados.findById(idUtente);
	}
		
		
	//numero de vacinas já agendadas para um dia especifico
	@GetMapping("/utente/filaVacinacao/{idCentro}/{dataVacina}")
	public Map<String, String> verFilasJáAgendadas(@PathVariable(value="idCentro") long idCentro,
			@PathVariable(value="dataVacina") String dataVacina) {
			
		HashMap<String, String> map = new HashMap<>();
		String numeroVacinas = filaVac.getVacinasParaDia(idCentro, dataVacina);
		String valor = filaVac.getVacinasCentro(idCentro);
			
		//primeiro argumento representa o numero de vacinas para o dia escolhido no centro escolhido
		//segundo o numero de vacinas total diario
		map.put("key", numeroVacinas);
		map.put("key2", valor);
		return map;
	}
	
	//retirar utente da fila de espera pois já foi vacinado
	@DeleteMapping("utente/filaVacinacao/removeFila/{idUtente}")
	public Map<String, String> removeUtente(@PathVariable(value="idUtente") long idUtente) {
		filaVac.deleteById(idUtente);
			
		String nome = utentesRegistados.retornaNomeUtente(idUtente);
			
		//não é necessário eliminar este registo 
		//utentesRegistados.deleteById(idUtente);
			
		HashMap<String, String> map = new HashMap<>();
		map.put("key", "Utente " + idUtente + " de nome "+ nome +" foi removido da fila de vacinação.");
			
		return map;
	}
	
	//coloca utente na lista de vacinados
	@PostMapping("/utente/listaVacinados")
	public ListaVacinados adicionarListaVacinacao(@RequestBody ListaVacinados listavac) {
		return listaVac.save(listavac);
	}
		
	//retorna o id do centro onde o utente está na fila de espera
	@GetMapping("/utente/filaVacinacao/centro/{idUtente}")
	public Map<String, String> getIdCentroFila(@PathVariable(value="idUtente") long idUtente) {
			
		HashMap<String, String> map = new HashMap<>();
		String idCentro = filaVac.getFilaUtente(idUtente);
		String dataVacina = filaVac.getDataVacinaUtente(idUtente);
			
		if(idCentro.equals("null")) {
			map.put("key", "Utente não está em nenhuma fila de espera");
		} else {
			map.put("IdCentro", idCentro);
			map.put("DataVacina", dataVacina);
		}
			
		return map;
	}
	
	//------------------ Notificações dos Utentes  --------------------------//
	//para o cliente ver se tem notificaçoes
	//se nao tiver retorna hash com valor que não tem notificacoes
	@GetMapping("/utente/notificacao/{idUtente}/{email}")
	public Map<String, String> NotificacaoUtente(@PathVariable(value="idUtente") long idUtente, @PathVariable(value="email") String email){
			
		String emailUtente = utentesRegistados.retornaEmailUtente(idUtente);
			
		HashMap<String, String> map = new HashMap<>();
			
		if(emailUtente.equals(email)) {
			if(notificacaoUtente.getTamanhoNotificacao(idUtente) == "0") {
				map.put("key", "Utente não tem notificações no momento.");
			} else {
				map.put("key", notificacaoUtente.getNotificacaoUtente(idUtente));
			}
				
		} else {
			map.put("key", "Os valores dados nao corresnpondem a um utente existente.");
		}
		return map;
	}
		
	//para modificar utentes registados e fazer nova introdução na fila de vacinação
	//tem que receber o utilizador antigo para modificar os valores da data nova
	@PutMapping("/utente/alterarData/{idUtente}")
	UtenteRegistado atualizaDataVacinacao(@RequestBody UtenteRegistado utente, @PathVariable Long idUtente) {
		
	      return utentesRegistados.findById(idUtente)
	          .map(utenteNovo -> {
	              utenteNovo.setDataPreferida(utente.getDataPreferida());
	              return utentesRegistados.save(utenteNovo);
	          })
	          .orElseGet(() -> {
	              return utentesRegistados.save(utente);
	          });
	  }
		
	//remove notificacao existente de um utente
	//na aplicação cliente vê se existe notificacão ou nao
	@DeleteMapping("utente/notificacao/remove/{idUtente}")
	public Map<String, String> removeNotificacaoUtente(@PathVariable(value="idUtente") long idUtente) {
		
		notificacaoUtente.deleteById(idUtente);
			
		HashMap<String, String> map = new HashMap<>();
			
		map.put("key", "Notificação lida por " + String.valueOf(idUtente));
			
		return map;
	}
		
		
	//para o centro enviar a notificação ao cliente a dizer que tem que reagendar a vacinação para outro dia
	@PostMapping("utente/notificacao")
	public NotificacaoUtente notificaCliente(@RequestBody NotificacaoUtente utente) {
		return notificacaoUtente.save(utente);
	}
		
	//registar novo utilizar para vacinacao
	//insere apenas na tabela de utentes registados
	//terá posteriomente de adicionar à tabela das filas de vacinação
	@PostMapping("/registaUtente")
	public UtenteRegistado adicionarUtente(@RequestBody UtenteRegistado utenteRegistado) {
		return utentesRegistados.save(utenteRegistado);
	}

}
