package br.com.conta;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.conta.exception.ContaJaExisteException;
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
	private ModelMapper mapper;

	/**
	 * Metodo responsavel por cadastrar uma conta no banco de dados
	 * 
	 * @param contaDTO conta a ser cadastrada
	 * @return conta cadastrada
	 * @throws ContaJaExisteException caso ja exista a conta por ID
	 */
	public Conta cadastrarConta(@Valid ContaDTO contaDTO) throws ApiException {
		Conta conta = mapper.map(contaDTO, Conta.class);
		if (contaDTO.getId() != null) {
			if (contaRepository.existsById(contaDTO.getId())) {
				throw new ContaJaExisteException(contaDTO.getId());
			}
		}
		return contaRepository.save(conta);
	}

	/**
	 * Metodo responsavel por editar a conta
	 * 
	 * @param id       da conta a ser editada
	 * @param contaDTO com os novos valores
	 * @throws ContaNaoEncontradaException caso a conta com o id nao exista
	 */
	public void editarConta(Long id, @Valid ContaDTO contaDTO) throws ContaNaoEncontradaException {
		contaRepository.findById(id).orElseThrow(() -> new ContaNaoEncontradaException(id));
		Conta conta = mapper.map(contaDTO, Conta.class);
		conta.setId(id);
		contaRepository.save(conta);
	}

	/**
	 * Metodo responsavel por remover uma conta do banco de dados
	 * 
	 * @param id da conta a ser removida
	 * @throws ApiException caso a conta com id nao exista
	 */
	public void removerConta(Long id) throws ApiException {
		if (contaRepository.existsById(id)) {
			contaRepository.deleteById(id);
		} else {
			throw new ContaNaoEncontradaException(id);
		}
	}

	/**
	 * Metodo responsavel por listar todas as contas
	 * 
	 * @return list de todas as contas
	 */
	public List<Conta> listarContas() {
		return contaRepository.findAll();
	}

	/**
	 * Metodo responsavel por listar o saldo total pelo Id da Conta. O metodo ira
	 * somar saldo e receita, subtraindo entao as despesas (saldo + receita) -
	 * despesa
	 * 
	 * @param idConta a ser efetuado calculo do saldo total
	 * @return Double saldo total da conta
	 * @throws ApiException caso a conta com id nao seja encontrado
	 */
	public Double listarSaldoTotalPorId(Long idConta) throws ApiException {
		if (contaRepository.existsById(idConta)) {
			Conta conta = this.dadosConta(idConta);
			receitaRepository.findValorTotalReceitasPorContaId(idConta)
					.ifPresent(receita -> conta.setSaldo(conta.getSaldo() + receita));
			despesaRepository.findValorTotalDespesasPorContaId(idConta)
					.ifPresent(despesa -> conta.setSaldo(conta.getSaldo() - despesa));
			return conta.getSaldo();
		} else {
			throw new ContaNaoEncontradaException(idConta);
		}
	}

	/**
	 * Metodo responsavel por listar o saldo total de todas as contas. O metodo ira
	 * somar o saldo e a receita das contas, e entao subtrair as despesas (saldo +
	 * receitas) - despesa
	 * 
	 * @return Double saldo total de todas as contas
	 */
	public Double listarSaldoTotal() {
		Conta conta = new Conta();
		contaRepository.findSaldoTotal().ifPresent(conta::setSaldo);
		receitaRepository.findValorTotalReceitas().ifPresent(r -> conta.setSaldo(conta.getSaldo() + r));
		despesaRepository.findValorTotalDespesas().ifPresent(d -> conta.setSaldo(conta.getSaldo() - d));
		return conta.getSaldo();
	}

	/**
	 * Metodo responsavel por consultar os dados de uma conta
	 * 
	 * @param idConta dos dados a serem consultados
	 * @return Conta com os dados consultados
	 * @throws ApiException caso
	 */
	public Conta dadosConta(Long idConta) throws ApiException {
		return contaRepository.findById(idConta).orElseThrow(() -> new ContaNaoEncontradaException(idConta));
	}

	/**
	 * Metodo responsavel por transferir saldo entre duas contas
	 * 
	 * 
	 * @param contaOrigemId      da conta que esta transferindo
	 * @param contaDestinoId     da conta que recebe a transferencia
	 * @param valorTransferencia do valor a ser transferido
	 * @throws ApiException caso alguma das contas nao seja encontrada
	 */
	public void transferirSaldoEntreContas(Long contaOrigemId, Long contaDestinoId, Double valorTransferencia)
			throws ApiException {
		Conta contaOrigem = contaRepository.findById(contaOrigemId)
				.orElseThrow(() -> new ContaNaoEncontradaException(contaOrigemId));
		Conta contaDestino = contaRepository.findById(contaDestinoId)
				.orElseThrow(() -> new ContaNaoEncontradaException(contaDestinoId));
		this.sacarSaldo(contaOrigem.getId(), valorTransferencia);
		this.depositarSaldo(contaDestino.getId(), valorTransferencia);
	}

	/**
	 * Metodo responsavel por depositar em uma conta
	 * 
	 * @param idConta para onde sera depositoado
	 * @param saldo a ser depositado
	 * @return Double valor que foi depositado
	 * @throws ApiException caso a conta nao seja encontrada
	 */
	public Double depositarSaldo(Long idConta, Double saldo) throws ApiException {
		Conta conta = contaRepository.findById(idConta).orElseThrow(() -> new ContaNaoEncontradaException(idConta));
		conta.setSaldo(conta.getSaldo() + saldo);
		contaRepository.save(conta);
		return conta.getSaldo();
	}

	/**
	 * Metodo responsavel por sacar de uma conta
	 * 
	 * @param idConta de onde sera sacado
	 * @param saldo a ser sacado
	 * @return Double valor que foi sacado
	 * @throws ApiException caso a conta nao seja encontrada
	 */
	public Double sacarSaldo(Long idConta, Double saldo) throws ApiException {
		Conta conta = contaRepository.findById(idConta).orElseThrow(() -> new ContaNaoEncontradaException(idConta));
		conta.setSaldo(conta.getSaldo() - saldo);
		contaRepository.save(conta);
		return conta.getSaldo();
	}

}
