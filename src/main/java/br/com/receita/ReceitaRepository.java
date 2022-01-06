package br.com.receita;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

	List<Receita> findByIdAndDataRecebimentoBetween(Long id, LocalDate dataInicio, LocalDate dataFim);

	List<Receita> findByTipoReceita(Long tipoReceita);
	
	@Query("SELECT SUM(r.valor) FROM Receita r")
	Double findValorTotalReceitas();
	
	@Query("SELECT SUM(r.valor) FROM Receita r WHERE Conta_id = ?1")
	Double findValorTotalReceitasPorContaId(Long contaId);

	
}
