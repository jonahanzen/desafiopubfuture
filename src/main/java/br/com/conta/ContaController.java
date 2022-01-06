package br.com.conta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.conta.exception.ContaJaExisteException;
import br.com.conta.exception.ContaNaoEncontradaException;
import br.com.conta.exception.DadosContaIncorretosException;
import br.com.exception.ApiException;

@RestController
@RequestMapping("conta")
public class ContaController {
	
	@Autowired
	private ContaService contaService;
	
	@GetMapping
	public List<Conta> listarContas() {
		return contaService.listarContas();
	}
	@GetMapping("{contaId}")
	public Conta dadosContaporId(@PathVariable Long contaId) {
		return contaService.dadosConta(contaId);
	}
	
	
	@PostMapping
	public Conta cadastrarConta(@RequestBody ContaDTO contaDTO) throws ApiException {
		return contaService.cadastrarConta(contaDTO);
	}
	
	@PutMapping("{contaId}")
	public void editarConta(@PathVariable Long contaId, @RequestBody ContaDTO contaDTO) throws ApiException {
		contaService.editarConta(contaId, contaDTO);
	}
	
	@DeleteMapping("{contaId}")
	public void removerConta(@PathVariable Long contaId) {
		contaService.removerConta(contaId);
	}
	
	@PutMapping("{conta}/{contaDestino}/{valorTransferencia}")
	public void transferirSaldoEntreContas
	(@PathVariable Long conta, @PathVariable Long contaDestino, @PathVariable Double valorTransferencia) {
	contaService.transferirSaldoEntreContas(conta, contaDestino, valorTransferencia);
	}
	
	

}
