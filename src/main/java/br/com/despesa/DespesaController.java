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

import br.com.despesa.enums.TipoDespesa;
import br.com.exception.ApiException;

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
	public Optional<Double> valorTotalDespesas() {
		return despesaService.valorTotalDespesas();
	}

	@GetMapping("{contaId}/total")
	public Optional<Double> valorTotalDespesasPorContaId(@PathVariable Long contaId) {
		return despesaService.valorTotalDespesaPorContaId(contaId);
	}

	@GetMapping("tipo/{tipoDespesa}")
	public List<Despesa> despesaPorTipoDespesa(@PathVariable TipoDespesa tipoDespesa) {
		return despesaService.dadosDespesaPorTipoDespesa(tipoDespesa);
	}

	@PostMapping
	public Despesa cadastrarDespesa(@RequestBody DespesaDTO despesaDTO) throws ApiException {
		return despesaService.cadastrarDespesa(despesaDTO);
	}

	@PutMapping("{despesaId}")
	public void editarDespesa(@PathVariable Long despesaId, @RequestBody Despesa despesa) throws ApiException {
		despesaService.editarDespesa(despesaId, despesa);
	}

	@DeleteMapping("{despesaId}")
	public void removerDespesa(@PathVariable Long despesaId) {
		despesaService.removerDespesa(despesaId);
	}

}