package br.ufpb.dcx.aps.carcassone;

import br.ufpb.dcx.aps.carcassone.tabuleiro.TabuleiroFlexivel;
import br.ufpb.dcx.aps.carcassone.tabuleiro.Tile;

public class Partida {

	private BolsaDeTiles tiles;
	private Tile proximoTile;
	private TabuleiroFlexivel tabuleiro = new TabuleiroFlexivel("  ");
	Jogador[] jogador;
	int indiceJogadorVez = 0;
	String relatorio = "";
	Estado estadoDoTurno = Estado.TILE_POSICIONADO;
	Estado estadoDaPartida;

	public Partida(BolsaDeTiles tiles, Cor[] sequencia) {
		this.tiles = tiles;
		pegarProximoTile();

		jogador = new Jogador[sequencia.length];

		for (int i = 0; i < sequencia.length; ++i) {
			jogador[i] = new Jogador(sequencia[i]);
		}

		estadoDaPartida = Estado.PARTIDA_ANDAMENTO;
		tabuleiro.adicionarPrimeiroTile(proximoTile);
		verificarFimPartida();

	}

	public String relatorioPartida() {
		// verificarFimPartida();
		String sequencia = "";

		for (int i = 0; i < this.jogador.length; i++) {
			if (i == this.jogador.length - 1) {
				sequencia += this.jogador[i].toString();
			} else {
				sequencia += this.jogador[i].toString() + "; ";
			}
		}
		String relatorio = "Status: " + estadoDaPartida + "\nJogadores: " + sequencia;
		return relatorio;
	}

	public String relatorioTurno() {
		if (verificarFimDaPartida()) {
			throw new ExcecaoJogo("Partida finalizada");
		}
		Jogador proximoJogador = jogador[indiceJogadorVez % jogador.length];
		relatorio = "Jogador: " + proximoJogador.getCor() + "\nTile: " + proximoTile + "\nStatus: " + estadoDoTurno;
		return relatorio;
	}

	public Partida girarTile() {
		if (estadoDoTurno == Estado.TILE_POSICIONADO) {
			throw new ExcecaoJogo("Não pode girar tile já posicionado");
		}
		if (proximoTile == null) {
			estadoDaPartida = Estado.PARTIDA_FINALIZADA;
			throw new ExcecaoJogo("Não pode girar tiles com a partida finalizada");
		}
		proximoTile.girar();
		return this;

	}

	public Jogador proximoJogador() {
		return jogador[indiceJogadorVez % jogador.length];

	}

	private void pegarProximoTile() {
		proximoTile = tiles.pegar();
		if (proximoTile != null) {
			proximoTile.reset();
		} /*
			 * else { verificarFimPartida(); }
			 */
	}

	public Partida finalizarTurno() {
		pegarProximoTile();
		indiceJogadorVez++;
		estadoDoTurno = Estado.TURNO_INICIO;
		verificarFimDaPartida();
		return this;
	}

	public Partida posicionarTile(Tile tileReferencia, Lado ladoTileReferencia) {
		if (estadoDoTurno == Estado.TILE_POSICIONADO) {
			throw new ExcecaoJogo("Não pode reposicionar tile já posicionado");
		}
		tabuleiro.posicionar(tileReferencia, ladoTileReferencia, proximoTile);
		estadoDoTurno = Estado.TILE_POSICIONADO;
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

	public void verificarFimPartida() {
		if (proximoTile == null) {
			estadoDaPartida = Estado.PARTIDA_FINALIZADA;
			throw new ExcecaoJogo("Partida finalizada");
		} else {
			estadoDaPartida = Estado.PARTIDA_ANDAMENTO;
		}

	}

	public boolean verificarFimDaPartida() {
		if (proximoTile == null) {
			estadoDaPartida = Estado.PARTIDA_FINALIZADA;
			return true;
		}
		return false;
	}
}
