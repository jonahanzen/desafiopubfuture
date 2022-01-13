package br.com.conta;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import br.com.conta.dto.EditarContaDTO;
import br.com.conta.dto.NovaContaDTO;
import br.com.conta.enums.TipoConta;

@SpringBootTest
@AutoConfigureMockMvc
class ContaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ContaService contaService;

	@Test
	void deveListarContas() throws Exception {
		NovaContaDTO contaDTO = new NovaContaDTO();
		contaDTO.setSaldo(1000.00);
		contaDTO.setTipoConta(TipoConta.CARTEIRA);
		contaDTO.setInstituicaoFinanceira("banco A");
		contaService.cadastrarConta(contaDTO);

		mockMvc.perform(get("/conta")).andExpect(status().isOk());

		assertTrue(contaService.listarContas().size() > 0);
	}

	@Test
	void deveListarDadosContaPorId() throws Exception {
		NovaContaDTO contaDTO = new NovaContaDTO();
		contaDTO.setSaldo(1000.00);
		contaDTO.setTipoConta(TipoConta.CARTEIRA);
		contaDTO.setInstituicaoFinanceira("banco A");
		Conta conta = contaService.cadastrarConta(contaDTO);

		mockMvc.perform(get("/conta/{id}", conta.getId())).andExpect(status().isOk());

		assertNotNull(contaService.dadosConta(conta.getId()));
	}

	@Test
	void deveListarSaldoTotal() throws Exception {
		NovaContaDTO contaDTO = new NovaContaDTO();
		contaDTO.setSaldo(1000.00);
		contaDTO.setTipoConta(TipoConta.CARTEIRA);
		contaDTO.setInstituicaoFinanceira("banco A");
		contaService.cadastrarConta(contaDTO);

		MvcResult resultado = mockMvc.perform(get("/conta/total")).andExpect(status().isOk()).andReturn();

		assertAll(() -> assertEquals("application/json", resultado.getResponse().getContentType()),
				() -> assertEquals(contaService.listarSaldoTotal().toString(),
						resultado.getResponse().getContentAsString()));

	}

	@Test
	void deveListarSaldoTotalContaPorId() throws Exception {
		NovaContaDTO contaDTO = new NovaContaDTO();
		contaDTO.setSaldo(1000.00);
		contaDTO.setTipoConta(TipoConta.CARTEIRA);
		contaDTO.setInstituicaoFinanceira("banco A");
		Conta conta = contaService.cadastrarConta(contaDTO);

		MvcResult resultado = mockMvc.perform(get("/conta/{id}/total", conta.getId())).andExpect(status().isOk())
				.andReturn();

		assertAll(() -> assertEquals("application/json", resultado.getResponse().getContentType()),
				() -> assertEquals(contaService.listarSaldoTotalPorId(conta.getId()).toString(),
						resultado.getResponse().getContentAsString()));
	}

	@Test
	void deveCadastrarConta() throws Exception {
		NovaContaDTO conta = new NovaContaDTO();
		conta.setSaldo(1000.00);
		conta.setTipoConta(TipoConta.CARTEIRA);
		conta.setInstituicaoFinanceira("banco A");

		mockMvc.perform(
				post("/conta").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(conta)))
				.andExpect(status().isOk()).andExpect(content().string(containsString("id")));

	}

	@Test
	void deveEditarConta() throws Exception {
		NovaContaDTO contaDTO = new NovaContaDTO();
		contaDTO.setSaldo(1000.00);
		contaDTO.setTipoConta(TipoConta.CARTEIRA);
		contaDTO.setInstituicaoFinanceira("banco A");
		Conta conta = contaService.cadastrarConta(contaDTO);
		assumeTrue(conta != null);

		EditarContaDTO editarConta = new EditarContaDTO();
		editarConta.setInstituicaoFinanceira("Banco B");
		editarConta.setSaldo(10500.50);
		editarConta.setTipoConta(TipoConta.POUPANCA);

		Gson contaJson = new Gson();
		String json = contaJson.toJson(editarConta);

		mockMvc.perform(put("/conta/{id}", conta.getId()).contentType(MediaType.APPLICATION_JSON).content((json)))
				.andExpect(status().isOk());

		assertAll(() -> assertEquals(10500.50, contaService.dadosConta(conta.getId()).getSaldo()),
				() -> assertEquals("Banco B", contaService.dadosConta(conta.getId()).getInstituicaoFinanceira()),
				() -> assertEquals(TipoConta.POUPANCA, contaService.dadosConta(conta.getId()).getTipoConta()));

	}

	@Test
	void deveTransferirSaldoEntreContas() throws Exception {
		NovaContaDTO contaDTO = new NovaContaDTO();
		contaDTO.setSaldo(1000.00);
		contaDTO.setTipoConta(TipoConta.CARTEIRA);
		contaDTO.setInstituicaoFinanceira("banco A");
		Conta contaOrigem = contaService.cadastrarConta(contaDTO);
		NovaContaDTO contaDTO2 = new NovaContaDTO();
		contaDTO2.setSaldo(50.00);
		contaDTO2.setTipoConta(TipoConta.POUPANCA);
		contaDTO2.setInstituicaoFinanceira("banco B");
		Conta contaDestino = contaService.cadastrarConta(contaDTO2);
		
		assumeTrue(contaOrigem != null && contaDestino != null);
		
		mockMvc.perform(put("/conta/{contaOrigem}/transferir/{contaDestino}/valor/{valorTransferencia}"
				, contaOrigem.getId(), contaDestino.getId(), contaDTO.getSaldo()))
		.andExpect(status().isOk());
		
		assertEquals( (contaDTO.getSaldo() + contaDTO2.getSaldo()) ,contaService.dadosConta(contaDestino.getId()).getSaldo());
		
	}

	@Test
	void deveRemoverConta() throws Exception {
		NovaContaDTO contaDTO = new NovaContaDTO();
		contaDTO.setSaldo(1000.00);
		contaDTO.setTipoConta(TipoConta.CARTEIRA);
		contaDTO.setInstituicaoFinanceira("banco A");
		Conta conta = contaService.cadastrarConta(contaDTO);

		mockMvc.perform(delete("/conta/{id}", conta.getId())).andExpect(status().isOk());
	}

}
