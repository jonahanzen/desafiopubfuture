package br.com.despesa.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.despesa.enums.TipoDespesa;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
/**
 * Classe que contem apenas  as propriedades necessarias para cadastrar uma Despesa
 *
 */
public class NovaDespesaDTO {
	@JsonIgnore
	private Long id;
	
	private Long contaId;
	private Double valor;
	@ApiModelProperty(example = "2022-01-16")
	private LocalDate dataPagamento;
	@ApiModelProperty(example = "2022-01-16")
	private LocalDate dataPagamentoEsperado;
	private String descricao;
	private TipoDespesa tipoDespesa;

}