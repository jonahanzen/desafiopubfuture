package br.com.despesa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.despesa.enums.TipoDespesa;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

	List<Despesa> findByContaIdAndDataPagamentoBetween(Long id, LocalDate dataInicio, LocalDate dataFim);

	List<Despesa> findByTipoDespesa(TipoDespesa tipoDespesa);

	@Query("SELECT SUM(d.valor) FROM Despesa d")
	Optional<Double> findValorTotalDespesas();

	@Query("SELECT SUM(d.valor) FROM Despesa d WHERE	 Conta_id = ?1")
	Optional<Double> findValorTotalDespesasPorContaId(Long contaId);

	List<Despesa> findByDataPagamentoBetween(LocalDate dataInicio, LocalDate dataFim);

}
