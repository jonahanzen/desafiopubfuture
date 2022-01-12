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

import br.com.conta.dto.EditarContaDTO;
import br.com.conta.dto.NovaContaDTO;
import br.com.exception.ApiException;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("conta")
public class ContaController {

	@Autowired
	private ContaService contaService;

	@GetMapping
	@ApiOperation(value = "Listar todas as contas")
	public List<Conta> listarContas() {
		return contaService.listarContas();
	}

	@GetMapping("{contaId}")
	@ApiOperation(value = "Consultar uma conta pelo ID")
	public Conta dadosContaPorId(@PathVariable Long contaId) throws ApiException {
		return contaService.dadosConta(contaId);
	}

	@GetMapping("total")
	@ApiOperation(value = "Consultar o saldo total das contas, levando em consideracao receitas e despesas")
	public Double listarSaldoTotal() {
		return contaService.listarSaldoTotal();
	}

	@GetMapping("{contaId}/total")
	@ApiOperation(value = "Consultar o saldo total de uma conta, levando em consideracao receitas e despesas")
	public Double saldoTotalContaPorId(@PathVariable Long contaId) throws ApiException {
		return contaService.listarSaldoTotalPorId(contaId);
	}

	@PostMapping
	@ApiOperation(value = "Cadastrar uma nova conta")
	public Conta cadastrarConta(@RequestBody NovaContaDTO contaDTO) {
		return contaService.cadastrarConta(contaDTO);
	}

	@PutMapping("{contaId}")
	@ApiOperation(value = "Editar uma conta ja existente")
	public void editarConta(@PathVariable Long contaId, @RequestBody EditarContaDTO contaDTO) throws ApiException {
		contaService.editarConta(contaId, contaDTO);
	}

	@PutMapping("{contaOrigem}/transferir/{contaDestino}/valor/{valorTransferencia}")
	@ApiOperation(value = "Transferir saldo entre duas contas")
	public void transferirSaldoEntreContas(@PathVariable Long contaOrigem, @PathVariable Long contaDestino,
			@PathVariable Double valorTransferencia) throws ApiException {
		contaService.transferirSaldoEntreContas(contaOrigem, contaDestino, valorTransferencia);
	}

	@DeleteMapping("{contaId}")
	@ApiOperation(value = "Remover uma conta")
	public void removerConta(@PathVariable Long contaId) throws ApiException {
		contaService.removerConta(contaId);
	}

}
