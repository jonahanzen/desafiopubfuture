package br.com.despesa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.conta.Conta;
import br.com.conta.ContaRepository;
import br.com.conta.exception.ContaNaoEncontradaException;
import br.com.despesa.enums.TipoDespesa;
import br.com.despesa.exception.DespesaNaoEncontradaException;
import br.com.exception.ApiException;

@Service
public class DespesaService {

	@Autowired
	private DespesaRepository despesaRepository;

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Metodo responsavel por cadastrar uma despesa
	 * 
	 * @param despesaDTO despesa a ser cadastrada
	 * @return Despesa cadastrada
	 * @throws ApiException caso o id da Conta da despesa nao seja encontrado
	 */
	public Despesa cadastrarDespesa(@Valid DespesaDTO despesaDTO) throws ApiException {
		Despesa despesa = modelMapper.map(despesaDTO, Despesa.class);
		Conta conta = contaRepository.findById(despesaDTO.getContaId())
				.orElseThrow(() -> new ContaNaoEncontradaException(despesaDTO.getId()));
		despesa.setConta(conta);
		return despesaRepository.save(despesa);
	}

	/**
	 * Metodo responsavel por consultar os dados de uma despesa
	 * 
	 * @param id da despesa a ser consultada
	 * @return Despesa consultada
	 * @throws ApiException caso a despesa nao seja encontrada
	 */
	public Despesa dadosDespesa(Long id) throws ApiException {
		return despesaRepository.findById(id).orElseThrow(() -> new DespesaNaoEncontradaException(id));
	}

	/**
	 * Metodo responsavel por consultar dados de todas as despesas pelo tipo de despesa
	 * @see @L
	 * 
	 * @param tipoDespesa a ser consultado
	 * @return List de despesas 
	 */
	public List<Despesa> dadosDespesaPorTipoDespesa(TipoDespesa tipoDespesa) {
		return despesaRepository.findByTipoDespesa(tipoDespesa);
	}

	public void editarDespesa(Long id, @Valid Despesa despesa) throws ApiException {
		if (despesaRepository.existsById(id)) {
			despesaRepository.save(despesa);
		} else {
			throw new DespesaNaoEncontradaException(id);
		}
	}

	/**
	 * Metodo responsavel por remover uma despesa
	 * 
	 * @param id da despesa a ser removida
	 */
	public void removerDespesa(Long id) {
		despesaRepository.deleteById(id);
	}

	/**
	 * Metodo reponsavel por consultar todas as despesas
	 * 
	 * @return List de despesas
	 */
	public List<Despesa> listarReceitas() {
		return despesaRepository.findAll();
	}

	/**
	 * Metodo responsavel por consultar despesas entre um periodo e outro
	 * 
	 * @param contaId das despesas a serem consultadas
	 * @param dataInicio da consulta das despesas
	 * @param dataFim da consulta das despesas
	 * @return List com as despesas do periodo
	 */
	public List<Despesa> listarDespesasPorPeriodo(Long contaId, LocalDate dataInicio, LocalDate dataFim) {
		return despesaRepository.findByContaIdAndDataPagamentoBetween(contaId, dataInicio, dataFim);
	}

	/**
	 * Metodo responsavel por consultar o valor total de todas as despesas
	 * 
	 * @return null caso nao haja despesas; Double valor total das despesas
	 */
	public Optional<Double> valorTotalDespesas() {
		return despesaRepository.findValorTotalDespesas();
	}

	/**
	 * Metodo responsavel por consultar o valor total das despesas de uma conta
	 * 
	 * @param contaId a ser consultadas as despesas
	 * @return null caso nao haja despesas; Double valor total das despesas da conta
	 */
	public Optional<Double> valorTotalDespesaPorContaId(Long contaId) {
		return despesaRepository.findValorTotalDespesasPorContaId(contaId);
	}

}
