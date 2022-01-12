package br.com.despesa.dto;

import java.time.LocalDate;

import br.com.despesa.enums.TipoDespesa;
import lombok.Data;

@Data
public class NovaDespesaDTO {

	private Long id;
	private Long contaId;
	private Double valor;
	private LocalDate dataPagamento;
	private LocalDate dataPagamentoEsperado;
	private String descricao;
	private TipoDespesa tipoDespesa;

	
	
}
