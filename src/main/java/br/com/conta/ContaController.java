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
	public Conta dadosContaporId(@PathVariable Long contaId) throws ApiException {
		return contaService.dadosConta(contaId);
	}

	@PostMapping
	public Conta cadastrarConta(@RequestBody ContaDTO contaDTO) throws Exception {
		return contaService.cadastrarConta(contaDTO);
	}

	@PutMapping("{contaId}")
	public void editarConta(@PathVariable Long contaId, @RequestBody ContaDTO contaDTO) throws ApiException {
		contaService.editarConta(contaId, contaDTO);
	}

	@PutMapping("{contaOrigem}/transferir/{contaDestino}/valor/{valorTransferencia}")
	public void transferirSaldoEntreContas(@PathVariable Long contaOrigem, @PathVariable Long contaDestino,
			@PathVariable Double valorTransferencia) throws ApiException {
		contaService.transferirSaldoEntreContas(contaOrigem, contaDestino, valorTransferencia);
	}

	@DeleteMapping("{contaId}")
	public void removerConta(@PathVariable Long contaId) throws ApiException {
		contaService.removerConta(contaId);
	}

}
