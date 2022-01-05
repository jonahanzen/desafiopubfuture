package br.com.conta;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;

	public Conta cadastrarConta(@Valid Conta conta) {
		return contaRepository.save(conta);
	}

	public void editarConta(Long id, @Valid Conta conta) {
		if (contaRepository.existsById(id)) {
			contaRepository.save(conta);
		} else {

		}
	}

	public void removerConta(Long id) {
		contaRepository.deleteById(id);
	}

	public List<Conta> listarContas() {
		return contaRepository.findAll();
	}
	public Double listarSaldoTotal(Long idConta) {
		Conta conta = this.dadosConta(idConta);
		return conta.getSaldo();
	}
	public Conta dadosConta(Long idConta) {
		return contaRepository.findById(idConta).get();
	}

	public void transferirSaldoEntreContas(Long contaId, Long contaDestinoId, Double valorTransferencia) {
		Conta conta = contaRepository.findById(contaId).get();
		Conta contaDestino = contaRepository.findById(contaDestinoId).get();
		this.sacarSaldo(conta.getId(), valorTransferencia);
		this.depositarSaldo(contaDestinoId, valorTransferencia);
	}

	public Double depositarSaldo(Long idConta, Double saldo) {
		Conta conta = contaRepository.findById(idConta).get();
		conta.setSaldo(conta.getSaldo() + saldo);
		contaRepository.save(conta);
		return conta.getSaldo();
	}

	public Double sacarSaldo(Long idConta, Double saldo) {
		Conta conta = contaRepository.findById(idConta).get();
		conta.setSaldo(conta.getSaldo() - saldo);
		contaRepository.save(conta);
		return conta.getSaldo();
	}

}
