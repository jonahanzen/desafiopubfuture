package br.com.despesa;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.conta.Conta;
import br.com.despesa.enums.TipoDespesa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class Despesa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double valor;
	
	private LocalDate dataPagamento;
	
	private LocalDate dataPagamentoEsperado;
	
	@Enumerated(EnumType.ORDINAL)
	private TipoDespesa tipoDespesa;
	
	@ManyToOne
	private Conta conta;
	
	private String descricao;
	
}
