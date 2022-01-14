package br.com.receita;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.conta.Conta;
import br.com.receita.enums.TipoReceita;
import lombok.Data;

@Entity
@Data
public class Receita {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private TipoReceita tipoReceita;

	@ManyToOne
	@JsonBackReference
	private Conta conta;

	private Double valor;
	private LocalDate dataRecebimento;
	private LocalDate dataRecebimentoEsperado;
	private String descricao;
}
