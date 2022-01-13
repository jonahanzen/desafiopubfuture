package br.com.receita.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.receita.enums.TipoReceita;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Classe que contem apenas as propriedades necessarias para a inclusao de uma
 * nova Receita
 */
@Data
public class NovaReceitaDTO {
	@JsonIgnore
	private Long id;

	@ApiModelProperty(example = "2022-01-16")
	private LocalDate dataRecebimento;
	
	@ApiModelProperty(example = "2022-01-16")
	private LocalDate dataRecebimentoEsperado;

	private Long contaId;
	private String descricao;
	private Double valor;
	private TipoReceita tipoReceita;

}