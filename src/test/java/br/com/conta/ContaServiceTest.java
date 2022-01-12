package br.com.conta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.Valid;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.conta.dto.EditarContaDTO;
import br.com.conta.dto.NovaContaDTO;
import br.com.conta.enums.TipoConta;
import br.com.conta.exception.ContaNaoEncontradaException;
import br.com.exception.ApiException;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class ContaServiceTest {

	@Autowired
	private ContaService contaService;

	private static @Valid NovaContaDTO contaDTO;

	@BeforeAll
	public void contasSetup() throws ApiException {
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
		contaDTO = new NovaContaDTO();
		contaDTO.setSaldo(2000.00);
		contaDTO.setTipoConta(TipoConta.CARTEIRA);
		contaDTO.setInstituicaoFinanceira("Picpay");
		contaService.cadastrarConta(contaDTO);
		Assumptions.assumeTrue(contaService.listarContas().size() > 0);
	}

	@Test
	void deveEditarConta() throws ApiException {
		EditarContaDTO conta = new EditarContaDTO();
		conta.setId(2L);
		conta.setInstituicaoFinanceira("BB");
		conta.setSaldo(1500.50);
		conta.setTipoConta(TipoConta.CARTEIRA);
		contaService.editarConta(2L, conta);
		Conta contaEdit = contaService.dadosConta(2L);
		assertTrue(contaEdit.getSaldo() == 1500.50 && contaEdit.getTipoConta() == TipoConta.CARTEIRA);
	}

	@Test
	void deveLancarExcecaoAoConsultarContaInexistente() throws ApiException {
		contaService.removerConta(3L);
		ContaNaoEncontradaException excecao = assertThrows(ContaNaoEncontradaException.class, () -> {
			contaService.dadosConta(3L);
		});

		String mensagemEsperada = "A conta: " + "3" + " Nao foi encontrada.";
		String mensagemRecebida = excecao.getMessage();

		assertTrue(mensagemRecebida.contains(mensagemEsperada));

	}

	@Test
	void deveLancarExcecaoAoRemoverContaInexistente() throws ApiException {
		ContaNaoEncontradaException excecao = assertThrows(ContaNaoEncontradaException.class, () -> {
			contaService.removerConta(1500L);
		});

		String mensagemEsperada = "A conta: " + "1500" + " Nao foi encontrada.";
		String mensagemRecebida = excecao.getMessage();

		assertTrue(mensagemRecebida.contains(mensagemEsperada));

	}

	@Test
	void deveListarContas() {
		assertNotNull(contaService.listarContas());
	}

	@Test
	void deveListarSaldoTotalPorId() throws ApiException {
		assertNotNull(contaService.listarSaldoTotalPorId(1L));
	}

	@Test
	void deveListarSaldoTotal() {
		assertTrue(contaService.listarSaldoTotal() > 0);
	}

	@Test
	void deveTrazerDadosConta() throws ApiException {
		Conta conta = contaService.dadosConta(1L);
		assertNotNull(conta);
	}

	@Test
	void deveTransferirSaldoEntreContas() throws ApiException {
		contaService.transferirSaldoEntreContas(4L, 5L, 2000.00);
		assertEquals(4000.00, contaService.dadosConta(5L).getSaldo());
	}

	@Test
	void deveDepositarSaldo() throws ApiException {
		Conta conta = contaService.dadosConta(4L);
		Double deposito = contaService.depositarSaldo(4L, 2000.00);
		assertNotSame(conta.getSaldo(), deposito);
	}

	@Test
	void deveSacarSaldo() throws ApiException {
		Conta conta = contaService.dadosConta(5L);
		contaService.sacarSaldo(5L, conta.getSaldo());
		assertEquals(0.0, contaService.dadosConta(5L).getSaldo());
	}

}
