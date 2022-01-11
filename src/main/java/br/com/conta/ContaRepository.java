package br.com.conta;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
	
	
	@Query("SELECT SUM(c.saldo) FROM Conta c")
	Optional<Double> findSaldoTotal();

}
