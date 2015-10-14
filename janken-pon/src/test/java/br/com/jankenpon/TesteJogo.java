package br.com.jankenpon;

import org.junit.*;

import br.com.jankenpon.controller.Partida;
import br.com.jankenpon.enums.Sinal;
import br.com.jankenpon.pojo.Jogador;

import static org.junit.Assert.*;

public class TesteJogo {

	private Jogador jogadorUm;
	private Jogador jogadorDois;

	private Partida partida;

	@Before
	public void init() {
		jogadorUm = new Jogador("João");
		jogadorDois = new Jogador("Maria");

		partida = new Partida();
		partida.adicionarJogadores(jogadorUm, jogadorDois);
	}

	@Test
	public void jogadorUmVence() {
		partida.adicionarRodada(Sinal.LAGARTO, Sinal.SPOCK);
		partida.adicionarRodada(Sinal.LAGARTO, Sinal.PAPEL);
		partida.adicionarRodada(Sinal.LAGARTO, Sinal.LAGARTO);

		assertTrue(jogadorUm.getQtdeVitoria() > jogadorDois.getQtdeVitoria());
	}

	@Test
	public void jogadorDoisVence() {
		partida.adicionarRodada(Sinal.PEDRA, Sinal.PAPEL);
		partida.adicionarRodada(Sinal.SPOCK, Sinal.PAPEL);
		partida.adicionarRodada(Sinal.PAPEL, Sinal.PAPEL);

		assertTrue(jogadorDois.getQtdeVitoria() > jogadorUm.getQtdeVitoria());
	}

	@Test
	public void jogadoresEmpatam() {
		partida.adicionarRodada(Sinal.PEDRA, Sinal.PEDRA);
		partida.adicionarRodada(Sinal.SPOCK, Sinal.SPOCK);
		partida.adicionarRodada(Sinal.PAPEL, Sinal.PAPEL);

		assertTrue(jogadorUm.getQtdeEmpate() == partida.getQtdeRodada()
				&& jogadorUm.getQtdeEmpate() == jogadorDois.getQtdeEmpate());
	}
}
