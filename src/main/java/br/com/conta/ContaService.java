package br.com.conta;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.conta.exception.ContaJaExisteException;
import br.com.conta.exception.ContaNaoEncontradaException;
import br.com.conta.exception.DadosContaIncorretosException;
import br.com.exception.ApiException;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	ModelMapper mapper;

	public Conta cadastrarConta(@Valid ContaDTO contaDTO) throws ApiException {
		if (contaRepository.existsById(contaDTO.getId())) {
			throw new ContaJaExisteException(contaDTO.getId());
		} else {
			Conta conta = mapper.map(contaDTO, Conta.class);
			return contaRepository.save(conta);
		}
	}

	public void editarConta(Long id, @Valid ContaDTO contaDTO) throws ApiException {
		if (contaRepository.existsById(id)) {
			try {
				Conta conta = mapper.map(contaDTO, Conta.class);
				contaRepository.save(conta);
			} catch (Exception e) {
				throw new DadosContaIncorretosException(contaDTO.getId());
			}
		} else {
			throw new ContaNaoEncontradaException(id);
		}
	}

	public void removerConta(Long id) throws ApiException {
		if (contaRepository.existsById(id)) {
			contaRepository.deleteById(id);
		} else {
			throw new ContaNaoEncontradaException(id);
		}
	}

	public List<Conta> listarContas() {
		return contaRepository.findAll();
	}

	public Double listarSaldoTotal(Long idConta) throws ApiException {
		if (contaRepository.existsById(idConta)) {
			Conta conta = this.dadosConta(idConta);
			return conta.getSaldo();
		} else {
			throw new ContaNaoEncontradaException(idConta);
		}
	}

	public Conta dadosConta(Long idConta) throws ApiException {
		return contaRepository.findById(idConta).orElseThrow(() -> new ContaNaoEncontradaException(idConta));
	}

	public void transferirSaldoEntreContas(Long contaId, Long contaDestinoId, Double valorTransferencia)
			throws ApiException {
		Conta conta = contaRepository.findById(contaId).orElseThrow(() -> new ContaNaoEncontradaException(contaId));
		Conta contaDestino = contaRepository.findById(contaDestinoId)
				.orElseThrow(() -> new ContaNaoEncontradaException(contaDestinoId));
		this.sacarSaldo(conta.getId(), valorTransferencia);
		this.depositarSaldo(contaDestino.getId(), valorTransferencia);
	}

	public Double depositarSaldo(Long idConta, Double saldo) throws ApiException {
		Conta conta = contaRepository.findById(idConta).orElseThrow(() -> new ContaNaoEncontradaException(idConta));
		conta.setSaldo(conta.getSaldo() + saldo);
		contaRepository.save(conta);
		return conta.getSaldo();
	}

	public Double sacarSaldo(Long idConta, Double saldo) throws ApiException {
		Conta conta = contaRepository.findById(idConta).orElseThrow(() -> new ContaNaoEncontradaException(idConta));
		conta.setSaldo(conta.getSaldo() - saldo);
		contaRepository.save(conta);
		return conta.getSaldo();
	}

}
