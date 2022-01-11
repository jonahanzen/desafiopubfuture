package br.com.conta;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.conta.exception.ContaNaoEncontradaException;
import br.com.despesa.DespesaRepository;
import br.com.exception.ApiException;
import br.com.receita.ReceitaRepository;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private DespesaRepository despesaRepository;

	@Autowired
	private ReceitaRepository receitaRepository;

	@Autowired
	ModelMapper mapper;

	public Conta cadastrarConta(@Valid ContaDTO contaDTO) throws NoSuchElementException {
		if (contaDTO.getId() == null) {
			Conta conta = mapper.map(contaDTO, Conta.class);
			return contaRepository.save(conta);
		} else {
			return contaRepository.findById(contaDTO.getId()).orElseThrow();
		}
	}

	public void editarConta(Long id, @Valid ContaDTO contaDTO) throws ApiException {
		Conta conta = contaRepository.findById(id).orElseThrow(() -> new ContaNaoEncontradaException(id));
		conta = mapper.map(contaDTO, Conta.class);
		conta.setId(id);
		contaRepository.save(conta);
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
			Double contas = (receitaRepository.findValorTotalReceitasPorContaId(idConta)
					- despesaRepository.findValorTotalDespesasPorContaId(idConta));
			 Conta conta = this.dadosConta(idConta);
			return (conta.getSaldo() - contas);
		} else {
			throw new ContaNaoEncontradaException(idConta);
		}
	}

	public Conta dadosConta(Long idConta) throws ApiException {
		return contaRepository.findById(idConta).orElseThrow(() -> new ContaNaoEncontradaException(idConta));
	}

	public void transferirSaldoEntreContas(Long contaOrigemId, Long contaDestinoId, Double valorTransferencia)
			throws ApiException {
		Conta contaOrigem = contaRepository.findById(contaOrigemId)
				.orElseThrow(() -> new ContaNaoEncontradaException(contaOrigemId));
		Conta contaDestino = contaRepository.findById(contaDestinoId)
				.orElseThrow(() -> new ContaNaoEncontradaException(contaDestinoId));
		this.sacarSaldo(contaOrigem.getId(), valorTransferencia);
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
