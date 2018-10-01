package br.ufpb.dcx.aps.carcassone.teste;

import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.AMARELO;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.LESTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.NORTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.OESTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.VERMELHO;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t11;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t30;
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
	 * Caso de Teste 01 Posicionar um Meeple onde não tem cidade.
	 * 
	 * Esse caso de teste so tem um tile
	 */
	@Test
	public void posicionarMeepleCidadeSemCidade() {
		mockarTiles(tiles, t30);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();

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

		ocorreExcecaoJogo(() -> partida.posicionarMeepleCidade(LESTE),
				"Impossível posicionar meeple em cidade pois o lado leste do tile 11 é Campo");

		ocorreExcecaoJogo(() -> partida.posicionarMeepleCidade(OESTE),
				"Impossível posicionar meeple em cidade pois o lado oeste do tile 11 é Campo");

	}

	/**
	 * Caso de tese 03 Verificar cidade antes e depois de colocar um Meeple em uma
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
		verificarRelatorioTurno(partida, "AMARELO", "30N", "Tile_Posicionado");
		partida.finalizarTurno();

		Assert.assertEquals("30(NO-AMARELO,NE-AMARELO)", partida.getCidades());
		partida.posicionarTile(t30, NORTE);
		partida.finalizarTurno();

		Assert.assertEquals("11(NO,NE)\\N11(SO,SE) 30(NO-AMARELO,NE-AMARELO)", partida.getCidades());
		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(8,6); VERMELHOR(0,7)");
		verificarRelatorioTabuleiro(partida, "30N11S");

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
