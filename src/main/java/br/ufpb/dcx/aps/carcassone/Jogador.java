package br.ufpb.dcx.aps.carcassone;

public class Jogador {
	// Classe que guarda a pontuação do jogador a cor que ele tem e quantos empeles
	// ele ainda tem
	private Cor corDoJogador;
	private int pontosDoJogador = 0;
	private int meeplesDoJogador = 7;

	public Jogador(Cor corDoJogador) {
		this(corDoJogador, 0, 7);
	}

	public Jogador(Cor corDoJogador, Integer pontosDoJogador, Integer meeplesDoJogador) {
		this.corDoJogador = corDoJogador;
		this.pontosDoJogador = pontosDoJogador;
		this.meeplesDoJogador = meeplesDoJogador;
	}

	public Cor getCor() {
		return corDoJogador;
	}

	public int getPontos() {
		return pontosDoJogador;
	}

	public int quantidadeMeeples() {
		return meeplesDoJogador;
	}

	public void tirarMeeple() {
		--meeplesDoJogador;
	}

	public void reset() {
		meeplesDoJogador = 7;
		pontosDoJogador = 0;
	}

	public String toString() {
		return corDoJogador + "(" + pontosDoJogador + "," + meeplesDoJogador + ")";
	}
}