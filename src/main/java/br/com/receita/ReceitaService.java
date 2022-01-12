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
import br.com.receita.exception.ReceitaNaoEncontradaException;

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
	 * @param tipoReceita a ser consultado
	 * @return List de receitas
	 */
	public List<Receita> dadosReceitaPorTipoReceita(TipoReceita tipoReceita) {
		return receitaRepository.findByTipoReceita(tipoReceita);
	}
	
	/**
	 * Metodo responsavel por editar uma receita
	 * 
	 * @param id da receita a ser editada
	 * @param receita com os novos valores
	 * @throws ApiException caso a receita nao seja encontrada
	 */
	public void editarReceita(Long id, @Valid ReceitaDTO receitaDTO) throws ApiException {
		Receita receita = receitaRepository.findById(id).orElseThrow( () -> new ReceitaNaoEncontradaException(id)) ;
		Conta conta = contaRepository.findById(receita.getConta().getId()).orElseThrow( () -> new ContaNaoEncontradaException(id) ) ;
			Receita novaReceita = modelMapper.map(receitaDTO, Receita.class);
			novaReceita.setId(id);
			novaReceita.setConta(conta);
			receitaRepository.save(novaReceita);
	}
	
	/**
	 * Metodo responsavel por listar as receitas entre um periodo
	 * 
	 * @param dataInicio do periodo a ser consultado 
	 * @param dataFim do periodo a ser consultado
	 * @return List de receitas do periodo
	 */
	public List<Receita> listarReceitasPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
		return receitaRepository.findByDataRecebimentoBetween(dataInicio, dataFim);
	}
	
	
	/**
	 * Metodo responsavel por remover uma receita
	 * 
	 * @param id da receita a ser removida
	 * @throws ReceitaNaoEncontradaException  caso a receita nao exista
	 */
	public void removerReceita(Long id) throws ReceitaNaoEncontradaException {
		if (!receitaRepository.existsById(id)) {
			throw new ReceitaNaoEncontradaException(id);
		}
		receitaRepository.deleteById(id);
	}
	
	/**
	 * Metodo responsavel por listar todas as receitas
	 * 
	 * @return List de receitas
	 */
	public List<Receita> listarReceitas() {
		return receitaRepository.findAll();
	}
	
	/**
	 * Metodo responsavel por consultar receitas entre um periodo e outro de uma conta
	 * 
	 * @param contaId das receitas a serem consultadas
	 * @param dataInicio do periodo a ser consultado
	 * @param dataFim do periodo a ser consultado
	 * @return List de receitas do periodo
	 */
	public List<Receita> listarReceitasContaPorPeriodo(Long contaId, LocalDate dataInicio, LocalDate dataFim) {
		return receitaRepository.findByContaIdAndDataRecebimentoBetween(contaId, dataInicio, dataFim);
	}
	
	/**
	 * Metodo responsavel por consultar o valor total das receitas
	 * 
	 * @return null caso nao haja receitas; Double valor total das receitas
	 */
	public Optional<Double> valorTotalReceitas() {
		return receitaRepository.findValorTotalReceitas();
	}
	
	/**
	 * Metodo responsavel por consultar o valor total das receitas de uma conta
	 * 
	 * @param contaId a ser consultado
	 * @return null caso nao haja receitas; Double valor total das receitas da conta
	 */
	public Optional<Double> valorTotalReceitaPorContaId(Long contaId) {
		return receitaRepository.findValorTotalReceitasPorContaId(contaId);
	}
	
}
