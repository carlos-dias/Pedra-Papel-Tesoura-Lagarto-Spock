package br.com.jankenpon.controller;

import br.com.jankenpon.enums.Sinal;
import br.com.jankenpon.pojo.Jogador;

public class Partida {
	private Jogador jogadorUm, jogadorDois;
	private Integer qtdeRodada;
	private String resultado;

	public Partida() {
		this.qtdeRodada = 0;
	}

	public void adicionarJogadores(Jogador jogadorUm, Jogador jogadorDois) {
		this.jogadorUm = jogadorUm;
		this.jogadorDois = jogadorDois;
	}

	public void adicionarRodada(Sinal sinalJogadorUm, Sinal sinalJogadorDois) {
		qtdeRodada++;
		verificarJogada(sinalJogadorUm, sinalJogadorDois);
	}

	private void verificarJogada(Sinal sinalJogadorUm, Sinal sinalJogadorDois) {
		if (sinalJogadorUm.ganha(sinalJogadorDois)) {
			jogadorUm.addQtdeVitoria();
			jogadorDois.addQtdeDerrota();

			resultado = "Rodada " + qtdeRodada + " - " + jogadorUm.getNome() + " venceu!";
		} else if (sinalJogadorUm.perde(sinalJogadorDois)) {
			jogadorUm.addQtdeDerrota();
			jogadorDois.addQtdeVitoria();

			resultado = "Rodada " + qtdeRodada + " - " + jogadorDois.getNome() + " venceu!";
		} else {
			jogadorUm.addQtdeEmpate();
			jogadorDois.addQtdeEmpate();

			resultado = "Rodada " + qtdeRodada + " - Empate!";
		}
	}

	public String getResultado() {
		return resultado;
	}

	public Integer getQtdeRodada() {
		return qtdeRodada;
	}

}
