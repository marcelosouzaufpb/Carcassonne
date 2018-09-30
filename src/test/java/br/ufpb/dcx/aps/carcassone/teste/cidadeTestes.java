package br.ufpb.dcx.aps.carcassone.teste;

import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.AMARELO;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.LESTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.NORTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.OESTE;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.SUL;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.VERMELHO;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t01;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t02;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t06;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t11;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t24;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t30;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t31;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t42;
import static br.ufpb.dcx.aps.carcassone.TilesJogoBase.t43;
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
	 * Caso de tese 03 Verificar estrada antes e depois de colocar Meeple em uma
	 * cidade
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

	/**
	 * Caso de teste 04 Cidade com dois tiles e um meeple
	 */
	@Test
	public void cidaeComDoisTilesMeeple() {
		mockarTiles(tiles, t30, t11);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
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

	/**
	 * Caso de teste 05 Cidade desconexas com meeple
	 */
	@Test
	public void cidadeDesconexasMeeple() {
		mockarTiles(tiles, t30, t06, t11);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		girar(partida, 1);

		ocorreExcecaoJogo(() -> partida.posicionarTile(t30, SUL),
				"Impossível posicionar tile pois o lado sul do tile 30 é Campo");
		partida.finalizarTurno();

		partida.posicionarTile(t30, NORTE);
		Assert.assertEquals("30(N) 11(N,S)", partida.getCidades());

		partida.posicionarMeepleCidade(NORTE);
		Assert.assertEquals("30(N))", partida.getCidades());

		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,7); VERMELHO(0,6)");
		verificarRelatorioTurno(partida, "VERMELHO", "11S", "Meeple_Posicionado");
		verificarRelatorioTabuleiro(partida, "30N11S");

	}

	/**
	 * Caso de teste 06 Posicionar meeple em cidade já ocupada
	 */
	@Test
	public void posicionarMeepleEmCidadeOcupada() {
		mockarTiles(tiles, t11, t06, t43);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		girar(partida, 1);

		partida.posicionarTile(t11, SUL);
		partida.posicionarMeepleCidade(SUL);
		partida.finalizarTurno();
		Assert.assertEquals("11(N,S-AMARELO) 06(N,L)", partida.getEstradas());

		partida.posicionarTile(t06, LESTE);
		ocorreExcecaoJogo(() -> partida.posicionarMeepleCidade(LESTE),
				"Impossível posicionar meeple pois a cidade já está ocupada pelo meeple AMARELO no lado Norte do tile 06");

		verificarRelatorioPartida(partida, "Em_Andamento", "AMARELO(0,6); VERMELHO(0,7)");
		verificarRelatorioTurno(partida, "AMARELO", "11L", "Tile_Posicionado");
		verificarRelatorioTabuleiro(partida, "11S06NL43O");

	}

	/**
	 * Caso de teste 07 Pontuacao para uma cidade
	 */
	@Test
	public void pontuacaoParaUmaCidade() {
		mockarTiles(tiles, t30, t11);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		girar(partida, 1);

		partida.posicionarTile(t30, NORTE);
		partida.posicionarMeepleCidade(SUL);
		partida.finalizarTurno();
		Assert.assertEquals("(30(N) 11(N,S-AMARELO)", partida.getCidades());

		verificarRelatorioPartida(partida, "PTD_FINALIZADA", "AMARELO(4,7); VERMELHOR(0,7)");
	}

	/**
	 * Caso de teste 08 Pontuacao para uma cidade
	 */
	@Test
	public void cidadeFechadaComMeeple() {
		mockarTiles(tiles, t30, t11);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		girar(partida, 1);

		partida.posicionarTile(t30, NORTE);
		partida.posicionarMeepleCidade(SUL);
		ocorreExcecaoJogo(() -> partida.finalizarTurno(),
				"O turno nao pode ser finalizado pois a uma cidade fechada e um meeple nela!");
		Assert.assertEquals("(30(N) 11(N,S-AMARELO)", partida.getCidades());

		verificarRelatorioPartida(partida, "PTD_FINALIZADA", "AMARELO(4,6); VERMELHOR(0,7)");
	}

	/**
	 * Caso de teste 09 Pontuacao para uma cidade com empate de meeples dos
	 * jogadores
	 */
	@Test
	public void pontuacaoParaCidadeComEmpateDeQuantidadeDeMeeples() {
		mockarTiles(tiles, t06, t02, t42);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.posicionarMeepleCidade(SUL);// Jogador 1 posiciona meeple amarelo
		partida.posicionarTile(t06, NORTE);
		partida.posicionarTile(t02, OESTE);
		partida.posicionarMeepleCidade(OESTE);// Jogador 2 posiciona meeple vermelho
		Assert.assertEquals("(06(S,S-AMARELO) 02(N) 42(O,O-VERMELHO) ", partida.getCidades());
		ocorreExcecaoJogo(() -> partida.finalizarTurno(), "Ao finalizar turno nao retrou o meeple");
		verificarRelatorioPartida(partida, "PTD_FINALIZADA", "AMARELO(6,7); VERMELHOR(6,7)");
	}

	/**
	 * Caso de teste 10 Pontuacao para uma cidade onde um dos jogadores tem mais
	 * meeples que o outro na cidade
	 */
	@Test
	public void pontuacaoParaCidadeOndeUmJogadorTemMaisMeeplesNaCidade() {
		mockarTiles(tiles, t06, t02, t42);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.posicionarMeepleCidade(SUL);// Jogador 1 posiciona meeple amarelo
		partida.posicionarTile(t06, NORTE);
		partida.posicionarMeepleCidade(NORTE);// Jogador 2 posiciona meeple vermelho
		partida.posicionarTile(t02, OESTE);
		partida.posicionarMeepleCidade(OESTE);// Jogador 1 posiciona meeple amarelo
		Assert.assertEquals("(06(S,S-AMARELO) 02(N) 42(O,O-VERMELHO) ", partida.getCidades());
		ocorreExcecaoJogo(() -> partida.finalizarTurno(), "Ao finalizar turno nao retrou o meeple");
		verificarRelatorioPartida(partida, "PTD_FINALIZADA", "AMARELO(6,7); VERMELHOR(0,7)");
	}

	/**
	 * Caso de teste 11 cidade pertencente a 2 jogadores
	 */
	@Test
	public void cidadeDeDoisJogadores() {
		mockarTiles(tiles, t30, t31, t24, t72, t01);
		Partida partida = jogo.criarPartida(tiles, AMARELO, VERMELHO);
		partida.posicionarTile(t30, NORTE);
		partida.posicionarMeepleCidade(SUL);
		partida.finalizarTurno();

		ocorreExcecaoJogo(() -> partida.relatorioTurno(), "Partida finalizada");

		partida.posicionarTile(t30, LESTE);
		partida.finalizarTurno();

		ocorreExcecaoJogo(() -> partida.relatorioTurno(), "Partida finalizada");

		partida.posicionarTile(t24, SUL);
		partida.posicionarMeepleCidade(NORTE);
		partida.finalizarTurno();

		ocorreExcecaoJogo(() -> partida.relatorioTurno(), "Partida finalizada");

		partida.posicionarTile(t31, LESTE);
		partida.finalizarTurno();

		ocorreExcecaoJogo(() -> partida.relatorioTurno(), "Partida finalizada");

		Assert.assertEquals("30(N) 11(S-AMARELO,L) 24(N-VERMELHO) 01(N,L,S,O )", partida.getCidades());
		verificarRelatorioPartida(partida, "Partida_Finalizada", "AMARELO(8,6); VERMELHO(8,6)");

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
