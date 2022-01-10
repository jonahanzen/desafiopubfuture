package br.com.despesa;

import java.time.LocalDate;

import br.com.receita.enums.TipoReceita;
import lombok.Data;

@Data
public class DespesaDTO {

	private Long id;
	private Long contaId;
	private Double valor;
	private LocalDate dataPagamento;
	private LocalDate dataRecebimentoEsperado;
	private String descricao;
	private TipoReceita tipoReceita;

}
