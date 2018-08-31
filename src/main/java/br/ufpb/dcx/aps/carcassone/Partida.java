package br.ufpb.dcx.aps.carcassone;

import br.ufpb.dcx.aps.carcassone.tabuleiro.TabuleiroFlexivel;
import br.ufpb.dcx.aps.carcassone.tabuleiro.Tile;

public class Partida {

	private BolsaDeTiles tiles;
	private Tile proximoTile;
	private TabuleiroFlexivel tabuleiro = new TabuleiroFlexivel("  ");
	Estado estadoDoTurno = Estado.TILE_POSICIONADO;
	Estado estadoDaPartida;
	Jogador[] jogador;
	int indiceJogadorVez = 0;

	Partida(BolsaDeTiles tiles, Cor... sequencia) {
		this.tiles = tiles;
		pegarProximoTile();

		jogador = new Jogador[sequencia.length];
		for (int i = 0; i < sequencia.length; ++i) {
			jogador[i] = new Jogador(sequencia[i]);
		}

		estadoDaPartida = Estado.PARTIDA_ANDAMENTO;
		tabuleiro.adicionarPrimeiroTile(proximoTile);
	}

	public String relatorioPartida() {
		String relatorio = "";
		for (int i = 0; i < jogador.length - 1; i++) {
			relatorio += jogador[i].toString() + "; ";
		}
		relatorio += jogador[jogador.length - 1];

		return "Status: " + estadoDaPartida + "\nJogadores: " + relatorio;
	}

	public String relatorioTurno() {
		Jogador proximoJogador = jogador[indiceJogadorVez % jogador.length];
		String relatorio = "Jogador: " + proximoJogador.getCor() + "\nTile: " + proximoTile + "\nStatus: "
				+ estadoDoTurno;
		return relatorio;
	}

	public Partida girarTile() {
		proximoTile.girar();
		return this;
	}

	private void pegarProximoTile() {
		proximoTile = tiles.pegar();
		proximoTile.reset();
	}

	public Partida finalizarTurno() {
		pegarProximoTile();
		return this;
	}

	public Partida posicionarTile(Tile tileReferencia, Lado ladoTileReferencia) {
		verificarTilePosicionado();
		tabuleiro.posicionar(tileReferencia, ladoTileReferencia, proximoTile);
		return this;
	}

	public Partida posicionarMeepleEstrada(Lado lado) {
		return this;
	}

	public Partida posicionarMeepleCampo(Vertice vertice) {
		return this;
	}

	public Partida posicionarMeepleCidade(Lado lado) {
		return this;
	}

	public Partida posicionarMeepleMosteiro() {
		return this;
	}

	public String getEstradas() {
		return null;
	}

	public String getCampos() {
		return null;
	}

	public String getCidades() {
		return null;
	}

	public String getMosteiros() {
		return null;
	}

	public String relatorioTabuleiro() {
		return tabuleiro.toString();
	}

	public void verificarTilePosicionado() {
		if (estadoDoTurno == Estado.TILE_POSICIONADO) {
			throw new ExcecaoJogo("Não pode reposicionar tile já posicionado");
		}
	}
}