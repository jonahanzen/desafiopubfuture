package br.com.despesa;

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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.conta.ContaService;
import br.com.conta.dto.EditarContaDTO;
import br.com.conta.dto.NovaContaDTO;
import br.com.conta.enums.TipoConta;
import br.com.despesa.dto.EditarDespesaDTO;
import br.com.despesa.dto.NovaDespesaDTO;
import br.com.despesa.enums.TipoDespesa;
import br.com.despesa.exception.DespesaNaoEncontradaException;
import br.com.exception.ApiException;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class DespesaServiceTest {

	@Autowired
	DespesaService despesaService;

	@Autowired
	ContaService contaService;

	@Autowired
	ModelMapper modelMapper;

	private static NovaContaDTO contaDTO;
	private static NovaDespesaDTO despesaDTO;

	@BeforeAll
	// Cadastra 2 contas e 3 despesas
	void despesasSetup() throws ApiException {
		contaDTO = new NovaContaDTO();
		contaDTO.setSaldo(2000.00);
		contaDTO.setTipoConta(TipoConta.CARTEIRA);
		contaDTO.setInstituicaoFinanceira("Picpay");
		contaService.cadastrarConta(contaDTO);
		contaDTO = new NovaContaDTO();
		contaDTO.setSaldo(2000.00);
		contaDTO.setTipoConta(TipoConta.CARTEIRA);
		contaDTO.setInstituicaoFinanceira("Picpay");
		contaService.cadastrarConta(contaDTO);
		despesaDTO = new NovaDespesaDTO();
		despesaDTO.setContaId(1L);
		despesaDTO.setDataPagamento(LocalDate.of(2020, 10, 20));
		despesaDTO.setDataPagamentoEsperado(LocalDate.of(2020, 10, 20));
		despesaDTO.setDescricao("Pofsagamento Boleto");
		despesaDTO.setTipoDespesa(TipoDespesa.OUTROS);
		despesaDTO.setValor(3500.50);
		despesaService.cadastrarDespesa(despesaDTO);
		despesaDTO = new NovaDespesaDTO();
		despesaDTO.setContaId(1L);
		despesaDTO.setDataPagamento(LocalDate.of(2020, 10, 20));
		despesaDTO.setDataPagamentoEsperado(LocalDate.of(2020, 10, 20));
		despesaDTO.setDescricao("Pagamento Boleto");
		despesaDTO.setTipoDespesa(TipoDespesa.OUTROS);
		despesaDTO.setValor(3500.50);
		despesaService.cadastrarDespesa(despesaDTO);
		despesaDTO = new NovaDespesaDTO();
		despesaDTO.setContaId(2L);
		despesaDTO.setDataPagamento(LocalDate.of(2020, 10, 20));
		despesaDTO.setDataPagamentoEsperado(LocalDate.of(2020, 10, 20));
		despesaDTO.setDescricao("Pagamento Boleto");
		despesaDTO.setTipoDespesa(TipoDespesa.OUTROS);
		despesaDTO.setValor(3500.50);
		despesaService.cadastrarDespesa(despesaDTO);

	}

	@Test
	void deveConsultarDadosDespesa() throws ApiException {
		assertNotNull(despesaService.dadosDespesa(1L));
	}

	@Test
	void deveConsultarDadosDespesaPorTipoDespesa() {
		assertNotNull(despesaService.dadosDespesaPorTipoDespesa(TipoDespesa.OUTROS));
	}

	@Test
	void deveListarDespesaPorPeriodo() {
		assertNotNull(despesaService.listarDespesaPorPeriodo(LocalDate.of(2020, 10, 19), LocalDate.of(2020, 10, 21)));
	}

	@Test
	void naoDeveTrazerListaDespesaPorPeriodoSemDespesas() {
		assertEquals(0,
				despesaService.listarDespesaPorPeriodo(LocalDate.of(2019, 5, 15), LocalDate.of(2020, 10, 19)).size());
	}

	@Test
	void deveEditarDespesa() throws ApiException {
		EditarDespesaDTO despesa = new EditarDespesaDTO();
		despesa.setDescricao("Edit Despesa");
		despesa.setTipoDespesa(TipoDespesa.LAZER);
		despesa.setId(2l);
		despesaService.editarDespesa(2l, despesa);
		Despesa edit = despesaService.dadosDespesa(2L);
		assertAll(() -> assertEquals(TipoDespesa.LAZER, edit.getTipoDespesa()),
				() -> assertEquals("Edit Despesa", edit.getDescricao()));
	}

	@Test
	void deveLancarExcecaoAoRemoverDespesaInexistente() throws ApiException {
		DespesaNaoEncontradaException excecao = assertThrows(DespesaNaoEncontradaException.class, () -> {
			despesaService.removerDespesa(500L);
		});
		String mensagemEsperada = "A despesa: " + "500" + " Nao existe";
		String mensagemRecebida = excecao.getMessage();
		assertTrue(mensagemRecebida.contains(mensagemEsperada));

	}

	@Test
	void deveListarReceitas() {
		assertNotNull(despesaService.listarReceitas());
	}

	@Test
	void deveListarDespesasContaPorPeriodo() {
		assertNotNull(despesaService.listarDespesasContaPorPeriodo(2L, LocalDate.of(2020, 1, 25),
				LocalDate.of(2020, 12, 25)));
	}

	@Test
	void deveConsultarValorTotalDespesas() {
		assertNotNull(despesaService.valorTotalDespesas());
	}

	@Test
	void deveConsultarValorTotalDespesaPorContaId() {
		assertNotNull(despesaService.valorTotalDespesaPorContaId(1L));
	}

}
