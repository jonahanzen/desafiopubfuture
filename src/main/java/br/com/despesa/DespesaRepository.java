package br.com.despesa;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.despesa.enums.TipoDespesa;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

	List<Despesa> findByIdAndDataRecebimentoBetween(Long id, LocalDate dataInicio, LocalDate dataFim);

	List<Despesa> findByTipoDespesa(TipoDespesa tipoDespesa);

	@Query("SELECT SUM(r.valor) FROM Despesa d")
	Double findValorTotalDespesas();

	@Query("SELECT SUM(r.valor) FROM Despesa d WHERE Conta_id = ?1")
	Double findValorTotalDespesasPorContaId(Long contaId);

}
