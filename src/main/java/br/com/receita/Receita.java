package br.com.receita;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.conta.Conta;
import br.com.despesa.enums.TipoDespesa;
import br.com.receita.enums.TipoReceita;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class Receita {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@PositiveOrZero
	private Double valor;
	
	private LocalDate dataRecebimento;
	
	private LocalDate dataRecebimentoEsperado;
	
	private String descricao;
	
	@Enumerated(EnumType.ORDINAL)
	private TipoReceita tipoReceita;
	
	@ManyToOne
	private Conta conta;
	
	
	
	
}
