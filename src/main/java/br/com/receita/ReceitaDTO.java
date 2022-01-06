package br.com.receita;

import java.time.LocalDate;

import br.com.receita.enums.TipoReceita;
import lombok.Data;

@Data
public class ReceitaDTO {

	private Long id;
	private Long contaId;
	private Double valor;
	private LocalDate dataRecebimento;
	private LocalDate dataRecebimentoEsperado;
	private String descricao;
	private TipoReceita tipoReceita;

}