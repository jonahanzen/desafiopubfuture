package br.com.receita;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.receita.enums.TipoReceita;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

	List<Receita> findByContaIdAndDataRecebimentoBetween(Long id, LocalDate dataInicio, LocalDate dataFim);

	List<Receita> findByTipoReceita(TipoReceita tipoReceita);

	@Query("SELECT SUM(r.valor) FROM Receita r")
	Optional<Double> findValorTotalReceitas();

	@Query("SELECT SUM(r.valor) FROM Receita r WHERE Conta_id = ?1")
	Optional<Double> findValorTotalReceitasPorContaId(Long contaId);
	
	
	List<Receita> findByDataRecebimentoBetween(LocalDate dataInicio, LocalDate dataFim);

}
