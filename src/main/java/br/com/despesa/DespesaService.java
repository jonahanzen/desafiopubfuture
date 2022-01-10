package br.com.despesa;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.conta.Conta;
import br.com.conta.ContaRepository;
import br.com.conta.exception.ContaNaoEncontradaException;
import br.com.despesa.enums.TipoDespesa;
import br.com.exception.ApiException;

@Service
public class DespesaService {

	@Autowired
	private DespesaRepository despesaRepository;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private ModelMapper modelMapper;

	public Despesa cadastrarDespesa(@Valid DespesaDTO despesaDTO) throws ApiException {
		Despesa despesa = modelMapper.map(despesaDTO, Despesa.class);
		Conta conta = contaRepository.findById(despesaDTO.getContaId())
				.orElseThrow(() -> new ContaNaoEncontradaException(despesaDTO.getId()));
		despesa.setConta(conta);
		return despesaRepository.save(despesa);
	}

	public Despesa dadosDespesa(Long id) throws ApiException {
		return despesaRepository.findById(id).orElseThrow(() -> new ContaNaoEncontradaException(id));
	}

	public List<Despesa> dadosDespesaPorTipoDespesa(TipoDespesa tipoDespesa) {
		return despesaRepository.findByTipoDespesa(tipoDespesa);
	}

	public void editarDespesa(Long id, @Valid Despesa despesa) throws ApiException {
		if (despesaRepository.existsById(id)) {
			despesaRepository.save(despesa);
		} else {
			throw new ContaNaoEncontradaException(id);
		}
	}

	public void removerDespesa(Long id) {
		despesaRepository.deleteById(id);
	}

	public List<Despesa> listarReceitas() {
		return despesaRepository.findAll();
	}

	public List<Despesa> listarDespesasPorPeriodo(Long id, LocalDate dataInicio, LocalDate dataFim) {
		return despesaRepository.findByIdAndDataRecebimentoBetween(id, dataInicio, dataFim);
	}

	public Double valorTotalDespesas() {
		return despesaRepository.findValorTotalDespesas();
	}

	public Double valorTotalDespesaPorContaId(Long contaId) {
		return despesaRepository.findValorTotalDespesasPorContaId(contaId);
	}

}
