package br.ufpb.dcx.aps.carcassone.teste;

import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.AMARELO;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.VERMELHO;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.PRETO;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.AZUL;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.LESTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.NORTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.OESTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.NORDESTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.NOROESTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.SUDESTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.SUDOESTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.SUL;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t01;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t02;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t06;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t07;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t08;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t11;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t14;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t19;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t20;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t21;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t22;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t24;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t30;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t31;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t34;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t36;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t39;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t42;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t64;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t72;
import static br.ufpb.dcx.aps.carcassone.teste.Assertiva.ocorreExcecao;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ufpb.dcx.aps.carcassone.BolsaDeTiles;
import br.ufpb.dcx.aps.carcassone.ExcecaoJogo;
import br.ufpb.dcx.aps.carcassone.Jogo;
import br.ufpb.dcx.aps.carcassone.Partida;
import br.ufpb.dcx.aps.carcassone.tabuleiro.Tile;

public class CidadeTeste extends JogoTest {

	private Jogo jogo;
	private BolsaDeTiles tiles;

	@Before
	public void novoJogo() {
		tiles = mock(BolsaDeTiles.class);
		jogo = new Jogo();
	}

	/**
	 * Caso de Teste 00 Posicionar um Meeple em uma Cidade.
	 * 
	 * Esse caso de teste se preocupa em adicionar um meeple na peça inicial do jogo,
	 *Fazendo verificações de posicionamento e de contagem de pontos.

	 */

	@Test
	public void posicionarMeepleEmCidade() {
		mockarTiles(tiles, t30);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();

		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,7); VERMELHOR(0,7)");
		partida.posicionarMeepleCidade(NORDESTE);
		verificarRelatorioTurno(partida, "AMARELO", "30N", "MEEPLE_POSICIONADO");
		partida.finalizarTurno();

		verificarRelatorioPartida(partida, "Partida_Finalizada", "AMARELO(1,6); VERMELHOR(0,7)");
		Assert.assertEquals("30(NO-AMARELO,NE-AMARELO)", partida.getCidades());
		verificarRelatorioTabuleiro(partida, "30N");

	}

	/**
	 * Caso de Teste 01 Posicionar um Meeple onde não tem cidade.
	 * Esse caso como anterior de teste se preocupa em adicionar um 
	 * meeple em um local onde não a cidade
	 * 
	 */
	@Test
	public void posicionarMeepleCidadeSemCidade() {
		mockarTiles(tiles, t30);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();

		Assert.assertEquals("30(NO,NE)", partida.getCidades());

		ocorreExcecaoJogo(() -> partida.posicionarMeepleCidade(NOROESTE),
				"Impossível posicionar meeple em cidade pois o lado leste do tile 30 é Estrada");

		ocorreExcecaoJogo(() -> partida.posicionarMeepleCidade(NORDESTE),
				"Impossível posicionar meeple em cidade pois o lado oeste do tile 30 é Estrada");

	}

	/**
	 * Caso de Teste 02 Posicionar um Meeple onde não tem cidade.
	 * 
	 * Esse caso de teste  como anterior de teste se 
	 * preocupa em adicionar um meeple em um local onde não a cidade, a diferença desse
	 *  caso para o anterior é que ele tem 2 tiles, um tile a mais que o anterior .
	 */
	@Test
	public void posicionarMeepleCidadeSemCidade2() {
		mockarTiles(tiles, t30, t11);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();

		partida.posicionarTile(t30, NORTE);
		partida.finalizarTurno();

		Assert.assertEquals("11(NO,NE) 11(SO,SE) 30(NO,NE)", partida.getCidades());

		ocorreExcecaoJogo(() -> partida.posicionarMeepleCidade(NOROESTE),
				"Impossível posicionar meeple em cidade pois o lado leste do tile 11 é Campo");

		ocorreExcecaoJogo(() -> partida.posicionarMeepleCidade(SUDOESTE),
				"Impossível posicionar meeple em cidade pois o lado oeste do tile 11 é Campo");

	}

	/**
	 * Caso de teste 03 Cidade com 2 tiles e um meeple cidade.
	 *
	 * Esse caso de teste se preocupa com o resultado final da partida
	 */

	@Test
	public void cidaeComDoisTilesMeeple() {
		mockarTiles(tiles, t30, t11);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();

		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,7); VERMELHOR(0,7);");

		partida.posicionarTile(t30, NORTE);
		partida.finalizarTurno();

		Assert.assertEquals("11(NO,NE) 11(SO,SE) 30(NO,NE)", partida.getCidades());

		partida.posicionarMeepleCidade(SUDESTE);
		verificarRelatorioTurno(partida, "VERMELHO", "11S", "MEEPLE_POSICIONADO");
		partida.finalizarTurno();

		verificarRelatorioPartida(partida, "Partida_Finalizada", "AMARELO(2,7); VERMELHOR(0,7);");
		verificarRelatorioTabuleiro(partida, "11S\n30N");

	}

	/**
	 * Caso de tese 04 Verificar cidade antes e depois de colocar um Meeple em uma
	 * cidade.
	 */

	@Test
	public void verificarCidade() {
		mockarTiles(tiles, t30, t11);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();

		Assert.assertEquals("30(NO,NE)", partida.getCidades());
		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,7); VERMELHOR(0,7);");

		partida.posicionarMeepleCidade(NORDESTE);
		verificarRelatorioTurno(partida, "AMARELO", "30N", "MEEPLE_POSICIONADO");
		partida.finalizarTurno();

		Assert.assertEquals("30(NO-AMARELO,NE-AMARELO)", partida.getCidades());
		partida.posicionarTile(t30, NORTE);
		partida.finalizarTurno();
		Assert.assertEquals("11(NO,NE) 11(SO,SE) 30(NO-AMARELO,NE-AMARELO)", partida.getCidades());

		verificarRelatorioPartida(partida, "Partida_Finalizada", "AMARELO(3,6); VERMELHOR(2,7);");
		verificarRelatorioTabuleiro(partida, "11S\n30N");

	}

	/**
	 * Caso de tese 05 Verificar cidades antes e depois de colocar um Meeple em uma
	 * cidade.
	 * 
	 * Esse caso de teste faz a verificacao para 2 jogadores o anterior so fazia
	 * para um jogador.
	 */

	@Test
	public void verificaCidade2() {
		mockarTiles(tiles, t30, t11);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();

		Assert.assertEquals("30(NO,NE)", partida.getCidades());
		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,7); VERMELHOR(0,7);");

		partida.posicionarMeepleCidade(NORDESTE);
		verificarRelatorioTurno(partida, "AMARELO", "30N", "MEEPLE_POSICIONADO");
		partida.finalizarTurno();
		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,6); VERMELHOR(0,7);");

		Assert.assertEquals("30(NO-AMARELO,NE-AMARELO)", partida.getCidades());

		partida.posicionarTile(t30, NORTE);
		verificarRelatorioTurno(partida, "VERMELHO", "30N", "Tile_Posicionado");
		partida.posicionarMeepleCidade(SUDESTE);
		verificarRelatorioTurno(partida, "VERMELHO", "11S", "MEEPLE_POSICIONADO");
		partida.finalizarTurno();
		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,6); VERMELHOR(0,6);");

		Assert.assertEquals("11(NO,NE) 11(SO-VERMELHO,SE-VERMELHO) 30(NO-AMARELO,NE-AMARELO)", partida.getCidades());

		verificarRelatorioPartida(partida, "Partida_Finalizada", "AMARELO(0,7); VERMELHO(0,6);");
		verificarRelatorioTabuleiro(partida, "11S\n30N");

	}

	/**
	 * Caso de teste 06 Jogador com varias cidades
	 * 
	 * Esse caso de teste testa quando um jogador tem varias cidades.
	 */

	public void jogadorComDuasCidades() {
		mockarTiles(tiles, t30, t11, t64, t39);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();

		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,7); VERMELHOR(0,7);");
		partida.posicionarMeepleCidade(NORDESTE);
		verificarRelatorioTurno(partida, "AMARELO", "30N", "MEEPLE_POSICIONADO");
		partida.finalizarTurno();
		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,6); VERMELHOR(0,7);");

		Assert.assertEquals("30(NO-AMARELO,NE-AMARELO)", partida.getCidades());

		partida.posicionarTile(t30, NORTE);
		verificarRelatorioTurno(partida, "VERMELHO", "30N", "Tile_Posicionado");
		partida.posicionarMeepleCidade(NOROESTE);
		verificarRelatorioTurno(partida, "VERMELHO", "11N", "MEEPLE_POSICIONADO");
		partida.finalizarTurno();
		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,7); VERMELHOR(0,6);");

		Assert.assertEquals("11(NO,NE) 11(SO-VERMELHO,SE-VERMELHO) 30(NO-AMARELO,NE-AMARELO)", partida.getCidades());

		girar(partida, 1);
		partida.posicionarTile(t30, LESTE);
		verificarRelatorioTurno(partida, "AMARELO", "30L", "Tile_Posicionado");
		partida.finalizarTurno();
		ocorreExcecaoJogo(() -> partida.relatorioTurno(), "Partida finalizada");

		partida.posicionarTile(t64, LESTE);
		verificarRelatorioTurno(partida, "VERMELHO", "64L", "Tile_Posicionado");
		partida.posicionarMeepleCidade(NOROESTE);
		verificarRelatorioTurno(partida, "VERMELHO", "39N", "MEEPLE_POSICIONADO");
		partida.finalizarTurno();
		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,7); VERMELHOR(0,5);");

		Assert.assertEquals(
				"11(NO,NE) 11(SO-VERMELHO,SE-VERMELHO) 30(NO-AMARELO,NE-AMARELO 64(NO-VERMELHOR,NE-VERMELHO))",
				partida.getCidades());
		verificarRelatorioTabuleiro(partida, "11S\n30L64L39N");
	}

	/**
	 * Caso de teste 07 Posicionar meeple em cidade já ocupada
	 */
	@Test
	public void posicionarMeepleEmCidadeOcupada() {
		mockarTiles(tiles, t30, t11);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();

		partida.posicionarMeepleCidade(NOROESTE);
		verificarRelatorioTurno(partida, "AMARELO", "30N", "MEEPLE_POSICIONADO");
		partida.finalizarTurno();
		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,6); VERMELHOR(0,7);");
		Assert.assertEquals("30(NO-AMARELO,NE-AMARELO)", partida.getCidades());

		partida.posicionarTile(t30, NORTE);
		verificarRelatorioTurno(partida, "VERMELHO", "30N", "Tile_Posicionado");
		Assert.assertEquals("11(NO,NE) 11(SO,SE) 30(NO-AMARELO,NE-AMARELO)", partida.getCidades());
		ocorreExcecaoJogo(() -> partida.posicionarMeepleCidade(SUDOESTE),
				"Impossível posicionar meeple pois a cidade já está ocupada pelo meeple AMARELO no lado SUL do tile 11");

	}

	/**
	 * Caso de teste 08 Cidade em formato de L.
	 */
	@Test
	public void cidadeEmFormatoDeL() {
		mockarTiles(tiles, t14, t07, t11);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();

		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,7); VERMELHOR(0,7);");

		partida.posicionarMeepleCidade(NORDESTE);
		verificarRelatorioTurno(partida, "AMARELO", "14N", "MEEPLE_POSICIONADO");
		partida.finalizarTurno();

		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,6); VERMELHOR(0,7);");

		partida.posicionarTile(t07, OESTE);
		verificarRelatorioTurno(partida, "VERMELHO", "14O", "Tile_Posicionado");
		partida.finalizarTurno();

		partida.posicionarTile(t14, NORTE);
		verificarRelatorioTurno(partida, "AMARELO", "14N", "Tile_Posicionado");
		partida.finalizarTurno();

		verificarRelatorioPartida(partida, "Partida_Finalizada", "AMARELO(0,7); VERMELHOR(0,7);");
		Assert.assertEquals("14(NO,SO) 14(NO-AMARELO,NE-AMARELO) 07(NE,SE) 07(NO,NE) 11(NO,NE) 11(SO SE)",
				partida.getCidades());
		verificarRelatorioTabuleiro(partida, " 07S\n11L14N");

	}

	/**
	 * Caso de teste 09 Cidade em formato de Cruz.
	 */

	@Test
	public void cidadeEmFormatoCruz() {
		mockarTiles(tiles, t01, t19, t20, t21, t22);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();

		// A
		partida.posicionarMeepleCidade(NORDESTE);
		verificarRelatorioTurno(partida, "AMARELO", "01N", "MEEPLE_POSICIONADO");
		partida.finalizarTurno();
		verificarRelatorioPartida(partida, "PARTIDA_ANDAMENTO", "AMARELO(0,6); VERMELHOR(0,7);");
		// V
		partida.posicionarTile(t01, SUL);
		partida.finalizarTurno();
		verificarRelatorioTurno(partida, "VERMELHO", "01S", "Tile_Posicionado");
		// A
		girar(partida, 1);
		partida.posicionarTile(t01, OESTE);
		partida.finalizarTurno();
		verificarRelatorioTurno(partida, "VERMELHO", "01O", "Tile_Posicionado");
		// V
		girar(partida, 2);
		partida.posicionarTile(t01, NORTE);
		partida.finalizarTurno();
		verificarRelatorioTurno(partida, "VERMELHO", "01N", "Tile_Posicionado");
		// A
		girar(partida, 3);
		partida.posicionarTile(t01, LESTE);
		partida.finalizarTurno();
		verificarRelatorioTurno(partida, "VERMELHO", "01L", "Tile_Posicionado");

		verificarRelatorioPartida(partida, "Partida_Finalizada", "AMARELO(0,7); VERMELHOR(0,7);");
		Assert.assertEquals(
				"01(NO-AMARELO,NE-AMARELO) 11(NE,SE) 11(SE,SO) 11(SO,NO) 19(NO,SE) 20(SO,NO) 21(NO,NE) 22(NE,SE) ",
				partida.getCidades());
		verificarRelatorioTabuleiro(partida, " 19S\n20L01L21\n 22");

	}

	/**
	 * Caso de teste 10 Fim de partida com cidade aberta;
	 */

	public void fimdDePartidaComCidadeAberta() {
		mockarTiles(tiles, t11, t42);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();

		// A
		partida.posicionarMeepleCidade(NORDESTE);
		partida.finalizarTurno();
		// V
		girar(partida, 1);
		partida.posicionarTile(t11, SUL);
		partida.posicionarMeepleCidade(SUDESTE);
		partida.finalizarTurno();

		verificarRelatorioPartida(partida, "Partida_Finalizada", "AMARELO(0,6); VERMELHOR(0,6);");
		Assert.assertEquals("11(NO-AMARELO,NE-AMARELO) 11(SO-VERMELHO,SE-VERMELHO) 42(NO,NE) 42(SO,SE)",
				partida.getCidades());
		verificarRelatorioTabuleiro(partida, "11S\n42N");
	}

	/**
	 * Caso de teste 11 Pontuacao para uma cidade com empate de meeples dos
	 * jogadores
	 */
	@Test
	public void pontuacaoParaCidadeComEmpateDeQuantidadeDeMeeples() {
		mockarTiles(tiles, t06, t02, t42);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();

		partida.posicionarMeepleCidade(SUDESTE);// Jogador 1 posiciona meeple
		partida.finalizarTurno(); // amarelo

		partida.posicionarTile(t06, NORTE);
		partida.finalizarTurno();

		partida.posicionarTile(t02, OESTE);
		partida.posicionarMeepleCidade(SUDOESTE);// Jogador 2 posiciona meeple
		partida.finalizarTurno(); // vermelho

		Assert.assertEquals("06(SO-VERMELHO,SE-VERMELHO) 02(NO-AMARELO,NE-AMARELO) 42(NE,SE)", partida.getCidades());
		verificarRelatorioPartida(partida, "PTD_FINALIZADA", "AMARELO(6,7); VERMELHOR(6,7)");
		verificarRelatorioTabuleiro(partida, "06S\n02L42");
	}

	/**
	 * Caso de teste 12 Pontuacao para uma cidade onde um dos jogadores tem mais
	 * meeples que o outro na cidade
	 */
	@Test
	public void pontuacaoParaCidadeOndeUmJogadorTemMaisMeeplesNaCidade() {
		mockarTiles(tiles, t06, t02, t42);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();

		partida.posicionarMeepleCidade(SUDESTE);// Jogador 1 posiciona meeple
		partida.finalizarTurno(); // amarelo

		partida.posicionarTile(t06, NORTE);
		partida.posicionarMeepleCidade(NORDESTE);// Jogador 2 posiciona meeple
		partida.finalizarTurno(); // vermelho

		partida.posicionarTile(t02, OESTE);
		partida.posicionarMeepleCidade(NORDESTE);// Jogador 1 posiciona meeple
		partida.finalizarTurno(); // amarelo

		Assert.assertEquals("06(SO-VERMELHO,SE-VERMELHO) 02(NO-AMARELO,NE-AMARELO) 42(NE,SE)", partida.getCidades());
		verificarRelatorioPartida(partida, "PTD_FINALIZADA", "AMARELO(6,7); VERMELHOR(0,7)");
		verificarRelatorioTabuleiro(partida, "06S\n02L42");
		// 11(NO,NE) 11(SO,SE) 42(NO,NE) 42(SO,SE)
	}

	/**
	 * Caso de teste 13 Duas cidades separadas cada uma pertence a um jogador mas
	 * elas se encontram formando uma só cidade
	 */

	public void cidadesSeparadasQueSeEncontram() {
		mockarTiles(tiles, t30, t24, t14, t34);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();

		// A
		partida.posicionarMeepleCidade(NORDESTE);
		verificarRelatorioTurno(partida, "AMARELO", "30N", "MEEPLE_POSICIONADO");
		partida.finalizarTurno();

		// V
		partida.posicionarTile(t30, LESTE);
		verificarRelatorioTurno(partida, "AMARELO", "30L", "Tile_Posicionado");
		partida.posicionarMeepleCidade(NORDESTE);
		verificarRelatorioTurno(partida, "VERMELHO", "24N", "MEEPLE_POSICIONADO");
		partida.finalizarTurno();

		// A
		girar(partida, 3);
		partida.posicionarTile(t24, NORTE);
		partida.finalizarTurno();

		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,6); VERMELHOR(0,6);");

		// V
		partida.posicionarTile(t30, NORTE);
		partida.finalizarTurno();

		verificarRelatorioPartida(partida, "PTD_FINALIZADA", "AMARELO(4,7); VERMELHOR(4,7)");
		Assert.assertEquals(
				"34(SO,SE) 34(NE,SE) 14(NO,SO) 14(SO,SE) 30(NO-AMARELO,NE-AMARELO) 24(NO-VERMELHO,NE-VERMELHO)",
				partida.getCidades());
		verificarRelatorioTabuleiro(partida, "34S14S\n30N24O");
	}

	/**
	 * Caso de teste 14 Partida com 3 Jogadores
	 */
	public void partidaCom3Jogadores() {
		mockarTiles(tiles, t30, t24, t08);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO, PRETO);
		partida.finalizarTurno();

		// Amarelo
		partida.posicionarMeepleCidade(NORDESTE);
		verificarRelatorioPartida(partida, "PTD_FINALIZADA", "AMARELO(0,6); VERMELHOR(0,7); PRETO(0,7);");
		partida.finalizarTurno();
		// Vermelho
		partida.posicionarTile(t30, LESTE);
		partida.posicionarMeepleCidade(NORDESTE);
		verificarRelatorioPartida(partida, "PTD_FINALIZADA", "AMARELO(0,6); VERMELHOR(0,6); PRETO(0,7);");
		partida.finalizarTurno();
		// Preto
		girar(partida, 3);
		//As cidades são ligadas pelos vértices no getCidades mais no tabuleiro elas são ligadas por (N,S,L,O) isso ta correto? O Girar ele é do oeste para leste ?
		partida.posicionarTile(t24, SUL);
		partida.posicionarMeepleCidade(SUDESTE);
		verificarRelatorioPartida(partida, "PTD_FINALIZADA", "AMARELO(0,6); VERMELHOR(0,6); PRETO(0,6);");
		partida.finalizarTurno();

		verificarRelatorioPartida(partida, "PTD_FINALIZADA", "AMARELO(1,6); VERMELHOR(1,6); PRETO(1,6);");
		//As cidades se ligam pelos vértices então os tem que colocar todos os vértices?
		//E os meeples também tem que ser colocados em todos os vértices?

		Assert.assertEquals(
				"30(NO-AMARELO,NE-AMARELO) 24(NO-VERMELHO,NE-VERMELHO) 08(NO-PRETO,SO-PRETO) 08(SO-PRETO,SE-PRETO) 08(SE-PRETO,NE-PRETO)",
				partida.getCidades());
		verificarRelatorioTabuleiro(partida, "30L24S\n 08N");

	}

	/**
	 * Caso de teste 15 Partida com 4 Jogadores.
	 */
	public void partidaCom4Jogadores() {
		mockarTiles(tiles, t30, t24, t08, t36);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO, PRETO, AZUL);
		partida.finalizarTurno();

		// Amarelo
		partida.posicionarMeepleCidade(NOROESTE);
		verificarRelatorioPartida(partida, "PTD_FINALIZADA", "AMARELO(0,6); VERMELHOR(0,7); PRETO(0,7); AZUL(0,7);");
		partida.finalizarTurno();
		// Vermelho
		partida.posicionarTile(t30, LESTE);
		partida.posicionarMeepleCidade(SUDOESTE);
		verificarRelatorioPartida(partida, "PTD_FINALIZADA", "AMARELO(0,6); VERMELHOR(0,6); PRETO(0,7) AZUL(0,7);");
		partida.finalizarTurno();
		// Preto
		girar(partida, 3);
		partida.posicionarTile(t24, SUL);
		partida.posicionarMeepleCidade(SUDESTE);
		verificarRelatorioPartida(partida, "PTD_FINALIZADA", "AMARELO(0,6); VERMELHOR(0,6); PRETO(0,6) AZUL(0,7);");
		partida.finalizarTurno();
		// Azul
		partida.posicionarTile(t30, OESTE);
		partida.posicionarMeepleCidade(SUDOESTE);
		partida.finalizarTurno();
		verificarRelatorioPartida(partida, "PTD_FINALIZADA", "AMARELO(0,6); VERMELHOR(0,6); PRETO(0,6) AZUL(0,6);");

		verificarRelatorioPartida(partida, "PTD_FINALIZADA", "AMARELO(1,6); VERMELHOR(1,6); PRETO(1,6); AZUL(1,6)");
		Assert.assertEquals(
				"30(NO-AMARELO,NE-AMARELO) 24(NO-VERMELHO,NE-VERMELHO) 08(NO-PRETO,SO-PRETO) 08(SO-PRETO,SE-PRETO) 08(SE-PRETO,NE-PRETO) 36(NO-AZUL,NE-AZUL)",
				partida.getCidades());
		verificarRelatorioTabuleiro(partida, "36L30L24S\n 08N");

	}

	/**
	 * Caso de teste 16 Partida cidade mista e 4 jogadores.
	 */

	public void partidaCom4JogadoreCidadeMista() {
		mockarTiles(tiles, t30, t24, t08, t36, t14, t31);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO, PRETO, AZUL);
		partida.finalizarTurno();

		// Amarelo
		partida.posicionarMeepleCidade(NORDESTE);
		verificarRelatorioPartida(partida, "PTD_FINALIZADA", "AMARELO(0,6); VERMELHOR(0,7); PRETO(0,7); AZUL(0,7);");
		partida.finalizarTurno();
		// Vermelho
		partida.posicionarTile(t30, LESTE);
		partida.posicionarMeepleCidade(NORDESTE);
		verificarRelatorioPartida(partida, "PTD_FINALIZADA", "AMARELO(0,6); VERMELHOR(0,6); PRETO(0,7) AZUL(0,7);");
		partida.finalizarTurno();
		// Preto
		girar(partida, 3);
		partida.posicionarTile(t24, SUL);
		partida.posicionarMeepleCidade(SUDOESTE);
		verificarRelatorioPartida(partida, "PTD_FINALIZADA", "AMARELO(0,6); VERMELHOR(0,6); PRETO(0,6) AZUL(0,7);");
		partida.finalizarTurno();
		// Azul
		partida.posicionarTile(t30, OESTE);
		partida.posicionarMeepleCidade(NOROESTE);
		partida.finalizarTurno();
		// AMARELO
		girar(partida, 3);
		partida.posicionarTile(t24, NORTE);
		partida.finalizarTurno();
		// VERMELHO
		partida.posicionarTile(t30, NORTE);
		partida.finalizarTurno();

		verificarRelatorioPartida(partida, "PTD_FINALIZADA", "AMARELO(4,7); VERMELHOR(4,7); PRETO(1,6) AZUL(1,6);");
		Assert.assertEquals(
				"30(NO-AMARELO,NE-AMARELO) 24(NO-VERMELHO,NE-VERMELHO) 08(NO-PRETO,SO-PRETO) 08(SO-PRETO,SE-PRETO) 08(SE-PRETO,NE-PRETO) 36(NO-AZUL,NE-AZUL) 14(NO,SO) 14(SO,SE) 31(SO,SE) 31(SE,NE)",
				partida.getCidades());
		verificarRelatorioTabuleiro(partida, " 31S14S\n36L30L\n  08N");

	}

	private void girar(Partida partida, int quantidade) {
		for (int i = 0; i < quantidade; i++) {
			partida.girarTile();
		}
	}

	private void ocorreExcecaoJogo(ExceptionThrower et, String mensagem) {
		ocorreExcecao(et).tipoExcecao(ExcecaoJogo.class).mensagem(mensagem);
	}

	private void mockarTiles(BolsaDeTiles mock, Tile primeiro, Tile... tiles) {
		when(mock.pegar()).thenReturn(primeiro, Arrays.copyOf(tiles, tiles.length + 1));
	}

	private void verificarRelatorioPartida(Partida partida, String status, String sequencia) {
		String relatorio = partida.relatorioPartida();
		Assert.assertEquals("Status: " + status + "\nJogadores: " + sequencia, relatorio);
	}

	private void verificarRelatorioTurno(Partida partida, String jogador, String tile, String status) {
		String relatorio = partida.relatorioTurno();
		Assert.assertEquals("Jogador: " + jogador + "\nTile: " + tile + "\nStatus: " + status, relatorio);
	}

	private void verificarRelatorioTabuleiro(Partida partida, String configuracao) {
		Assert.assertEquals(configuracao, partida.relatorioTabuleiro());
	}

}
