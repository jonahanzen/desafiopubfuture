package br.com.despesa.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.despesa.enums.TipoDespesa;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Classe que contem apenas as propriedades necessarias para editar uma Despesa
 */
@Data
public class EditarDespesaDTO {

	@JsonIgnore
	private Long id;
	
	@ApiModelProperty(example = "2022-01-16")
	private LocalDate dataPagamento;
	
	@ApiModelProperty(example = "2022-01-16")
	private LocalDate dataPagamentoEsperado;
	
	private String descricao;
	private TipoDespesa tipoDespesa;
	private Double valor;

}
