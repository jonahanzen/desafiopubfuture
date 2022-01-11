package br.com.conta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.conta.enums.TipoConta;
import br.com.conta.exception.ContaNaoEncontradaException;
import br.com.exception.ApiException;

@SpringBootTest
class ContaServiceTest {

	@Autowired
	private ContaService contaService;

	private static ContaDTO contaDTO;
	@BeforeAll
	public static void init() {
		contaDTO = new ContaDTO();
	}

	@BeforeEach
	public  void contas() {
		contaDTO.setSaldo(2000.00);
		contaDTO.setTipoConta(TipoConta.CARTEIRA);
		try {
			contaService.cadastrarConta(contaDTO);
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

	@Test
	void deveCadastrarConta() throws ApiException {
		assertNotNull(contaService.cadastrarConta(contaDTO));
	}

	@Test
	void deveEditarConta() throws ApiException {
		contaDTO.setSaldo(1500.50);
		contaDTO.setTipoConta(TipoConta.CARTEIRA);
		contaService.editarConta(2L, contaDTO);
		Conta conta = contaService.dadosConta(2L);
		assertTrue(conta.getSaldo() == 1500 && conta.getTipoConta() == TipoConta.CARTEIRA);
	}

	@Test
	void deveRemoverConta() throws ApiException {
		contaService.removerConta(1L);
		assertNull(contaService.dadosConta(1L));
	}

	@Test
	void deveLancarExcecaoAoRemoverContaInexistente() throws ApiException {
		contaDTO.setId(250L);
		contaService.cadastrarConta(contaDTO);
		contaService.removerConta(250L);
		assertThrows(ContaNaoEncontradaException.class, () -> {
			contaService.dadosConta(250L);
		});
	}

	@Test
	void deveListarContas() {
		assertNotNull(contaService.listarContas());
	}

	@Test
	void deveListarSaldoTotalPorId() throws ApiException {
		assertTrue(contaService.listarSaldoTotalPorId(2L) > 0);
	}

	@Test
	void deveListarSaldoTotal() {
		assertTrue(contaService.listarSaldoTotal() > 0);
	}

	@Test
	void deveTrazerDadosConta() throws ApiException {
		Conta conta = contaService.dadosConta(3L);
		assertNotNull(conta);
	}

	@Test
	void deveTransferirSaldoEntreContas() throws ApiException {
		contaService.transferirSaldoEntreContas(3L, 4L, 2000.00);
		assertEquals(4000.00, contaService.dadosConta(4L).getSaldo());
	}

	@Test
	void deveDepositarSaldo() throws ApiException {
		contaService.depositarSaldo(4L, 2000.00);
		assertEquals(4000.00, contaService.dadosConta(4L).getSaldo());
	}

	@Test
	void deveSacarSaldo() throws ApiException {
		contaService.sacarSaldo(3L, 2000.00);
		assertEquals(0.0, contaService.dadosConta(3L).getSaldo());
	}

}
