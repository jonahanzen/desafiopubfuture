package br.com.receita;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.conta.Conta;
import br.com.conta.ContaRepository;

@Service
public class ReceitaService {

	@Autowired
	private ReceitaRepository receitaRepository;
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Receita cadastrarReceita(@Valid ReceitaDTO receitaDTO) {
	Receita receita = modelMapper.map(receitaDTO, Receita.class);
	//TODO melhorar implementacao do metodo
	Conta conta = contaRepository.findById(receitaDTO.getContaId()).get();
	receita.setConta(conta);
		return receitaRepository.save(receita);
	}
	
	public Receita dadosReceita(Long id) {
		//TODO Melhorar implementacao do metodo
		return receitaRepository.findById(id).get();
	}
	
	public List<Receita> dadosReceitaPorTipoReceita(Long tipoReceita) {
		return receitaRepository.findByTipoReceita(tipoReceita);
	}
	
	public void editarReceita(Long id, @Valid Receita receita) {
		if (receitaRepository.existsById(id)) {
			receitaRepository.save(receita);
		} else {
			
		}
	}
	public void removerReceita(Long id) {
		receitaRepository.deleteById(id);;
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
	public Double valorTotalReceitaPorUsuarioId(Long contaId) {
		return receitaRepository.findValorTotalReceitasPorContaId(contaId);
	}
	
}
