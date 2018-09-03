package br.ufpb.dcx.aps.carcassone;

import br.ufpb.dcx.aps.carcassone.tabuleiro.TabuleiroFlexivel;
import br.ufpb.dcx.aps.carcassone.tabuleiro.Tile;

public class Partida {

	private BolsaDeTiles tiles;
	private Tile proximoTile;
	private TabuleiroFlexivel tabuleiro = new TabuleiroFlexivel("  ");
	Estado estadoDoTurno = Estado.TILE_POSICIONADO; // Variável responsável pelo estado em que o turno se encontra
	Estado estadoDaPartida; // Variável responsável pelo estado da partida se encontra
	Jogador[] jogadores; // Vetor com os jogadores da partida
	int indiceDoJogador = 0; // Índice que representa o jogador da vez

	/*
	 * Construtor que inicializa a partida atribuindo a cada jogar sua respectiva
	 * cor, atribuindo o estado da partida e adicionando ao tabuleiro a primeira
	 * peça do jogo, pegando a próximo tile para o jogador.
	 */

	Partida(BolsaDeTiles tiles, Cor... sequencia) {
		this.tiles = tiles;
		pegarProximoTile(); // Função que pega o próximo tile na bolça de tiles
		addCorJogador(sequencia); // funcao que atribui cores aos jogadores
		estadoDaPartida = Estado.PARTIDA_ANDAMENTO; // mudando o estado da partida
		tabuleiro.adicionarPrimeiroTile(proximoTile); // dicionando ao tabuleiro a peça do jogo

	}

	/*
	 * Função que retorna o estado da partida, as cores dos jogadores
	 */
	public String relatorioPartida() {
		String relatorio = "Status: " + estadoDaPartida + "\nJogadores: " + relatorioJogador();

		return relatorio;
	}

	// Função que retorna a cor do jogador o tilo colocado e o estado
	public String relatorioTurno() {
		verificarFimDaPartida();
		Jogador proximoJogador = jogadores[indiceDoJogador % jogadores.length];
		// String que retorna o relatório do tudo
		String relatorio = "Jogador: " + proximoJogador.getCor() + "\nTile: " + proximoTile + "\nStatus: "
				+ estadoDoTurno;
		return relatorio;
	}
	/*
	 * Função que gira o Tile, colocando ele na para o norte, essa função também faz
	 * as verificações de estado do turno e estado da partida, podendo retorna
	 * exceções, se não ocorrer execuções o próximo tátil vai ser girado
	 */

	public Partida girarTile() {
		// Verificação de estado do turno.
		if (estadoDoTurno == Estado.TILE_POSICIONADO) {
			throw new ExcecaoJogo("Não pode girar tile já posicionado");
		}
		// Verificação para o estado da partida.
		if (proximoTile == null) {
			estadoDaPartida = Estado.PARTIDA_FINALIZADA;
			throw new ExcecaoJogo("Não pode girar tiles com a partida finalizada");
		}
		// Função de girar tile.
		proximoTile.girar();
		return this;
	}

	/*
	 * Função de pegar o próximo tile, atribuindo a um novo objeto o tile anterior
	 * fazendo uma verificação se ele não é nulo, utilizando da função reset que
	 * recola o tile virado para o norte
	 */
	private void pegarProximoTile() {
		proximoTile = tiles.pegar();
		if (proximoTile != null) {
			proximoTile.reset();
		}
	}

	/*
	 * Função de finalização de turno, dando ao próximo jogador um novo tile,
	 * adicionando ao índice da vez para mostrar que é um novo jogador, mudando o
	 * estado do turno e verificando se a partida acabou.
	 */
	public Partida finalizarTurno() {
		pegarProximoTile();
		indiceDoJogador++;
		estadoDoTurno = Estado.TURNO_INICIO;
		verificaTileNulo();
		return this;
	}

	/*
	 * Função que tem grande responsabilidade de verificar o estado do turno, se o
	 * tile foi posicionado no tabuleiro, e posicionar no tabuleiro o tile do
	 * jogador.
	 */
	public Partida posicionarTile(Tile tileReferencia, Lado ladoTileReferencia) {
		verificarTilePosicionado(); // verificando estado do turno
		tabuleiro.posicionar(tileReferencia, ladoTileReferencia, proximoTile); // posicionando tile no tabuleiro
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

	// Função que retorna um texto com toda a informação do tabuleiro atual
	public String relatorioTabuleiro() {
		return tabuleiro.toString();
	}
	// Função que retorna os atributos de cada jogador, varrendo o vetor dos
	// jogadores.

	public String relatorioJogador() {
		String sequencia = "";
		for (int i = 0; i < jogadores.length - 1; i++) {
			sequencia += jogadores[i].toString() + "; ";
		}
		sequencia += jogadores[jogadores.length - 1];
		return sequencia;
	}
	// Função que atribuir a cor a cada jogador

	public void addCorJogador(Cor... sequencia) {
		jogadores = new Jogador[sequencia.length];
		for (int i = 0; i < sequencia.length; ++i) {
			jogadores[i] = new Jogador(sequencia[i]);
		}
	}

	// Função de verificação de peça nula
	public void verificaTileNulo() {
		if (proximoTile == null) {
			estadoDaPartida = Estado.PARTIDA_FINALIZADA;
		}
	}

	// Função de verificação se o estado do turno já tem uma peça posicionada.
	public void verificarTilePosicionado() {
		if (estadoDoTurno == Estado.TILE_POSICIONADO) {
			throw new ExcecaoJogo("Não pode reposicionar tile já posicionado");
		}
	}

	// Função de verificação do fim da partida.
	public void verificarFimDaPartida() {
		if (proximoTile == null) {
			estadoDaPartida = Estado.PARTIDA_FINALIZADA;
			throw new ExcecaoJogo("Partida finalizada");
		}
	}
}