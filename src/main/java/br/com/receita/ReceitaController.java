package br.com.receita;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
import br.com.receita.dto.EditarReceitaDTO;
import br.com.receita.dto.NovaReceitaDTO;
import br.com.receita.enums.TipoReceita;
import br.com.receita.exception.ReceitaNaoEncontradaException;

@RestController
@RequestMapping("receita")
public class ReceitaController {

	@Autowired
	private ReceitaService receitaService;

	@GetMapping
	public List<Receita> listarReceitas() {
		return receitaService.listarReceitas();
	}

	@GetMapping("{receitaId}")
	public Receita dadosReceitaPorId(@PathVariable Long receitaId) throws ApiException {
		return receitaService.dadosReceita(receitaId);
	}

	@GetMapping("{contaId}/{dataInicio}/{dataFim}")
	public List<Receita> listarReceitasContaPorPeriodo(@PathVariable Long contaId,
			@PathVariable("dataInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
			@PathVariable("dataFim") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim) {
		return receitaService.listarReceitasContaPorPeriodo(contaId, dataInicio, dataFim);
	}
	
	@GetMapping("{dataInicio}/{dataFim}")
	public List<Receita> listarReceitasPorPeriodo(
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim) {
		return receitaService.listarReceitasPorPeriodo(dataInicio, dataFim);
	}

	@GetMapping("total")
	public Optional<Double> valorTotalReceitas() {
		return receitaService.valorTotalReceitas();
	}

	@GetMapping("{contaId}/total")
	public Optional<Double> valorTotalReceitasPorContaId(@PathVariable Long contaId) {
		return receitaService.valorTotalReceitaPorContaId(contaId);
	}

	@GetMapping("tipo/{tipoReceita}")
	public List<Receita> receitasPorTipoReceita(@PathVariable TipoReceita tipoReceita) {
		return receitaService.dadosReceitaPorTipoReceita(tipoReceita);
	}

	@PostMapping
	public Receita cadastrarReceita(@RequestBody NovaReceitaDTO receitaDTO) throws ApiException {
		return receitaService.cadastrarReceita(receitaDTO);
	}

	@PutMapping("{receitaId}")
	public void editarReceita(@PathVariable Long receitaId, @RequestBody EditarReceitaDTO receitaDTO) throws ApiException {
		receitaService.editarReceita(receitaId, receitaDTO);
	}

	@DeleteMapping("{receitaId}")
	public void removerReceita(@PathVariable Long receitaId) throws ReceitaNaoEncontradaException {
		receitaService.removerReceita(receitaId);
	}

}
