package br.com.receita;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.conta.ContaDTO;
import br.com.conta.ContaService;
import br.com.conta.enums.TipoConta;
import br.com.exception.ApiException;
import br.com.receita.enums.TipoReceita;
import br.com.receita.exception.ReceitaNaoEncontradaException;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class ReceitaServiceTest {
	
	@Autowired
	private ReceitaService receitaService;
	
	@Autowired
	private ContaService contaService;
	
	private static ContaDTO contaDTO;
	private static ReceitaDTO receitaDTO;
	
	@BeforeAll
	//Cadastra 2 contas e 3 receitas
	public void receitaSetup() throws ApiException {
		
		contaDTO = new ContaDTO();
		contaDTO.setSaldo(2000.00);
		contaDTO.setTipoConta(TipoConta.CARTEIRA);
		contaDTO.setInstituicaoFinanceira("Picpay");
		contaService.cadastrarConta(contaDTO);
		contaDTO = new ContaDTO();
		contaDTO.setSaldo(2000.00);
		contaDTO.setTipoConta(TipoConta.CARTEIRA);
		contaDTO.setInstituicaoFinanceira("Picpay");
		contaService.cadastrarConta(contaDTO);
		receitaDTO = new ReceitaDTO();
		receitaDTO.setContaId(1L);
		receitaDTO.setDataRecebimento(LocalDate.of(2022, 01, 16));
		receitaDTO.setDataRecebimentoEsperado(LocalDate.of(2022, 01, 16));
		receitaDTO.setTipoReceita(TipoReceita.SALARIO);
		receitaService.cadastrarReceita(receitaDTO);
		receitaDTO = new ReceitaDTO();
		receitaDTO.setContaId(1L);
		receitaDTO.setDataRecebimento(LocalDate.of(2022, 01, 16));
		receitaDTO.setDataRecebimentoEsperado(LocalDate.of(2022, 01, 16));
		receitaDTO.setTipoReceita(TipoReceita.SALARIO);
		receitaService.cadastrarReceita(receitaDTO);
		receitaDTO = new ReceitaDTO();
		receitaDTO.setContaId(2L);
		receitaDTO.setDataRecebimento(LocalDate.of(2022, 01, 16));
		receitaDTO.setDataRecebimentoEsperado(LocalDate.of(2022, 01, 16));
		receitaDTO.setTipoReceita(TipoReceita.SALARIO);
		receitaService.cadastrarReceita(receitaDTO);
	}

	@Test
	void deveConsultarDadosReceita() throws ApiException {
		assertNotNull(receitaService.dadosReceita(1L));
	}

	@Test
	void deveDadosReceitaPorTipoReceita() {
		assertNotNull(receitaService.dadosReceitaPorTipoReceita(TipoReceita.SALARIO));
	}

	@Test
	void deveEditarReceita() throws ApiException {
		receitaDTO = new ReceitaDTO();
		receitaDTO.setId(2L);
		receitaDTO.setDescricao("Receita Edit");
		receitaDTO.setValor(1800.75);
		receitaService.editarReceita(2L, receitaDTO);
		Receita receita = receitaService.dadosReceita(2L);
		assertAll(
				() -> assertEquals( "Receita Edit" , receita.getDescricao()),
				() -> assertEquals( 1800.75, receita.getValor())
				);
		
	}

	@Test
	void deveListarReceitasPorPeriodo() {
		assertNotNull(receitaService.listarReceitasPorPeriodo(LocalDate.of(2022, 01, 15), LocalDate.of(2022, 01, 17)));
	}

	@Test
	void deveLancarExcecaoaoRemoverReceitaInexistente() {
		ReceitaNaoEncontradaException excecao = assertThrows(ReceitaNaoEncontradaException.class, 
				() -> receitaService.removerReceita(700L) );
		
		String mensagemEsperada = "A receita: " + "700" + " Nao foi encontrada";
		String mensagemRecebida = excecao.getMessage();
		assertTrue(mensagemRecebida.contains(mensagemEsperada));
	}

	@Test
	void deveListarReceitas() {
		assertNotNull(receitaService.listarReceitas());
	}

	@Test
	void deveListarReceitasContaPorPeriodo() {
	assertNotNull(receitaService.listarReceitasContaPorPeriodo(1L, LocalDate.of(2022, 01, 15), LocalDate.of(2022, 01, 17)));
	}

	@Test
	void deveConsultarValorTotalReceitas() {
		assertNotNull(receitaService.valorTotalReceitas());
	}

	@Test
	void deveValorTotalReceitaPorContaId() {
		assertNotNull(receitaService.valorTotalReceitaPorContaId(1L));
	}

}
