package br.com.despesa;

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

import br.com.despesa.dto.EditarDespesaDTO;
import br.com.despesa.dto.NovaDespesaDTO;
import br.com.despesa.enums.TipoDespesa;
import br.com.exception.ApiException;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("despesa")
public class DespesaController {

	@Autowired
	private DespesaService despesaService;
	
	@GetMapping
	@ApiOperation(value = "Listar todas as despesas")
	public List<Despesa> listarDespesas() {
		return despesaService.listarDespesas();
	}

	@GetMapping("{despesaId}")
	@ApiOperation(value = "Consultar dados de uma despesa pelo ID")
	public Despesa dadosDespesaPorId(@PathVariable Long despesaId) throws ApiException {
		return despesaService.dadosDespesa(despesaId);
	}

	@GetMapping("{contaId}/{dataInicio}/{dataFim}")
	@ApiOperation(value = "Listar todas as despesas de uma contra entre um periodo e outro")
	public List<Despesa> listarDespesasContaPorPeriodo(@PathVariable Long contaId,
			@PathVariable("dataInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
			@PathVariable("dataFim") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim) {
		return despesaService.listarDespesasContaPorPeriodo(contaId, dataInicio, dataFim);
	}
	
	@GetMapping("{dataInicio}/{dataFim}")
	@ApiOperation(value = "Listar todas as despesas entre um periodo e outro")
	public List<Despesa> listarDespesasPorPeriodo(
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim ) {
		return despesaService.listarDespesaPorPeriodo(dataInicio, dataFim);
	}
	
	@GetMapping("total")
	@ApiOperation(value = "Listar valor total de todas as despesas")
	public Optional<Double> valorTotalDespesas() {
		return despesaService.valorTotalDespesas();
	}

	@GetMapping("{contaId}/total")
	@ApiOperation(value = "Listar todas as despesas de uma conta")
	public Optional<Double> valorTotalDespesasPorContaId(@PathVariable Long contaId) {
		return despesaService.valorTotalDespesaPorContaId(contaId);
	}

	@GetMapping("tipo/{tipoDespesa}")
	@ApiOperation(value = "Listar todas as despesas pelo tipo da despesa")
	public List<Despesa> despesaPorTipoDespesa(@PathVariable TipoDespesa tipoDespesa) {
		return despesaService.dadosDespesaPorTipoDespesa(tipoDespesa);
	}

	@PostMapping
	@ApiOperation(value = "Cadastrar uma nova despesa")
	public Despesa cadastrarDespesa(@RequestBody NovaDespesaDTO despesaDTO) throws ApiException {
		return despesaService.cadastrarDespesa(despesaDTO);
	}

	@PutMapping("{despesaId}")
	@ApiOperation(value = "Editar uma despesa ja existente")
	public void editarDespesa(@PathVariable Long despesaId, @RequestBody EditarDespesaDTO despesaDTO) throws ApiException {
		despesaService.editarDespesa(despesaId, despesaDTO);
	}

	@DeleteMapping("{despesaId}")
	@ApiOperation(value = "Remover uma despesa")
	public void removerDespesa(@PathVariable Long despesaId) throws ApiException {
		despesaService.removerDespesa(despesaId);
	}

}