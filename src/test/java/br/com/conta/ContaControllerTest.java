package br.com.conta;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

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
		contaDTO.setInstituicaoFinanceira("picpay");
		contaService.cadastrarConta(contaDTO);
		
		mockMvc.perform(get("/conta"))
		.andExpect(status().isOk());
		
	 assertTrue(contaService.listarContas().size() > 0);
	

	}

	@Test
	void testDadosContaPorId() {
	}

	@Test
	void testListarSaldoTotal() {
	}

	@Test
	void testSaldoTotalContaPorId() {
	}

	@Test
	void deveCadastrarConta() throws Exception {
		NovaContaDTO conta = new NovaContaDTO();
		conta.setSaldo(1000.00);
		conta.setTipoConta(TipoConta.CARTEIRA);
		conta.setInstituicaoFinanceira("picpay");

		mockMvc.perform(
				post("/conta").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(conta)))
				.andExpect(status().isOk());
		
		assertNotNull(contaService.dadosConta(1L));
	}

	@Test
	void testEditarConta() {
	}

	@Test
	void testTransferirSaldoEntreContas() {
	}

	@Test
	void testRemoverConta() throws Exception {
		NovaContaDTO contaDTO = new NovaContaDTO();
		contaDTO.setSaldo(1000.00);
		contaDTO.setTipoConta(TipoConta.CARTEIRA);
		contaDTO.setInstituicaoFinanceira("picpay");
		Conta conta = contaService.cadastrarConta(contaDTO);
		
		mockMvc.perform(
				delete("/conta/" + conta.getId()))
				.andExpect(status().isOk());
	}

}
