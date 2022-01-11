package br.com.receita;

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
import br.com.exception.ApiException;
import br.com.receita.enums.TipoReceita;

@Service
public class ReceitaService {

	@Autowired
	private ReceitaRepository receitaRepository;
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * Metodo responsavel por cadastrar uma receita
	 * 
	 * @param receitaDTO receita a ser cadastrada
	 * @return Receita cadastrada
	 * @throws ApiException caso o Id da conta da receita nao seja encontrado
	 */
	public Receita cadastrarReceita(@Valid ReceitaDTO receitaDTO) throws ApiException {
	Receita receita = modelMapper.map(receitaDTO, Receita.class);
	Conta conta = contaRepository.findById(receitaDTO.getContaId()).orElseThrow( () -> new ContaNaoEncontradaException(receitaDTO.getId()));
	receita.setConta(conta);
		return receitaRepository.save(receita);
	}
	
	/**
	 * Metodo responsavel por consultar os dados de uma receita
	 * 
	 * @param id da receita a ser consultada
	 * @return Receita da consulta
	 * @throws ApiException caso a receita nao exista
	 */
	public Receita dadosReceita(Long id) throws ApiException {
		return receitaRepository.findById(id).orElseThrow( () -> new ContaNaoEncontradaException(id));
	}
	
	/**
	 * Metodo responsavel por consultar as receitas por tipo de receita
	 * @see {@link TipoReceita}
	 * 
	 * @param tipoReceita
	 * @return
	 */
	public List<Receita> dadosReceitaPorTipoReceita(TipoReceita tipoReceita) {
		return receitaRepository.findByTipoReceita(tipoReceita);
	}
	
	public void editarReceita(Long id, @Valid Receita receita) throws ApiException {
		if (receitaRepository.existsById(id)) {
			receitaRepository.save(receita);
		} else {
			throw new ContaNaoEncontradaException(id);
		}
	}
	public void removerReceita(Long id) {
		receitaRepository.deleteById(id);
	}
	public List<Receita> listarReceitas() {
		return receitaRepository.findAll();
	}
	
	public List<Receita> listarReceitasPorPeriodo(Long contaId, LocalDate dataInicio, LocalDate dataFim) {
		return receitaRepository.findByContaIdAndDataRecebimentoBetween(contaId, dataInicio, dataFim);
	}
	
	public Optional<Double> valorTotalReceitas() {
		return receitaRepository.findValorTotalReceitas();
	}
	public Optional<Double> valorTotalReceitaPorContaId(Long contaId) {
		//TODO melhorar implementacao deste metodo
		return receitaRepository.findValorTotalReceitasPorContaId(contaId);
	}
	
}
