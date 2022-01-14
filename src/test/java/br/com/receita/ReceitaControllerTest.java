package br.com.receita;

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
import br.com.receita.dto.EditarReceitaDTO;
import br.com.receita.dto.NovaReceitaDTO;
import br.com.receita.enums.TipoReceita;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class ReceitaControllerTest {

	private static NovaContaDTO novaContaDTO;
	private static NovaReceitaDTO novaReceitaDTO;
	private static EditarReceitaDTO editarReceitaDTO;
	private static Conta conta;

	@Autowired
	private ContaService contaService;

	@Autowired
	private ReceitaService receitaService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeAll
	private void init() {
		novaContaDTO = new NovaContaDTO();
		novaContaDTO.setInstituicaoFinanceira("banco X");
		novaContaDTO.setSaldo(15000.60);
		novaContaDTO.setTipoConta(TipoConta.POUPANCA);
		conta = contaService.cadastrarConta(novaContaDTO);
	}

	private Long novaReceita() throws Exception {
		novaReceitaDTO = new NovaReceitaDTO();
		novaReceitaDTO.setContaId(conta.getId());
		novaReceitaDTO.setDataRecebimento(LocalDate.now());
		novaReceitaDTO.setDataRecebimentoEsperado(LocalDate.now());
		novaReceitaDTO.setValor(3000.00);
		novaReceitaDTO.setTipoReceita(TipoReceita.SALARIO);
		return receitaService.cadastrarReceita(novaReceitaDTO).getId();
	}

	@Test
	void deveListarReceitas() throws Exception {
		novaReceita();
		novaReceita();

		mockMvc.perform(get("/receita")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString("id")))
				.andExpect(jsonPath("$.length()", is(equalTo(receitaService.listarReceitas().size()))));
	}

	@Test
	void deveListarDadosReceitaPorId() throws Exception {
		Long id = novaReceita();
		mockMvc.perform(get("/receita/{id}", id)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(id.intValue())));
	}

	@Test
	void deveListarReceitasContaPorPeriodo() throws Exception {
		Long receitaId = novaReceita();
		Long contaId = receitaService.dadosReceita(receitaId).getConta().getId();
		assumeThat(receitaId != null && contaId != null);
		Receita receita = receitaService.dadosReceita(receitaId);

		mockMvc.perform(get("/receita/{id}/{dataInicio}/{dataFim}", contaId, receita.getDataRecebimento().minusDays(1L),
				receita.getDataRecebimento().plusDays(1L))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()",
						is(receitaService.listarReceitasContaPorPeriodo(contaId,
								receita.getDataRecebimento().minusDays(1l), receita.getDataRecebimento().plusDays(1L))
								.size())));

	}

	@Test
	void deveListarReceitasPorPeriodo() throws Exception {
		Long receitaId = novaReceita();
		assumeThat(receitaId != null);
		Receita receita = receitaService.dadosReceita(receitaId);

		mockMvc.perform(get("/receita/{dataInicio}/{dataFim}", receita.getDataRecebimento().minusDays(1L),
				receita.getDataRecebimento().plusDays(1L))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()",
						is(receitaService.listarReceitasPorPeriodo(receita.getDataRecebimento().minusDays(1l),
								receita.getDataRecebimento().plusDays(1L)).size())));

	}

	@Test
	void deveListarValorTotalReceitas() throws Exception {
		novaReceita();
		novaReceita();
		assumeThat(receitaService.listarReceitas().size() > 1);

		String valorTotalReceita = mockMvc.perform(get("/receita/total")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
				.getContentAsString();

		assertEquals(receitaService.valorTotalReceitas().get().toString(), valorTotalReceita);
	}

	@Test
	void deveListarValorTotalReceitasPorContaId() throws Exception {
		Long receitaId = novaReceita();
		Long contaId = receitaService.dadosReceita(receitaId).getConta().getId();

		MvcResult resultado = mockMvc.perform(get("/receita/{contaId}/total", contaId)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

		assertEquals(receitaService.valorTotalReceitaPorContaId(contaId).get().toString(),
				resultado.getResponse().getContentAsString());

	}

	@Test
	void deveListarReceitasPorTipoReceita() throws Exception {
		novaReceita();
		mockMvc.perform(get("/receita/tipo/{tipoReceita}", novaReceitaDTO.getTipoReceita())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].tipoReceita", is("SALARIO")));
	}

	@Test
	void deveCadastrarReceita() throws Exception {
		novaReceitaDTO = new NovaReceitaDTO();
		novaReceitaDTO.setContaId(conta.getId());
		novaReceitaDTO.setDescricao("Pubfuture");
		novaReceitaDTO.setValor(3000.00);
		novaReceitaDTO.setDataRecebimento(LocalDate.of(2022, 01, 16));
		novaReceitaDTO.setDataRecebimentoEsperado(LocalDate.of(2022, 01, 16));
		novaReceitaDTO.setTipoReceita(TipoReceita.SALARIO);

		String receitaJson = objectMapper.writeValueAsString(novaReceitaDTO);

		mockMvc.perform(post("/receita").contentType(MediaType.APPLICATION_JSON).content(receitaJson))
				.andExpect(status().isOk()).andExpect(jsonPath("$.descricao", is(novaReceitaDTO.getDescricao())));
	}

	@Test
	void deveEditarReceita() throws Exception {
		Long receitaId = novaReceita();
		editarReceitaDTO = new EditarReceitaDTO();
		editarReceitaDTO.setDescricao("Desafio");
		editarReceitaDTO.setValor(4000.00);

		String receitaJson = objectMapper.writeValueAsString(editarReceitaDTO);

		mockMvc.perform(put("/receita/{id}", receitaId).contentType(MediaType.APPLICATION_JSON).content(receitaJson))
				.andExpect(status().isOk());

		Receita receita = receitaService.dadosReceita(receitaId);

		assertAll(() -> assertEquals(editarReceitaDTO.getDescricao(), receita.getDescricao()),
				() -> assertEquals(editarReceitaDTO.getValor(), receita.getValor()));
	}

	@Test
	void deveRemoverReceita() throws Exception {
		Long receitaId = novaReceita();

		mockMvc.perform(delete("/receita/{id}", receitaId)).andExpect(status().isOk());
	}

}
