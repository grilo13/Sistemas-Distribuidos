package com.example.restservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="notificacao_utente") //representa a tabela das notificacoes do utente
public class NotificacaoUtente {
	
	@Id
	@Column(name="idUtente")
	private long idUtente;
	
	@Column(name="notificacao")
	private String notificacao;
	
	public NotificacaoUtente() {
		super();
	}

	public NotificacaoUtente(long idUtente, String notificacao) {
		super();
		this.idUtente = idUtente;
		this.notificacao = notificacao;
	}
	
	public long getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(long idUtente) {
		this.idUtente = idUtente;
	}
	public String getNotificacao() {
		return notificacao;
	}
	public void setNotificacao(String notificacao) {
		this.notificacao = notificacao;
	}
	
	

}
