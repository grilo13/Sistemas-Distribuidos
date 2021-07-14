package com.example.restservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restservice.model.VacinasDisponiveis;
import com.example.restservice.repository.FilaVacinacaoRepository;
import com.example.restservice.repository.ListaVacinadosRepository;
import com.example.restservice.repository.VacinasDisponiveisRepository;

@RestController
public class DgsController {
	
	@Autowired
	private ListaVacinadosRepository listaVac;
	
	@Autowired
	private VacinasDisponiveisRepository vacinasDisp;
	
	@Autowired
	private FilaVacinacaoRepository filaVac;
	
	//retorna lista de vacinados por tipo de vacina e dia
	@GetMapping("/dgs/estatisticaVacinados")
	public Map<String, String> estatisticasVacinados() {
			
		HashMap<String, String> map = new HashMap<>();
			
		int tamanho_query = listaVac.tamanhoListaEstatistica();
		map.put("0", String.valueOf(tamanho_query));
		List<String> list = listaVac.listaVacinacao();
			
			
		for(int i = 0; i < list.size(); i++) {
			map.put(String.valueOf(i+1), list.get(i));
		}
			
		return map;
	}
	
	//adiciona na tabela de doses disponiveis o número de doses que a dgs disponibiliza ao centro
	//num determinado dia
	@PostMapping("/dgs/doses")
	public VacinasDisponiveis adicionarDosesVacinas(@RequestBody VacinasDisponiveis vacinasDisponiveis) {
		return vacinasDisp.save(vacinasDisponiveis);
	}
	
	//retorna todos os valores da tabela de vacinas disponiveis
	@GetMapping("/dgs")
	public List<VacinasDisponiveis> ver() {
		return vacinasDisp.findAll();
	}
		
	/*retorna o número de doses disponiveis para o centro idCentro no dia data
	*para efeitos de verificar se existem mais utentes registados que doses disponiveis
	*retorna também uma string verificar que irá dizer a aplicacao se deve ou nao
	*avisar um utente que dever reagendar a sua vacinação (envinado notificacao posterior)
	*/
	@GetMapping("/dgs/doses/{idCentro}/{data}")
	public Map<String, String> dosesParaCentro(@PathVariable(value="idCentro") long idCentro, @PathVariable(value="data") String data){
			
		HashMap<String,String> map = new HashMap<>();
			
		List<String> lista_reagendamentos;
			
		String dosesExistentes = vacinasDisp.getDosesDisponiveisCentro(idCentro, data);
			
		map.put("Doses", dosesExistentes);
			
		//retorna também o número de utentes agendados para esse dia
		String utentesAgendados = filaVac.getVacinasParaDia(idCentro, data);
			
		map.put("Agendados", utentesAgendados);
			
		if(Integer.parseInt(utentesAgendados) > Integer.parseInt(dosesExistentes)) {
			int doses = Integer.parseInt(utentesAgendados) - Integer.parseInt(dosesExistentes);
			lista_reagendamentos = filaVac.getUtentesReagendamento(idCentro, data, doses);
			map.put("tamanho", String.valueOf(lista_reagendamentos.size()));
				
			for(int i = 0; i < lista_reagendamentos.size(); i++) {
				map.put(String.valueOf(i), lista_reagendamentos.get(i));
				//filaVac.deleteById(Long.parseLong(lista_reagendamentos.get(i)));
			}
			map.put("verificar", "Dosesmal");
		} else {
			map.put("verificar", "Dosesbem");
		}
		
		return map;
	}

}
