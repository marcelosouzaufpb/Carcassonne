package br.ufpb.dcx.aps.carcassone.teste;

import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.AMARELO;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.AZUL;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.LESTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.NORDESTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.NORTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.OESTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.PRETO;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.SUDESTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.SUDOESTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.SUL;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.VERDE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.VERMELHO;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t01;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t02;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t06;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t10;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t11;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t29;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t30;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t43;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t49;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t51;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t52;
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

public class cidadeTestes extends JogoTest {

	private Jogo jogo;
	private BolsaDeTiles tiles;

	@Before
	public void novoJogo() {
		tiles = mock(BolsaDeTiles.class);
		jogo = new Jogo();
	}

	/**
	 * Caso de Teste 01 Posicionar uma um Meeple onde não tem cidade.
	 */

	@Test
	public void posicionarMeepleCidadedaSemCidade() {
		mockarTiles(tiles, t30, t11);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();
		girar(partida, 1);

		partida.posicionarTile(t30, NORTE);

		ocorreExcecaoJogo(() -> partida.posicionarMeepleCidade(LESTE),
				"Impossível posicionar meeple em cidade pois o lado leste do tile 11 é Campo");

		ocorreExcecaoJogo(() -> partida.posicionarMeepleCidade(OESTE),
				"Impossível posicionar meeple em cidade pois o lado oeste do tile 11 é Campo");
	}

	/**
	 * Caso de tese 02 Verificar cidade antes e depois de colocar Meeple em uma
	 * cidade
	 */
	@Test
	public void verificarCidade() {
		mockarTiles(tiles, t11, t30);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);

		Assert.assertEquals("11(N,S)", partida.getCidades());

		ocorreExcecaoJogo(() -> partida.posicionarMeepleCidade(NORTE), "Impossível posicionar meeple na peça inicial");

		partida.posicionarTile(t11, SUL);
		Assert.assertEquals("11(N,S) t30(N)", partida.getCidades());
		partida.posicionarMeepleCidade(NORTE);
		Assert.assertEquals("11(N,S) t30(N-VERMELHO)", partida.getCidades());
	}

	/**
	 * Caso de tese 03 Verificar estrada 
	 * antes e depois de colocar Meeple em uma cidade
	 */
	@Test
	public void verificarCidade2() {
		mockarTiles(tiles, t30, t11);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.finalizarTurno();
		girar(partida, 1);

		partida.posicionarTile(t30, NORTE);
		Assert.assertEquals("30(N) 11(N,S)", partida.getCidades());

		partida.posicionarMeepleCidade(SUL);
		Assert.assertEquals("30(N) 11(N,S-AMARELO)", partida.getCidades());

		partida.finalizarTurno();
		Assert.assertEquals("30(N) 11(N,S-AMARELO)", partida.getCidades());

		verificarRelatorioPartida(partida, "Partida_Finalizada", "AMARELO(0,6); VERMELHO(0,7)");
		ocorreExcecaoJogo(() -> partida.relatorioTurno(), "Partida finalizada");
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
