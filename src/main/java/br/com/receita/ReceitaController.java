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
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("receita")
public class ReceitaController {

	@Autowired
	private ReceitaService receitaService;

	@GetMapping
	@ApiOperation(value = "Listar todas as receitas")
	public List<Receita> listarReceitas() {
		return receitaService.listarReceitas();
	}

	@GetMapping("{receitaId}")
	@ApiOperation(value = "Listar dados de uma receita por ID")
	public Receita dadosReceitaPorId(@PathVariable Long receitaId) throws ApiException {
		return receitaService.dadosReceita(receitaId);
	}

	@GetMapping("{contaId}/{dataInicio}/{dataFim}")
	@ApiOperation(value = "Listar todas as receitas de uma conta entre um periodo e outro")
	public List<Receita> listarReceitasContaPorPeriodo(@PathVariable Long contaId,
			@PathVariable("dataInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
			@PathVariable("dataFim") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim) {
		return receitaService.listarReceitasContaPorPeriodo(contaId, dataInicio, dataFim);
	}
	
	@GetMapping("{dataInicio}/{dataFim}")
	@ApiOperation(value = "Listar todas as receitas entre um periodo e outro")
	public List<Receita> listarReceitasPorPeriodo(
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim) {
		return receitaService.listarReceitasPorPeriodo(dataInicio, dataFim);
	}

	@GetMapping("total")
	@ApiOperation(value = "Listar o valor total de todas as receitas")
	public Optional<Double> valorTotalReceitas() {
		return receitaService.valorTotalReceitas();
	}

	@GetMapping("{contaId}/total")
	@ApiOperation(value = "Listar o valor total de todas as receitas de uma conta")
	public Optional<Double> valorTotalReceitasPorContaId(@PathVariable Long contaId) {
		return receitaService.valorTotalReceitaPorContaId(contaId);
	}

	@GetMapping("tipo/{tipoReceita}")
	@ApiOperation(value = "Listar todas as receitas pelo tipo da receita")
	public List<Receita> receitasPorTipoReceita(@PathVariable TipoReceita tipoReceita) {
		return receitaService.dadosReceitaPorTipoReceita(tipoReceita);
	}
	
	@PostMapping
	@ApiOperation(value = "Cadastrar uma nova receita")
	public Receita cadastrarReceita(@RequestBody NovaReceitaDTO receitaDTO) throws ApiException {
		return receitaService.cadastrarReceita(receitaDTO);
	}

	@PutMapping("{receitaId}")
	@ApiOperation(value = "Editar uma receita que ja existe")
	public void editarReceita(@PathVariable Long receitaId, @RequestBody EditarReceitaDTO receitaDTO) throws ApiException {
		receitaService.editarReceita(receitaId, receitaDTO);
	}

	@DeleteMapping("{receitaId}")
	@ApiOperation(value = "Remover uma receita")
	public void removerReceita(@PathVariable Long receitaId) throws ReceitaNaoEncontradaException {
		receitaService.removerReceita(receitaId);
	}
}
