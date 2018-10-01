package br.ufpb.dcx.aps.carcassone.teste;

import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.AMARELO;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.LESTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.NORTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.OESTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.SUL;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.VERMELHO;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t06;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t07;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t11;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t14;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t30;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t39;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t43;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t64;
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

public class TestC extends JogoTest {

	private Jogo jogo;
	private BolsaDeTiles tiles;

	@Before
	public void novoJogo() {
		tiles = mock(BolsaDeTiles.class);
		jogo = new Jogo();
	}

	/**
	 * Caso de Teste 00 Posicionar um Meeple em uma Cidade.
	 */

	@Test
	public void posicionarMeepleEmCidade() {
		mockarTiles(tiles, t30);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();

		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,7); VERMELHOR(0,7)");
		partida.posicionarMeepleCidade(NORTE);
		partida.finalizarTurno();

		verificarRelatorioTurno(partida, "AMARELO", "30N", "MEEPLE_POSICIONADO");
		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,6); VERMELHOR(0,7)");
		Assert.assertEquals("30(NO,NE)", partida.getCidades());
		verificarRelatorioTabuleiro(partida, "30N");

	}

	/**
	 * Caso de Teste 01 Posicionar um Meeple onde não tem cidade.
	 * 
	 * Esse caso de teste so tem um tile
	 */
	@Test
	public void posicionarMeepleCidadeSemCidade() {
		mockarTiles(tiles, t30);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();

		Assert.assertEquals("30(NO,NE)", partida.getCidades());

		ocorreExcecaoJogo(() -> partida.posicionarMeepleCidade(OESTE),
				"Impossível posicionar meeple em cidade pois o lado leste do tile 30 é Estrada");

		ocorreExcecaoJogo(() -> partida.posicionarMeepleCidade(LESTE),
				"Impossível posicionar meeple em cidade pois o lado oeste do tile 30 é Estrada");

	}

	/**
	 * Caso de Teste 02 Posicionar um Meeple onde não tem cidade.
	 * 
	 * Esse caso de teste é composto de 2 tile e um Meeple
	 */
	@Test
	public void posicionarMeepleCidadeSemCidade2() {
		mockarTiles(tiles, t30, t11);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();

		partida.posicionarTile(t30, NORTE);

		Assert.assertEquals("11(NO,NE) 11(SO,SE) 30(NO,NE)", partida.getCidades());

		ocorreExcecaoJogo(() -> partida.posicionarMeepleCidade(LESTE),
				"Impossível posicionar meeple em cidade pois o lado leste do tile 11 é Campo");

		ocorreExcecaoJogo(() -> partida.posicionarMeepleCidade(OESTE),
				"Impossível posicionar meeple em cidade pois o lado oeste do tile 11 é Campo");

	}

	/**
	 * Caso de teste 03 Cidade com 2 tiles e um meeple cidade.
	 *
	 * Esse teste faz a comparacao do final da partida
	 */

	@Test
	public void cidaeComDoisTilesMeeple() {
		mockarTiles(tiles, t30, t11);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();

		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,7); VERMELHOR(0,7)");
		partida.posicionarTile(t30, NORTE);
		partida.finalizarTurno();
		Assert.assertEquals("11(NO,NE) 11(SO,SE) 30(NO,NE)", partida.getCidades());
		ocorreExcecaoJogo(() -> partida.relatorioTurno(), "Partida finalizada");

		partida.posicionarMeepleCidade(SUL);
		partida.finalizarTurno();
		verificarRelatorioTurno(partida, "VERMELHO", "11S", "MEEPLE_POSICIONADO");
		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,7); VERMELHOR(0,7)");
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
		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,7); VERMELHOR(0,7)");

		partida.posicionarMeepleCidade(NORTE);
		verificarRelatorioTurno(partida, "AMARELO", "30N", "MEEPLE_POSICIONADO");
		partida.finalizarTurno();

		Assert.assertEquals("30(NO-AMARELO,NE-AMARELO)", partida.getCidades());
		partida.posicionarTile(t30, NORTE);
		partida.finalizarTurno();
		Assert.assertEquals("11(NO,NE) 11(SO,SE) 30(NO-AMARELO,NE-AMARELO)", partida.getCidades());

		Assert.assertEquals("11(NO,NE)\\N11(SO,SE) 30(NO-AMARELO,NE-AMARELO)", partida.getCidades());
		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(8,6); VERMELHOR(0,7)");
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
		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,7); VERMELHOR(0,7)");

		partida.posicionarMeepleCidade(NORTE);
		verificarRelatorioTurno(partida, "AMARELO", "30N", "MEEPLE_POSICIONADO");
		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,6); VERMELHOR(0,7)");
		partida.finalizarTurno();

		Assert.assertEquals("30(NO-AMARELO,NE-AMARELO)", partida.getCidades());

		partida.posicionarTile(t30, NORTE);
		verificarRelatorioTurno(partida, "VERMELHO", "30N", "Tile_Posicionado");
		partida.posicionarMeepleCidade(SUL);
		verificarRelatorioTurno(partida, "VERMELHO", "11S", "MEEPLE_POSICIONADO");
		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,6); VERMELHOR(0,6)");
		partida.finalizarTurno();

		Assert.assertEquals("11(NO,NE) 11(SO-VERMELHO,SE-VERMELHO) 30(NO-AMARELO,NE-AMARELO)", partida.getCidades());

		Assert.assertEquals("11(NO,NE)\\N11(SO-VERMELHO,SE-VERMELHO) 30(NO-AMARELO,NE-AMARELO)", partida.getCidades());
		verificarRelatorioPartida(partida, "Partida_Finalizada", "AMARELO(0,7); VERMELHO(0,6)");
		ocorreExcecaoJogo(() -> partida.relatorioTurno(), "Partida finalizada");
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

		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,7); VERMELHOR(0,7)");
		partida.posicionarMeepleCidade(NORTE);
		verificarRelatorioTurno(partida, "AMARELO", "30N", "MEEPLE_POSICIONADO");
		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,6); VERMELHOR(0,7)");
		partida.finalizarTurno();

		Assert.assertEquals("30(NO-AMARELO,NE-AMARELO)", partida.getCidades());

		partida.posicionarTile(t30, NORTE);
		verificarRelatorioTurno(partida, "VERMELHO", "30N", "Tile_Posicionado");
		partida.posicionarMeepleCidade(NORTE);
		verificarRelatorioTurno(partida, "VERMELHO", "11N", "MEEPLE_POSICIONADO");
		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,7); VERMELHOR(0,6)");
		partida.finalizarTurno();

		Assert.assertEquals("11(NO,NE) 11(SO-VERMELHO,SE-VERMELHO) 30(NO-AMARELO,NE-AMARELO)", partida.getCidades());

		girar(partida, 1);
		partida.posicionarTile(t30, LESTE);
		verificarRelatorioTurno(partida, "AMARELO", "30L", "Tile_Posicionado");
		partida.finalizarTurno();
		ocorreExcecaoJogo(() -> partida.relatorioTurno(), "Partida finalizada");

		partida.posicionarTile(t64, LESTE);
		verificarRelatorioTurno(partida, "VERMELHO", "64L", "Tile_Posicionado");
		partida.posicionarMeepleCidade(NORTE);
		verificarRelatorioTurno(partida, "VERMELHO", "39N", "MEEPLE_POSICIONADO");
		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,7); VERMELHOR(0,5)");
		partida.finalizarTurno();

		Assert.assertEquals(
				"11(NO,NE) 11(SO-VERMELHO,SE-VERMELHO) 30(NO-AMARELO,NE-AMARELO 64(NO-VERMELHOR,NE-VERMELHO))",
				partida.getCidades());

		ocorreExcecaoJogo(() -> partida.relatorioTurno(), "Partida finalizada");
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

		partida.posicionarMeepleCidade(NORTE);
		verificarRelatorioTurno(partida, "AMARELO", "30N", "MEEPLE_POSICIONADO");
		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,6); VERMELHOR(0,7)");
		Assert.assertEquals("30(NO-AMARELO,NE-AMARELO)", partida.getCidades());
		partida.finalizarTurno();

		partida.posicionarTile(t30, NORTE);
		verificarRelatorioTurno(partida, "VERMELHO", "30N", "Tile_Posicionado");
		Assert.assertEquals("11(NO,NE) 11(SO,SE) 30(NO-AMARELO,NE-AMARELO)", partida.getCidades());
		ocorreExcecaoJogo(() -> partida.posicionarMeepleCidade(SUL),
				"Impossível posicionar meeple pois a cidade já está ocupada pelo meeple AMARELO no lado SUL do tile 11");

	}

	/**
	 * Caso de teste 08 Cidade em formato de L.
	 */
	@Test
	public void CidadeEmFormatoDeL() {
		mockarTiles(tiles, t14, t07, t11);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();

		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,7); VERMELHOR(0,7)");

		partida.posicionarMeepleCidade(NORTE);
		verificarRelatorioTurno(partida, "AMARELO", "14N", "MEEPLE_POSICIONADO");
		partida.finalizarTurno();

		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,6); VERMELHOR(0,7)");

		partida.posicionarTile(t07, OESTE);
		verificarRelatorioTurno(partida, "VERMELHO", "14O", "Tile_Posicionado");
		partida.finalizarTurno();

		partida.posicionarTile(t14, NORTE);
		verificarRelatorioTurno(partida, "AMARELO", "14N", "Tile_Posicionado");
		partida.finalizarTurno();

		verificarRelatorioPartida(partida, "PARTIDA_FINALIZADA", "AMARELO(0,7); VERMELHOR(0,7)");
		Assert.assertEquals("14(NO,SO) 14(NO-AMARELO,NE-AMARELO) 07(NE,SE) 07(NO,NE) 11(NO,NE) 11(SO SE)",
				partida.getCidades());
		verificarRelatorioTabuleiro(partida, "_07S\n11L14N");

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
