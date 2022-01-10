package br.com.receita;

import java.time.LocalDate;
import java.util.List;

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
	
	public Receita cadastrarReceita(@Valid ReceitaDTO receitaDTO) throws ApiException {
	Receita receita = modelMapper.map(receitaDTO, Receita.class);
	Conta conta = contaRepository.findById(receitaDTO.getContaId()).orElseThrow( () -> new ContaNaoEncontradaException(receitaDTO.getId()));
	receita.setConta(conta);
		return receitaRepository.save(receita);
	}
	
	public Receita dadosReceita(Long id) throws ApiException {
		return receitaRepository.findById(id).orElseThrow( () -> new ContaNaoEncontradaException(id));
	}
	
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
	
	public List<Receita> listarReceitasPorPeriodo(Long id, LocalDate dataInicio, LocalDate dataFim) {
		return receitaRepository.findByIdAndDataRecebimentoBetween(id, dataInicio, dataFim);
	}
	
	public Double valorTotalReceitas() {
		return receitaRepository.findValorTotalReceitas();
	}
	public Double valorTotalReceitaPorContaId(Long contaId) {
		return receitaRepository.findValorTotalReceitasPorContaId(contaId);
	}
	
}
