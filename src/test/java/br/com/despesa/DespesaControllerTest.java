package br.com.despesa;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.conta.Conta;
import br.com.conta.ContaService;
import br.com.conta.dto.NovaContaDTO;
import br.com.conta.enums.TipoConta;
import br.com.despesa.dto.EditarDespesaDTO;
import br.com.despesa.dto.NovaDespesaDTO;
import br.com.despesa.enums.TipoDespesa;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class DespesaControllerTest {

	private static NovaContaDTO novaContaDTO;
	private static Conta conta;
	private static NovaDespesaDTO novaDespesa;
	private static EditarDespesaDTO editarDespesa;

	@Autowired
	private DespesaService despesaService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ContaService contaService;

	@Autowired
	ObjectMapper objectMapper;

	@BeforeAll
	private void init() {
		novaContaDTO = new NovaContaDTO();
		novaContaDTO.setSaldo(1000.00);
		novaContaDTO.setTipoConta(TipoConta.CARTEIRA);
		novaContaDTO.setInstituicaoFinanceira("banco A");
		conta = contaService.cadastrarConta(novaContaDTO);
		assumeThat(conta != null);
	}

	private Long novaDespesa() throws Exception {
		novaDespesa = new NovaDespesaDTO();
		novaDespesa.setContaId(conta.getId());
		novaDespesa.setDataPagamento(LocalDate.of(2020, 05, 15));
		novaDespesa.setDataPagamentoEsperado(LocalDate.of(2020, 05, 15));
		novaDespesa.setDescricao("pizza");
		novaDespesa.setTipoDespesa(TipoDespesa.ALIMENTACAO);
		novaDespesa.setValor(60.74);
		assumeThat(novaDespesa != null);
		return despesaService.cadastrarDespesa(novaDespesa).getId();
	}

	@Test
	void deveListarDespesas() throws Exception {
		novaDespesa();
		novaDespesa();

		mockMvc.perform(get("/despesa")).andExpect(status().isOk()).andExpect(content().string(containsString("id")))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()", is(equalTo(despesaService.listarDespesas().size()))));

	}

	@Test
	void deveListarDadosDespesaPorId() throws Exception {
		Long id = novaDespesa();

		mockMvc.perform(get("/despesa/{id}", id)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(id.intValue())))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void deveListarDespesasContaPorPeriodo() throws Exception {
		Long despesaId = novaDespesa();
		Long contaId = despesaService.dadosDespesa(despesaId).getConta().getId();
		Despesa despesa = despesaService.dadosDespesa(despesaId);

		mockMvc.perform(get("/despesa/{id}/{dataInicio}/{dataFim}", contaId, despesa.getDataPagamento().minusDays(1L),
				despesa.getDataPagamento().plusDays(1L))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()",
						is(despesaService.listarDespesasContaPorPeriodo(contaId,
								despesa.getDataPagamento().minusDays(1L), despesa.getDataPagamento().plusDays(1L))
								.size())));
	}

	@Test
	void deveListarDespesasPorPeriodo() throws Exception {
		Long despesaId = novaDespesa();
		Despesa despesa = despesaService.dadosDespesa(despesaId);

		mockMvc.perform(get("/despesa/{dataInicio}/{dataFim}", despesa.getDataPagamento().minusDays(1L),
				despesa.getDataPagamento().plusDays(1L))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()",
						is(despesaService.listarDespesaPorPeriodo(despesa.getDataPagamento().minusDays(1L),
								despesa.getDataPagamento().plusDays(1L)).size())));
	}

	@Test
	void deveListarValorTotalDespesas() throws Exception {
		novaDespesa();
		novaDespesa();
		assumeThat(despesaService.listarDespesas().size() > 1);

		String valorTotalDespesa = mockMvc.perform(get("/despesa/total")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
				.getContentAsString();

		assertEquals(despesaService.valorTotalDespesas().get().toString(), valorTotalDespesa);
	}

	@Test
	void deveListarValorTotalDespesasPorContaId() throws Exception {
		Long despesaId = novaDespesa();
		Long contaId = despesaService.dadosDespesa(despesaId).getConta().getId();

		MvcResult resultado = mockMvc.perform(get("/despesa/{contaId}/total", contaId)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

		assertEquals(despesaService.valorTotalDespesaPorContaId(contaId).get().toString(),
				resultado.getResponse().getContentAsString());
	}

	@Test
	@Transactional
	void deveListarDespesaPorTipoDespesa() throws Exception {
		novaDespesa();

		mockMvc.perform(get("/despesa/tipo/{tipoDespesa}", novaDespesa.getTipoDespesa())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].tipoDespesa", is("ALIMENTACAO")));

	}

	@Test
	void deveCadastrarDespesa() throws Exception {
		novaDespesa = new NovaDespesaDTO();
		novaDespesa.setContaId(conta.getId());
		novaDespesa.setDataPagamento(LocalDate.of(2020, 05, 15));
		novaDespesa.setDataPagamentoEsperado(LocalDate.of(2020, 05, 15));
		novaDespesa.setDescricao("pizza");
		novaDespesa.setTipoDespesa(TipoDespesa.ALIMENTACAO);
		novaDespesa.setValor(60.74);

		mockMvc.perform(post("/despesa").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(novaDespesa))).andExpect(status().isOk())
				.andExpect(jsonPath("$.valor", is(60.74)));
	}

	@Test
	void deveEditarDespesa() throws Exception {
		Long despesaId = novaDespesa();
		editarDespesa = new EditarDespesaDTO();
		editarDespesa.setDescricao("cursos");
		editarDespesa.setTipoDespesa(TipoDespesa.EDUCACAO);
		editarDespesa.setValor(650.50);

		String despesaJson = objectMapper.writeValueAsString(editarDespesa);

		mockMvc.perform(put("/despesa/{id}", despesaId).contentType(MediaType.APPLICATION_JSON).content(despesaJson))
				.andExpect(status().isOk());

		Despesa novaDespesa = despesaService.dadosDespesa(despesaId);

		assertAll(() -> assertEquals(editarDespesa.getDescricao(), novaDespesa.getDescricao()),
				() -> assertEquals(editarDespesa.getValor(), novaDespesa.getValor()),
				() -> assertEquals(editarDespesa.getTipoDespesa(), novaDespesa.getTipoDespesa()),
				() -> assertEquals(editarDespesa.getDescricao(), novaDespesa.getDescricao()));
	}

	@Test
	void deveRemoverDespesa() throws Exception {
		Long despesaId = novaDespesa();

		mockMvc.perform(delete("/despesa/{id}", despesaId)).andExpect(status().isOk());

	}

}
