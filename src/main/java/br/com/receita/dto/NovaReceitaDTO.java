package br.com.receita.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.receita.enums.TipoReceita;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Classe que contem apenas as propriedades necessarias para a inclusao de uma
 * nova Receita
 *
 */
@Data
public class NovaReceitaDTO {
	@JsonIgnore
	private Long id;

	private Long contaId;
	private Double valor;
	@ApiModelProperty(example = "2022-01-16")
	private LocalDate dataRecebimento;
	@ApiModelProperty(example = "2022-01-16")
	private LocalDate dataRecebimentoEsperado;
	private String descricao;
	private TipoReceita tipoReceita;

}