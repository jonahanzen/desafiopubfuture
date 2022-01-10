package br.com.despesa;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.exception.ApiException;
import br.com.receita.Receita;
import br.com.receita.ReceitaDTO;
import br.com.receita.ReceitaService;
import br.com.receita.enums.TipoReceita;

@RestController
@RequestMapping("despesa")
public class DespesaController {

	@Autowired
	private DespesaService despesaService;

	@GetMapping
	public List<Despesa> listarDespesas() {
		return despesaService.listarReceitas();
	}

	@GetMapping("{despesaId}")
	public Despesa dadosDespesaPorId(@PathVariable Long despesaId) throws ApiException {
		return despesaService.dadosDespesa(despesaId);
	}

	@GetMapping("{contaId}/{dataInicio}/{dataFim}")
	public List<Despesa> listarDespesasPorPeriodo(@PathVariable Long contaId,
			@PathVariable("dataInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
			@PathVariable("dataFim") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim) {
		return despesaService.listarDespesasPorPeriodo(contaId, dataInicio, dataFim);
	}

	@GetMapping("total")
	public Double valorTotalDespesas() {
		return despesaService.valorTotalDespesas();
	}

	@GetMapping("{contaId}/total")
	public Double valorTotalDespesasPorContaId(@PathVariable Long contaId) {
		return despesaService.valorTotalReceitaPorContaId(contaId);
	}

	@GetMapping("tipo/{tipoReceita}")
	public List<Receita> receitasPorTipoReceita(@PathVariable TipoReceita tipoReceita) {
		return receitaService.dadosReceitaPorTipoReceita(tipoReceita);
	}

	@PostMapping
	public Receita cadastrarReceita(@RequestBody ReceitaDTO receitaDTO) throws ApiException {
		return receitaService.cadastrarReceita(receitaDTO);
	}

	@PutMapping("{receitaId}")
	public void editarReceita(@PathVariable Long receitaId, @RequestBody Receita receita) throws ApiException {
		receitaService.editarReceita(receitaId, receita);
	}

	@DeleteMapping("{receitaId}")
	public void removerReceita(@PathVariable Long receitaId) {
		receitaService.removerReceita(receitaId);
	}

}