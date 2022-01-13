package br.com.despesa;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.conta.Conta;
import br.com.conta.ContaService;
import br.com.conta.dto.NovaContaDTO;
import br.com.conta.enums.TipoConta;
import br.com.despesa.dto.NovaDespesaDTO;
import br.com.despesa.enums.TipoDespesa;

//TODO implementar todos os testes

@SpringBootTest
@AutoConfigureMockMvc
class DespesaControllerTest {

	@Autowired
	private DespesaService despesaService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ContaService contaService;

	@Test
	void deveListarDespesas() throws Exception {
		NovaContaDTO contaDTO = new NovaContaDTO();
		contaDTO.setSaldo(1000.00);
		contaDTO.setTipoConta(TipoConta.CARTEIRA);
		contaDTO.setInstituicaoFinanceira("banco A");
		Conta conta = contaService.cadastrarConta(contaDTO);

		NovaDespesaDTO despesa = new NovaDespesaDTO();
		despesa.setContaId(conta.getId());
		despesa.setDataPagamento(LocalDate.of(2020, 05, 15));
		despesa.setDataPagamentoEsperado(LocalDate.of(2020, 05, 15));
		despesa.setDescricao("pizza");
		despesa.setTipoDespesa(TipoDespesa.ALIMENTACAO);
		despesa.setValor(60.74);
		despesaService.cadastrarDespesa(despesa);

		mockMvc.perform(get("/despesa")).andExpect(status().isOk()).andExpect(content().string(containsString("id")))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

	}

	@Test
	void deveListarDadosDespesaPorId() {
	}

	@Test
	void deveListarDespesasContaPorPeriodo() {
	}

	@Test
	void deveListarDespesasPorPeriodo() {
	}

	@Test
	void deveListarValorTotalDespesas() {
	}

	@Test
	void deveListarValorTotalDespesasPorContaId() {
	}

	@Test
	void deveListarDespesaPorTipoDespesa() {
	}

	@Test
	void deveCadastrarDespesa() {
	}

	@Test
	void deveEditarDespesa() {
	}

	@Test
	void deveRemoverDespesa() {
	}

}
