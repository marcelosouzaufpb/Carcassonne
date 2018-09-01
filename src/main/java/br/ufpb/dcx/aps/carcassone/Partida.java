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
		atribuirCorAJogador(sequencia);
		estadoDaPartida = Estado.PARTIDA_ANDAMENTO;
		tabuleiro.adicionarPrimeiroTile(proximoTile);

	}

	public String relatorioPartida() {
		String relatorio = "Status: " + estadoDaPartida + "\nJogadores: " + relatorioJogador();
		return relatorio;
	}

	public String relatorioTurno() {
		verificarFimDaPartida();
		Jogador proximoJogador = jogador[indiceJogadorVez % jogador.length];
		String relatorio = "Jogador: " + proximoJogador.getCor() + "\nTile: " + proximoTile + "\nStatus: "
				+ estadoDoTurno;
		return relatorio;
	}

	public Partida girarTile() {
		if (estadoDoTurno == Estado.TILE_POSICIONADO) {
			throw new ExcecaoJogo("Não pode girar tile já posicionado");
		}
		proximoTile.girar();
		return this;
	}

	private void pegarProximoTile() {
		proximoTile = tiles.pegar();
		if (proximoTile != null) {
			proximoTile.reset();
		}
	}

	public Partida finalizarTurno() {
		pegarProximoTile();
		indiceJogadorVez++;
		estadoDoTurno = Estado.TURNO_INICIO;
		verificaTileNulo();
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

	public String relatorioJogador() {
		String sequencia = "";
		for (int i = 0; i < jogador.length - 1; i++) {
			sequencia += jogador[i].toString() + "; ";
		}
		sequencia += jogador[jogador.length - 1];
		return sequencia;
	}

	public void atribuirCorAJogador(Cor... sequencia) {
		jogador = new Jogador[sequencia.length];
		for (int i = 0; i < sequencia.length; ++i) {
			jogador[i] = new Jogador(sequencia[i]);
		}
	}

	public void verificaTileNulo() {
		if (proximoTile == null) {
			estadoDaPartida = Estado.PARTIDA_FINALIZADA;
		}
	}

	public void verificarTilePosicionado() {
		if (estadoDoTurno == Estado.TILE_POSICIONADO) {
			throw new ExcecaoJogo("Não pode reposicionar tile já posicionado");
		}
	}

	public void verificarFimDaPartida() {
		if (proximoTile == null) {
			estadoDaPartida = Estado.PARTIDA_FINALIZADA;
			throw new ExcecaoJogo("Partida finalizada");
		}
	}
}